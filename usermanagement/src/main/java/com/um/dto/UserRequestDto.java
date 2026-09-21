package com.um.dto;

import java.util.List;

import com.um.model.UserAddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class UserRequestDto {

    // @Positive 
    // private long userId;

    @NotBlank @Size(min = 3,max = 50)
    private String userName;
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone Number must contain 10 digits"
    )
    private String userPhone;
    @NotNull
    private String email;
    @NotNull 
    private String password;
    @NotNull 
    private List<UserAddress> userAddresses;
}