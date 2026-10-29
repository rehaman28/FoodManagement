package com.um.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Request data for creating a user account.")
public class UserRequestDto {

    // @Positive 
    // private long userId;

    @NotBlank @Size(min = 3,max = 50)
    @Schema(description = "User name.")
    private String userName;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone Number must contain 10 digits"
    )
    @Schema(description = "User phone number.")
    private String userPhone;
    
    @Email @NotBlank 
    @Schema(description = "User email address.")
    private String email;

    @NotBlank
    @Size(min = 8, max = 100) 
    @Schema(description = "User password.")
    private String password;
    
    @NotEmpty
    @Valid   
    @Schema(description = "User addresses.")
    private List<UserAddressRequestDto> userAddresses;
}