package com.om.service.impl;

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
        Order savedOrder = orderRepository.save(order);

        return OrderResponseBuilder
                .buildOrderResponseDtoFromOrder(savedOrder);

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
}
