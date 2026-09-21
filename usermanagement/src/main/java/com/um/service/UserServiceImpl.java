package com.um.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.um.dao.UserRepository;
import com.um.dto.UpdateUserRequestDto;
import com.um.dto.UserAddressRequestDto;
import com.um.dto.UserRequestDto;
import com.um.dto.UserResponseDto;
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
    public ResponseEntity<UserResponseDto> addUsers(UserRequestDto userRequestDto) {
        User user = new  User();
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setUserPhone(userRequestDto.getUserPhone());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setUserAddresses(toUserAddresses(userRequestDto.getUserAddresses()));
        User savedUser = userRepository.save(user);

        UserResponseDto userResponse = buildUserResponseEntity(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }


    @Override
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> userResponse = userRepository.findAll()
        .stream()
        .map(this::buildUserResponseEntity)
        .toList();
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Override
    public ResponseEntity<UserResponseDto> getUsersById(Long userId) {
        User user = findUserById(userId);
        UserResponseDto userResponseDto = buildUserResponseEntity(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @Override
    public ResponseEntity<UserResponseDto> updateUser(Long userId,
            UpdateUserRequestDto userRequestDto) {

        User user = findUserById(userId);
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
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);

    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
        return  ResponseEntity.noContent().build();
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
