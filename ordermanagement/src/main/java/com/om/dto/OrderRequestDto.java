package com.om.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    private long userId;
    private long restaurantId;
    private double orderPrice;
    @JsonProperty("OrderingItems")
    private List<OrderItemRequestDto> orderItemsRequest;    
}
