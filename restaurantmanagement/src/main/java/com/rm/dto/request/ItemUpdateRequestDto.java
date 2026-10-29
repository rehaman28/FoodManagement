package com.rm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Optional menu item fields to update.")
public class ItemUpdateRequestDto {

    @Size(min = 1, max = 100)
    @Schema(description = "Name of the menu item.")
    private String itemName;

    @Positive
    @Schema(description = "Price of the menu item.")
    private Double itemPrice;

    @Size(min = 1, max = 50)
    @Schema(description = "Category of the menu item.")
    private String itemCategory;

    @Size(min = 1, max = 50)
    @Schema(description = "Type of the menu item.")
    private String itemType;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Schema(description = "Rating of the menu item from 0.0 to 5.0.")
    private Double itemRating;
}
