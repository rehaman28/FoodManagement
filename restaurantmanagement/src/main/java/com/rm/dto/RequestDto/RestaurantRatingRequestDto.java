package com.rm.dto.RequestDto;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Request data for rating a restaurant.")
public class RestaurantRatingRequestDto {
    
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Schema(description = "Restaurant rating from 0.0 to 5.0.")
    private Double rating;
}
