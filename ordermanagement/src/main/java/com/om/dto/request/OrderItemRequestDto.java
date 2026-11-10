package com.om.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request data for an item and quantity in an order.")
public class OrderItemRequestDto {

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    @Schema(description = "Number of units ordered.")
    private Long quantity;
    @NotNull(message = "Item ID is required")
    @Positive(message = "Item ID must be greater than zero")
    @Schema(description = "Identifier of the menu item.")
    private Long itemId;
}
