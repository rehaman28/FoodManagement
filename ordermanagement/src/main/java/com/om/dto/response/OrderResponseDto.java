package com.om.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.om.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({
    "orderId",
    "restaurantName",
    "orderPrice",
    "orderStatus",
    "orderItems"
})
@Schema(description = "Response data describing a placed order.")
public class OrderResponseDto {

    @Schema(description = "Identifier of the order.")
    private Long orderId;
    // private long userId;
    @Schema(description = "Name of the restaurant.")
    private String restaurantName;
    @Schema(description = "Total price of the order.")
    private double orderPrice;
    @Schema(description = "Current order status.")
    private OrderStatus orderStatus;
    /*
     * Response DTO should contain response DTOs, not entities.
     * This ensures the API contract is independent of database structure and
     * allows selective field exposure without leaking internal entity details.
     */
    @Schema(description = "Items included in the order.")
    private List<OrderItemResponseDto> orderItems;
}
