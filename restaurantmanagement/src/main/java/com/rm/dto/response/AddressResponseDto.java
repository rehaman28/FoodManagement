package com.rm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response data describing a restaurant address.")
public class AddressResponseDto {

    @Schema(description = "Address landmark.")
    private String landmark;
    @Schema(description = "City name.")
    private String city;
    @Schema(description = "State name.")
    private String state;
    @Schema(description = "Six-digit postal code.")
    private String pincode;
    
}
