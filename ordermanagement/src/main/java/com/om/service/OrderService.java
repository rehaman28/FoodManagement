package com.om.service;

import java.util.List;

import com.om.controller.OrderStatusRequestDto;
import com.om.dto.OrderRequestDto;
import com.om.dto.OrderResponseDto;
import com.om.model.OrderStatus;

public interface OrderService {

    OrderResponseDto placeorder(OrderRequestDto orderRequest);

    OrderResponseDto getOrder(Long orderId);

    List<OrderResponseDto> getAllOrder();

    List<OrderResponseDto> getOrdersByUserId(Long userId);

    List<OrderResponseDto> getOrdersByRestaurantId(Long restaurantId);

    List<OrderResponseDto> getOrdersByStatus(OrderStatus orderStatus);

	OrderResponseDto updateOrderStatus(Long orderId, OrderStatusRequestDto orderStatus);

}
