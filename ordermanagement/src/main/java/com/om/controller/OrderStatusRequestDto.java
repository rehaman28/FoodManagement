package com.om.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.om.model.OrderStatus;

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
    private OrderStatus orderStatus;

}
