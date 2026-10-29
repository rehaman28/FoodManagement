package com.rm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    "itemId",
    "itemName",
    "itemPrice",
    "itemCategory",
    "itemType",
    "itemRating"
})
@Schema(description = "Response data describing a menu item.")
public class ItemResponseDto {

    @Schema(description = "Identifier of the menu item.")
    private long itemId;
    @Schema(description = "Name of the menu item.")
    private String itemName;
    @Schema(description = "Price of the menu item.")
    private Double itemPrice;
    @Schema(description = "Category of the menu item.")
    private String itemCategory;
    @Schema(description = "Type of the menu item.")
    private String itemType;
    @Schema(description = "Rating of the menu item from 0.0 to 5.0.")
    private Double itemRating;
}
