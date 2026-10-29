package com.om.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response data describing a menu item.")
public class ItemResponseDto {
    
    @Schema(description = "Name of the menu item.")
    private String itemName;
    @Schema(description = "Price of the menu item.")
    private double itemPrice;
    @Schema(description = "Category of the menu item.")
    private String itemCategory;
    @Schema(description = "Type of the menu item.")
    private String itemType;
}
