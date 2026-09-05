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
        OrderResponseDto placedOrder = orderService.placeorder(orderRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(placedOrder);
    }

    //Get Order by ID
    //Get Order by ID --ADMIN role
    //Get Orders by User
    //Get Orders by Restaurant
    //Update Order Status
    //Cancel order patch or delete
    //Get Orders by Status
    //Add Item to Existing Order
    //Update Quantity
    //Remove Item From Order
    
}
