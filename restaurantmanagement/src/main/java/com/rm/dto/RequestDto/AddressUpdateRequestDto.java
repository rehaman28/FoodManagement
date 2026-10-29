package com.rm.dto.RequestDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Optional address fields to update for a restaurant.")
public class AddressUpdateRequestDto {

    @Size(min = 1, max = 100)
    @Schema(description = "Address landmark.")
    private String landmark;

    @Size(min = 2, max = 50)
    @Schema(description = "City name.")
    private String city;

    @Size(min = 2, max = 50)
    @Schema(description = "State name.")
    private String state;

    @Pattern(
        regexp = "^\\d{6}$",
        message = "Pincode must contain 6 digits"
    )
    @Schema(description = "Six-digit postal code.")
    private String pincode;
}
