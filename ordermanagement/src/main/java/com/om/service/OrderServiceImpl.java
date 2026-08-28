package com.om.service;


import org.springframework.stereotype.Service;

import com.om.builder.OrderBuilder;
import com.om.dao.OrderRepository;
import com.om.dto.OrderRequestDto;
import com.om.dto.OrderResponseDto;
import com.om.model.Order;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDto placeorder(OrderRequestDto orderRequest) {
        Order order = OrderBuilder.buildOrderResponseFromOrderRequestDto(orderRequest);
        Order savedOrder =orderRepository.save(order);
        return  OrderBuilder.buildOrderResponseDtoFromOrder(savedOrder);
    }   
    
}
