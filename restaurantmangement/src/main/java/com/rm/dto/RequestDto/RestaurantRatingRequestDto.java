package com.rm.dto.RequestDto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter  
public class RestaurantRatingRequestDto {
    
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double rating;
}
