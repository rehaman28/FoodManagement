package com.om.dto;

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
public class OrderItemResponseDto {
    
    private long orderItemId;
    private long itemId;
    private int quantity;

}
