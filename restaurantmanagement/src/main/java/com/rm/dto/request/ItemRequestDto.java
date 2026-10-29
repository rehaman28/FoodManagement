package com.rm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Menu item data for a restaurant.")
public class ItemRequestDto {

    @NotBlank
    @Schema(description = "Name of the menu item.")
    private String itemName;

    @NotNull
    @Positive
    @Schema(description = "Price of the menu item.")
    private Double itemPrice;

    @NotBlank
    @Schema(description = "Category of the menu item.")
    private String itemCategory;

    @NotBlank
    @Schema(description = "Type of the menu item.")
    private String itemType;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Schema(description = "Rating of the menu item from 0.0 to 5.0.")
    private Double itemRating;
}
