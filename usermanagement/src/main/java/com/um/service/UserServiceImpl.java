package com.um.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.um.dao.UserRepository;
import com.um.dto.UpdateUserRequestDto;
import com.um.dto.UserAddressRequestDto;
import com.um.dto.UserRequestDto;
import com.um.dto.UserResponseDto;
import com.um.exception.DuplicateUserException;
import com.um.exception.InvalidUserRequestException;
import com.um.exception.UserNotFoundException;
import com.um.model.User;
import com.um.model.UserAddress;

@Service 
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder  passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public UserResponseDto updateUser(Long userId,
            UpdateUserRequestDto userRequestDto) {
        validateUpdateRequest(userRequestDto);

        User user = findUserById(userId);
        validateUniqueUserFields(
            userRequestDto.getEmail(),
            userRequestDto.getUserPhone(),
            user
        );

        if (userRequestDto.getEmail() != null) {
            user.setEmail(userRequestDto.getEmail());
        }
        if (userRequestDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        }
        if (userRequestDto.getUserName() != null) {
            user.setUserName(userRequestDto.getUserName());
        }
        if (userRequestDto.getUserPhone() != null) {
            user.setUserPhone(userRequestDto.getUserPhone());
        }
        if (userRequestDto.getUserAddresses() != null) {
            user.setUserAddresses(toUserAddresses(userRequestDto.getUserAddresses()));
        }

        User savedUser = userRepository.save(user);

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
            savedUser.getUserPhone()
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
   
    
}
