package com.rm.dto.RequestDto;

import java.util.List;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantUpdateRequestDto {

    @NotBlank
    @Size(min = 3,max = 50)
    private String restaurantName;

    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone Number must contain 10 digits"
    )
    private String phoneNumber;
    
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating;
    private AddressRequestDto addressRequestDto;
    private List<ItemRequestDto> itemRequestDto;

}
