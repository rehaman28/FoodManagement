package com.om.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderItemResponseDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    "orderItemId",
    "itemId",
    "quantity"
})
@Schema(description = "Response data for an item in an order.")
public class OrderItemResponseDto {
    
    @Schema(description = "Identifier of the order item.")
    private long orderItemId;
    @Schema(description = "Number of units ordered.")
    private long quantity;
    @Schema(description = "Identifier of the menu item.")
    private long itemId;
}
