package com.om.builder;

import java.util.ArrayList;
import java.util.List;

import com.om.dto.OrderItemResponseDto;
import com.om.dto.OrderResponseDto;
import com.om.model.Order;
import com.om.model.OrderItem;

public class OrderResponseBuilder {

    public static OrderResponseDto buildOrderResponseDtoFromOrder(Order order, double price) {
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .orderPrice(price)
                .orderItems(buildOrderItemsResponseFromOrder(order.getOrderItems()))
                .build();

    }

    private static List<OrderItemResponseDto> buildOrderItemsResponseFromOrder(List<OrderItem> orderItems) {
        List<OrderItemResponseDto> orderResponseDtosList = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
            orderItemResponseDto.setItemId(orderItem.getItemId());
            orderItemResponseDto.setOrderItemId(orderItem.getOrderItemId());
            orderItemResponseDto.setQuantity(orderItem.getQuantity());
            orderResponseDtosList.add(orderItemResponseDto);
        }
        return orderResponseDtosList;
    }

    public static OrderResponseDto buildOrderResponseDtoFromOrder(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .orderPrice(order.getOrderPrice())
                .orderItems(buildOrderItemsResponseFromOrder(order.getOrderItems()))
                .build();
    }
}
