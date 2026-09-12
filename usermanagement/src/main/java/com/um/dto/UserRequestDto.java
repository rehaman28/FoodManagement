package com.um.dto;

import java.util.List;

import com.um.model.UserAddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class UserRequestDto {

    private long userId;
    private String userName;
    private String userPhone;
    private String email;
    private String password;
    private List<UserAddress> userAddresses;
}