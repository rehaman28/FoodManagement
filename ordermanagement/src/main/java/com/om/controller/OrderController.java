package com.om.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.om.dto.OrderRequestDto;
import com.om.dto.OrderResponseDto;
import com.om.service.OrderService;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

   
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeorder")    
    public ResponseEntity<OrderResponseDto>placeOrder(@RequestBody OrderRequestDto orderRequest)
    {
        System.out.println("Request received: " + orderRequest);
        System.out.println("OrderingItems: " + orderRequest.getOrderItemsRequest());
        orderRequest.getOrderItemsRequest().forEach(item -> 
            System.out.println("Item quantity: " + item.getQuantity())
        );

        OrderResponseDto placedOrder = orderService.placeorder(orderRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(placedOrder);
    }
    
}
