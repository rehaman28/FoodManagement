package com.om.dto;

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
public class OrderStatusRequestDto {

    @JsonProperty("OrderStatus")
    @NotNull (message = "Order status is Required")
    private OrderStatus orderStatus;

}
