package com.um.service;

import java.util.List;

import com.um.dto.UpdateUserRequestDto;
import com.um.dto.UserRequestDto;
import com.um.dto.UserResponseDto;


public interface UserService {

    UserResponseDto addUsers(UserRequestDto userRequestDto);

    List<UserResponseDto> getUsers();

    UserResponseDto getUsersById(Long userId);

    UserResponseDto updateUser(Long userId, UpdateUserRequestDto userRequestDto);

    Void deleteUser(Long userId);

}
