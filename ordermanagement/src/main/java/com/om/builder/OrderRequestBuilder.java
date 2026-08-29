package com.om.builder;

import java.util.ArrayList;
import java.util.List;

import com.om.dto.OrderItemRequestDto;
import com.om.dto.OrderRequestDto;
import com.om.model.Order;
import com.om.model.OrderItem;

public class OrderRequestBuilder {

    public static Order buildOrderResponseFromOrderRequestDto(OrderRequestDto orderRequest) {
       return Order.builder()
        .userId(orderRequest.getUserId())
        .orderStatus("Ordered")
        .restaurantId(orderRequest.getRestaurantId())
        .orderPrice(orderRequest.getOrderPrice())
        .orderItems(buildOrderItemsFromOrderRequestDto(orderRequest.getOrderItemsRequest()))
        .build();
    }   

    private static List<OrderItem> buildOrderItemsFromOrderRequestDto(List<OrderItemRequestDto> OrderItemRequestDtoList){
        List<OrderItem> orderItemsList= new ArrayList<>();
        for (OrderItemRequestDto orderItemRequest : OrderItemRequestDtoList) {
            OrderItem orderItem= new OrderItem();
            orderItem.setItemId(orderItemRequest.getItemId());
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItemsList.addLast(orderItem);
        }
        return orderItemsList;
    }    
}
