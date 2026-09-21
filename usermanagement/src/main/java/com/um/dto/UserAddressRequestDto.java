package com.um.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class UserAddressRequestDto {
    
    private String doorNumber;
    @NotBlank 
    private String street;
    @NotBlank 
    private String city;
    @NotBlank 
    private String district;
    @NotBlank 
    private String country;

    @NotBlank(message = "Pincode is required")
    @Pattern(
        regexp = "^\\d{6}$",
        message = "Pincode must contain 6 digits"
    )
    private String pincode;
    
}
