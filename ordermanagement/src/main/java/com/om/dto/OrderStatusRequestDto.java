package com.om.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.om.model.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderStatusRequestDto
 */
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Schema(description = "Request data for changing an order status.")
public class OrderStatusRequestDto {

    @JsonProperty("OrderStatus")
    @NotNull (message = "Order status is Required")
    @Schema(description = "Current order status.")
    private OrderStatus orderStatus;

}
