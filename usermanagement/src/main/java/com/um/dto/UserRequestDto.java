package com.um.dto;

import java.util.List;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    
    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone Number must contain 10 digits"
    )
    private String userPhone;
    
    @Email @NotBlank 
    private String email;

    @NotBlank
    @Size(min = 8, max = 100) 
    private String password;
    
    @NotEmpty
    @Valid   
    private List<UserAddressRequestDto> userAddresses;
}