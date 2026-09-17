package com.um.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.um.dto.UserRequestDto;
import com.um.dto.UserResponseDto;


public interface UserService {

    ResponseEntity<UserResponseDto> addUsers(UserRequestDto userRequestDto);

    ResponseEntity<List<UserResponseDto>> getUsers();

    ResponseEntity<UserResponseDto> getUsersById(Long userId);

    ResponseEntity<UserResponseDto> updateUser(Long userId, UserRequestDto userRequestDto);

    ResponseEntity<Void> deleteUser(Long userId);

}
