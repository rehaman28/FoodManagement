package com.om.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.builder.OrderResponseBuilder;
import com.om.dao.OrderRepository;
import com.om.dto.request.OrderStatusRequestDto;
import com.om.dto.response.OrderResponseDto;
import com.om.exception.InvalidOrderStatusTransitionException;
import com.om.exception.OrderNotFoundException;
import com.om.model.Order;
import com.om.model.OrderStatus;

@Service 
@Transactional 
public class OrderPersistenceService {

    private final OrderRepository orderRepository;
    
    public OrderPersistenceService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponseDto updateOrderStatus(Long orderId,
            OrderStatusRequestDto requestedOrderStatusDto) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found"));

        OrderStatus currentOrderStatus = order.getOrderStatus();
        OrderStatus requestedOrderStatus = requestedOrderStatusDto.getOrderStatus();

        if (!isValidTransition(currentOrderStatus, requestedOrderStatus)) {

            throw new InvalidOrderStatusTransitionException(
                    "Invalid order status transition from "
                            + currentOrderStatus
                            + " to "
                            + requestedOrderStatus);

        }
        order.setOrderStatus(requestedOrderStatus);
        return saveOrder(order);

    }
    
    public OrderResponseDto saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        return OrderResponseBuilder.buildOrderResponseDtoFromOrder(savedOrder);
    }

    // Helper Method to validate if the transition was valid & allowed
    private boolean isValidTransition(OrderStatus currentStatus,
            OrderStatus requestedStatus) {

        if (currentStatus == requestedStatus) {
            return true;
        }
        return switch (currentStatus) {
            case Ordered -> requestedStatus == OrderStatus.Accepted
                    || requestedStatus == OrderStatus.Cancelled;
            case Accepted -> requestedStatus == OrderStatus.Preparing
                    || requestedStatus == OrderStatus.Cancelled;
            case Preparing -> requestedStatus == OrderStatus.OutForDelivery
                    || requestedStatus == OrderStatus.Cancelled;

            case OutForDelivery ->
                requestedStatus == OrderStatus.Delivered;

            case Delivered, Cancelled -> false;

        };

    }

    @Transactional(readOnly = true)
    public OrderReadResult getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found"));
        return new OrderReadResult(
                OrderResponseBuilder.buildOrderResponseDtoFromOrder(order),
                order.getRestaurantId());
    }

    @Transactional(readOnly = true)
    public List<OrderReadResult> getAllOrder() {
        return  orderRepository.findAll()
                    .stream()
                    .map(this::toReadOrderResult)
                    .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderReadResult> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                    .stream()
                    .map(this::toReadOrderResult)
                    .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderReadResult> getOrdersByRestaurantId(Long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for restaurant with Id: " + restaurantId);
        }
        return orders.stream()
                .map(this::toReadOrderResult)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderReadResult> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus)
        .stream()
        .map(this::toReadOrderResult)
        .toList();
    }

    private  OrderReadResult toReadOrderResult(Order order){
        return  new OrderReadResult(OrderResponseBuilder.buildOrderResponseDtoFromOrder(order), order.getRestaurantId());
    }

    public record OrderReadResult(
            OrderResponseDto response,
            long restaurantId) {
    }
}
