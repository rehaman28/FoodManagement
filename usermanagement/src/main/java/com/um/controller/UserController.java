package com.um.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.um.dto.UserRequestDto;
import com.um.dto.UserResponseDto;
import com.um.service.UserService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/users")
public class UserController {

    private  final UserService userService;
    

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> AddUsers(@RequestBody UserRequestDto userRequestDto) {
        return userService.addUsers(userRequestDto);
    }

    @GetMapping 
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping ("/{Userid}")
    public ResponseEntity<UserResponseDto> getUserbyId(@PathVariable (name = "Userid" ) Long userId) {
        return userService.getUsersById(userId);
    }

    @PatchMapping ("/{Userid}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable (name = "Userid" ) Long userId, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(userId,userRequestDto);
    }

    @DeleteMapping ("/{Userid}")
    public ResponseEntity<Void> deleteUser(@PathVariable (name = "Userid" ) Long userId){
        return  userService.deleteUser(userId);
    }
    
    
}
