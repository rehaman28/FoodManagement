package com.om.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request data for an item and quantity in an order.")
public class OrderItemRequestDto {

    @Positive 
    @Schema(description = "Number of units ordered.")
    private long quantity;
    @Positive 
    @Schema(description = "Identifier of the menu item.")
    private long itemId;
}
