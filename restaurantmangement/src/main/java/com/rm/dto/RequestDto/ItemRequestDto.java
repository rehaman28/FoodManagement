package com.rm.dto.RequestDto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

    @NotBlank
    private String itemName;

    @Positive
    private Double itemPrice;

    @NotBlank
    private String itemCategory;
    
    @NotBlank
    private String itemType;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double itemRating;
}
