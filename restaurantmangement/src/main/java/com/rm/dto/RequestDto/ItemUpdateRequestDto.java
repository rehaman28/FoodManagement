package com.rm.dto.RequestDto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateRequestDto {

    @Size(min = 1, max = 100)
    private String itemName;

    @Positive
    private Double itemPrice;

    @Size(min = 1, max = 50)
    private String itemCategory;

    @Size(min = 1, max = 50)
    private String itemType;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double itemRating;
}
