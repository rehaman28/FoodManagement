package com.om.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.om.Exception.OrderNotFoundException;
import com.om.builder.OrderRequestBuilder;
import com.om.builder.OrderResponseBuilder;
import com.om.controller.OrderStatusRequestDto;
import com.om.dao.OrderRepository;
import com.om.dto.ItemResponseDto;
import com.om.dto.OrderItemRequestDto;
import com.om.dto.OrderRequestDto;
import com.om.dto.OrderResponseDto;
import com.om.model.Order;
import com.om.model.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    
    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public OrderResponseDto placeorder(OrderRequestDto orderRequest) {
        double totalPrice = calculateOrderPrice(orderRequest);
        Order order = OrderRequestBuilder.buildOrderResponseFromOrderRequestDto(orderRequest);
        order.setOrderPrice(totalPrice);
        Order savedOrder =orderRepository.save(order);
        OrderResponseDto orderResponseDto = OrderResponseBuilder.buildOrderResponseDtoFromOrder(savedOrder,totalPrice);
        String restauratName = fetchRestaurantNameFromId(savedOrder.getRestaurantId());
        orderResponseDto.setRestaurantName(restauratName);
        return orderResponseDto;

    } 
    @Override
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                        .orElseThrow(()-> new OrderNotFoundException("Order Not Found"));
        OrderResponseDto orderResponseDto = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
        orderResponseDto.setRestaurantName(fetchRestaurantNameFromId(order.getRestaurantId()));
        return orderResponseDto;
    }
    @Override
    public List<OrderResponseDto> getAllOrder() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    OrderResponseDto response = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
                    response.setRestaurantName(fetchRestaurantNameFromId(order.getRestaurantId()));
                    return response;
                })
                .toList();
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> {
                    OrderResponseDto response = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
                    response.setRestaurantName(fetchRestaurantNameFromId(order.getRestaurantId()));
                    return response;
                })
                .toList();
    }
    @Override
    public List<OrderResponseDto> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(order -> {
                    OrderResponseDto response = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
                    response.setRestaurantName(fetchRestaurantNameFromId(order.getRestaurantId()));
                    return response;
                })
                .toList();
    }

    @Override
    public List<OrderResponseDto> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus)
                .stream()
                .map(order -> {
                    OrderResponseDto response = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
                    response.setRestaurantName(fetchRestaurantNameFromId(order.getRestaurantId()));
                    return response;
                })
                .toList();
    } 

    
    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatusRequestDto orderStatusRequestDto) {
        Order order = orderRepository.findById(orderId).
                        orElseThrow(()-> new OrderNotFoundException("Order Not Found"));
        order.setOrderStatus(orderStatusRequestDto.getOrderStatus());
        Order savedOrder = orderRepository.save(order);
        OrderResponseDto orderResponseDto = OrderResponseBuilder.buildOrderResponseDtoFromOrder(savedOrder);
        return orderResponseDto;
    }

    private String fetchRestaurantNameFromId(long restaurantId){
    return restTemplate.getForObject("http://localhost:8001/restaurants/" + restaurantId + "/name", String.class);
    }

    private ItemResponseDto fetchItemsFromRestaurantIdAndItemId(long restaurantId, long itemId){
        String url =
            "http://localhost:8001/restaurants/{restaurantId}/items/{itemId}";
       return restTemplate.getForObject(url,ItemResponseDto.class,restaurantId,itemId);
    }

    private double calculateOrderPrice(OrderRequestDto orderRequestDto){
        double totalPrice =0;
        for (OrderItemRequestDto orderItem : orderRequestDto.getOrderItemsRequest()) {
            ItemResponseDto itemResponseDto = fetchItemsFromRestaurantIdAndItemId(orderRequestDto.getRestaurantId(), orderItem.getItemId());
            double itemPrice = itemResponseDto.getItemPrice();
            long quantity = orderItem.getQuantity();
            double itemTotal = itemPrice * quantity;
            totalPrice = totalPrice+itemTotal;            
        }
        return totalPrice;
    }       
    
}
