package com.om.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.web.client.RestTemplate;

import com.om.dto.OrderRequestDto;
import com.om.dto.OrderResponseDto;
import com.om.model.Order;
import com.om.model.OrderItem;

import com.om.dto.OrderItemRequestDto;
import com.om.dto.OrderItemResponseDto;

public class OrderBuilder {

    private final RestTemplate restTemplate;

    public OrderBuilder(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public OrderBuilder() {
         this.restTemplate = new RestTemplate();
    }


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
            BeanUtils.copyProperties(orderItemRequest, orderItem);
            orderItemsList.addLast(orderItem);
        }
        return orderItemsList;
    }

    public static OrderResponseDto buildOrderResponseDtoFromOrder(Order order){
        OrderBuilder orderBuilder = new OrderBuilder();
        return OrderResponseDto.builder()
         .orderId(order.getOrderId())
         .orderStatus(order.getOrderStatus())
         .orderPrice(order.getOrderPrice())
         .orderItems(buildOrderItemsResponseFromOrder(order.getOrderItems()))  
         .restaurantName(orderBuilder.fetchRestaurantNameFromId(order.getRestaurantId()))
        .build();

    }

    private static List<OrderItemResponseDto> buildOrderItemsResponseFromOrder(List<OrderItem> orderItems){
        List<OrderItemResponseDto> orderResponseDtosList = new ArrayList<>();
        for (OrderItem  orderItem : orderItems) {
            OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
            BeanUtils.copyProperties(orderItem, orderItemResponseDto);
            orderResponseDtosList.add(orderItemResponseDto);
        }
        return orderResponseDtosList;
    }

    private String fetchRestaurantNameFromId(long restaurantId){
       return restTemplate.getForObject("http://localhost:8001/restaurants/getrestaurant/name/"+ restaurantId, String.class);
    }
}
