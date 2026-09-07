package com.om.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    @Positive 
    private long userId;
    @Positive 
    private long restaurantId;
    
    @JsonProperty("OrderingItems")
    @NotEmpty 
    @Valid 
    private List<OrderItemRequestDto> orderItemsRequest;    
}
