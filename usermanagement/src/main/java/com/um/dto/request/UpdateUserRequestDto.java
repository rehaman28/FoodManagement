package com.um.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
@Schema(description = "Optional user profile fields to update.")
public class UpdateUserRequestDto {
    
    @Size(min = 3,max = 50)
    @Schema(description = "User name.")
    private String userName;

    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone Number must contain 10 digits"
    )

    @Schema(description = "User phone number.")
    private String userPhone;
    
    @Email 
    @Schema(description = "User email address.")
    private String email;

    @Size(min = 8, max = 100) 
    @Schema(description = "User password.")
    private String password;
    
    @Valid   
    @Schema(description = "User addresses.")
    private List<UserAddressRequestDto> userAddresses;

}
