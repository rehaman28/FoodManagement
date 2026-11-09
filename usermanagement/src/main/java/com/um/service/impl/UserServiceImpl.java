package com.um.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.um.config.CurrentUserService;
import com.um.dao.UserRepository;
import com.um.dto.request.UpdateUserRequestDto;
import com.um.dto.request.UpdateUserRoleRequestDto;
import com.um.dto.request.UserAddressRequestDto;
import com.um.dto.request.UserRequestDto;
import com.um.dto.response.UserResponseDto;
import com.um.exception.DuplicateUserException;
import com.um.exception.InvalidUserRequestException;
import com.um.exception.UserNotFoundException;
import com.um.model.Role;
import com.um.model.User;
import com.um.model.UserAddress;
import com.um.service.UserService;

@Service 
public class UserServiceImpl implements UserService{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder  passwordEncoder;
    private final CurrentUserService currentUserService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            CurrentUserService currentUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;
    }

    @Override
    public UserResponseDto addUsers(UserRequestDto userRequestDto) {
        validateUniqueUserFields(userRequestDto.getEmail(), userRequestDto.getUserPhone());

        User user = new  User();
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setUserPhone(userRequestDto.getUserPhone());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setUserAddresses(toUserAddresses(userRequestDto.getUserAddresses()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        UserResponseDto userResponse = buildUserResponseEntity(savedUser);
        return userResponse;
    }


    @Override
    public List<UserResponseDto> getUsers() {
        List<UserResponseDto> userResponse = userRepository.findAll()
        .stream()
        .map(this::buildUserResponseEntity)
        .toList();
        return userResponse;
    }

    @Override
    public UserResponseDto getUsersById(Long userId) {
        User user = findUserById(userId);
        UserResponseDto userResponseDto = buildUserResponseEntity(user);
        return userResponseDto;
    }

    @Override
    public UserResponseDto updateUser(Long userId,UpdateUserRequestDto userRequestDto) {

        validateUpdateRequest(userRequestDto);

        User targetUser  = findUserById(userId);
        User currentUser = currentUserService.getCurrentUser();

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isSelf = currentUser.getUserId() == userId;

        log.debug("Profile update authorization: actorUserId={}, actorRole={}, targetUserId={}, isAdmin={}, isSelf={}",
                currentUser.getUserId(), currentUser.getRole(), userId, isAdmin, isSelf);

        if (!isAdmin && !isSelf) {
        throw new AccessDeniedException(
                "Users can update only their own profile");
        }
        
        validateUniqueUserFields(
                userRequestDto.getEmail(),
                userRequestDto.getUserPhone(),
                targetUser);

        if (userRequestDto.getEmail() != null) {
            targetUser.setEmail(userRequestDto.getEmail());
        }
        if (userRequestDto.getPassword() != null) {
            targetUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        }
        if (userRequestDto.getUserName() != null) {
            targetUser.setUserName(userRequestDto.getUserName());
        }
        if (userRequestDto.getUserPhone() != null) {
            targetUser.setUserPhone(userRequestDto.getUserPhone());
        }
        if (userRequestDto.getUserAddresses() != null) {
            targetUser.setUserAddresses(toUserAddresses(userRequestDto.getUserAddresses()));
        }

        User savedUser = userRepository.save(targetUser);

        UserResponseDto userResponse = buildUserResponseEntity(savedUser);
        return userResponse;

    }

    private void validateUpdateRequest(UpdateUserRequestDto userRequestDto) {
        if (userRequestDto == null
                || (isBlank(userRequestDto.getUserName())
                && isBlank(userRequestDto.getUserPhone())
                && isBlank(userRequestDto.getEmail())
                && isBlank(userRequestDto.getPassword())
                && (userRequestDto.getUserAddresses() == null
                || userRequestDto.getUserAddresses().isEmpty()))) {
            throw new InvalidUserRequestException("At least one user field is required for update");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    @Override
    public Void deleteUser(Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
        return null;
    }

    private UserResponseDto buildUserResponseEntity(User savedUser) {
        return  new UserResponseDto(
            savedUser.getUserId(),
            savedUser.getUserName(),
            savedUser.getUserPhone(),
            savedUser.getRole()
        );
    }

    private User findUserById(Long userId){
        return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User not found by Id"));
    }

    private void validateUniqueUserFields(String email, String userPhone) {
        if (email != null && userRepository.existsByEmail(email)) {
            throw new DuplicateUserException("Email is already registered");
        }
        if (userPhone != null && userRepository.existsByUserPhone(userPhone)) {
            throw new DuplicateUserException("Phone number is already registered");
        }
    }

    private void validateUniqueUserFields(String email, String userPhone, User currentUser) {
        if (email != null
                && userRepository.existsByEmail(email)
                && !email.equals(currentUser.getEmail())) {
            throw new DuplicateUserException("Email is already registered");
        }
        if (userPhone != null
                && userRepository.existsByUserPhone(userPhone)
                && !userPhone.equals(currentUser.getUserPhone())) {
            throw new DuplicateUserException("Phone number is already registered");
        }
    }

    private List<UserAddress> toUserAddresses(List<UserAddressRequestDto> addressRequests) {
        return addressRequests.stream()
            .map(addressRequest -> {
                UserAddress address = new UserAddress();
                address.setDoorNumber(addressRequest.getDoorNumber());
                address.setStreet(addressRequest.getStreet());
                address.setCity(addressRequest.getCity());
                address.setDistrict(addressRequest.getDistrict());
                address.setCountry(addressRequest.getCountry());
                address.setPincode(addressRequest.getPincode());
                return address;
            })
            .toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDto updateUserRole(Long userId, UpdateUserRoleRequestDto userRoleRequestDto) {
        User targetUser = findUserById(userId);
        Role oldRole = targetUser.getRole();
        Role newRole = userRoleRequestDto.getRole();

        log.info("Processing role change: targetUserId={}, oldRole={}, newRole={}",
                userId, oldRole, newRole);

        validateRoleChange(targetUser, oldRole, newRole);

        if (userRoleRequestDto.getRole() != null) {
            targetUser.setRole(newRole);
        }
        User savedUser = userRepository.save(targetUser);
        log.info("Role change persisted: targetUserId={}, role={}",
                savedUser.getUserId(), savedUser.getRole());
        return buildUserResponseEntity(savedUser);
    }  
    
    private void validateRoleChange(
        User targetUser,
        Role oldRole,
        Role newRole) {

    if (oldRole == Role.ADMIN
            && newRole != Role.ADMIN
            && userRepository.countByRole(Role.ADMIN) <= 1) {

        log.warn("Last ADMIN protection triggered: targetUserId={}, oldRole={}, requestedRole={}",
                targetUser.getUserId(), oldRole, newRole);

        throw new InvalidUserRequestException(
                "At least one ADMIN user must remain");
        }
    }
    
}
