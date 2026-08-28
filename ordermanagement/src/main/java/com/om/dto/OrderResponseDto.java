package com.om.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
public class OrderResponseDto {

    private long orderId;
    // private long userId;
    private String restaurantName;
    private double orderPrice;
    private String orderStatus;
    /*
     * Response DTO should contain response DTOs, not entities.
     * This ensures the API contract is independent of database structure and
     * allows selective field exposure without leaking internal entity details.
     */
    private List<OrderItemResponseDto> orderItems;
}
