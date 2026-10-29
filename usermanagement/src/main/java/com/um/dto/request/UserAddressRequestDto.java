package com.um.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
@Schema(description = "Address data associated with a user.")
public class UserAddressRequestDto {
    
    @Schema(description = "Door or building number.")
    private String doorNumber;
    @NotBlank 
    @Schema(description = "Street name.")
    private String street;
    @NotBlank 
    @Schema(description = "City name.")
    private String city;
    @NotBlank 
    @Schema(description = "District name.")
    private String district;
    @NotBlank 
    @Schema(description = "Country name.")
    private String country;

    @NotBlank(message = "Pincode is required")
    @Pattern(
        regexp = "^\\d{6}$",
        message = "Pincode must contain 6 digits"
    )
    @Schema(description = "Six-digit postal code.")
    private String pincode;
    
}
