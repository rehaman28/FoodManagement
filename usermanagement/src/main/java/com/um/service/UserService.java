package com.um.service;

import java.util.List;

import com.um.dto.request.UpdateUserRequestDto;
import com.um.dto.request.UserRequestDto;
import com.um.dto.response.UserResponseDto;


public interface UserService {

    UserResponseDto addUsers(UserRequestDto userRequestDto);

    List<UserResponseDto> getUsers();

    UserResponseDto getUsersById(Long userId);

    UserResponseDto updateUser(Long userId, UpdateUserRequestDto userRequestDto);

    Void deleteUser(Long userId);

}
