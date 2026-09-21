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

/**
 * UpdateUserRequestDto
 */
@AllArgsConstructor 
@Data 
@NoArgsConstructor 
public class UpdateUserRequestDto {
    
    @NotBlank @Size(min = 3,max = 50)
    private String userName;
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
