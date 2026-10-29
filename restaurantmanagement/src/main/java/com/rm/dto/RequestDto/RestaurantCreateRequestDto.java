package com.rm.dto.RequestDto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request data for creating a restaurant.")
public class RestaurantCreateRequestDto {

    @NotBlank
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
    @NotNull(message = "Address is required")
    @Schema(description = "Restaurant address.")
    private AddressRequestDto addressRequestDto;

    @Valid
    @NotEmpty(message = "At least one item is required")
    @Schema(description = "Restaurant menu items.")
    private List<ItemRequestDto> itemRequestDto;
}
