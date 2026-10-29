package com.rm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
@Schema(description = "Optional restaurant fields to update.")
public class RestaurantUpdateRequestDto {

    @Size(min = 3, max = 50)
    @Schema(description = "Name of the restaurant.")
    private String restaurantName;

    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone Number must contain 10 digits"
    )
    @Schema(description = "Restaurant phone number.")
    private String phoneNumber;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    @Schema(description = "Restaurant rating from 0.0 to 5.0.")
    private Double rating;

    @Valid
    @Schema(description = "Restaurant address.")
    private AddressUpdateRequestDto addressRequestDto;
}
