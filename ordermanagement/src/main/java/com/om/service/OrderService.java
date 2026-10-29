package com.om.service;

import java.util.List;

import com.om.dto.request.OrderRequestDto;
import com.om.dto.response.OrderResponseDto;
import com.om.dto.request.OrderStatusRequestDto;
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
