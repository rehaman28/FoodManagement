package com.um.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.um.dao.UserRepository;
import com.um.dto.UserRequestDto;
import com.um.dto.UserResponseDto;
import com.um.exception.UserNotFoundException;
import com.um.model.User;

@Service 
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<UserResponseDto> addUsers(UserRequestDto userRequestDto) {
        User user = new  User();
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setUserPhone(userRequestDto.getUserPhone());
        user.setPassword(userRequestDto.getPassword());
        user.setUserAddresses(userRequestDto.getUserAddresses());
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
            UserRequestDto userRequestDto) {

        User user = findUserById(userId);
        if (userRequestDto.getEmail() != null) {
            user.setEmail(userRequestDto.getEmail());
        }
        if (userRequestDto.getPassword() != null) {
            user.setPassword(userRequestDto.getPassword());
        }
        if (userRequestDto.getUserName() != null) {
            user.setUserName(userRequestDto.getUserName());
        }
        if (userRequestDto.getUserPhone() != null) {
            user.setUserPhone(userRequestDto.getUserPhone());
        }
        if (userRequestDto.getUserAddresses() != null) {
            user.setUserAddresses(userRequestDto.getUserAddresses());
        }

        User savedUser = userRepository.save(user);

        UserResponseDto userResponse = buildUserResponseEntity(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

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
   
    
}
