package com.om.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.om.builder.OrderRequestBuilder;
import com.om.builder.OrderResponseBuilder;
import com.om.dao.OrderRepository;
import com.om.dto.ItemResponseDto;
import com.om.dto.OrderItemRequestDto;
import com.om.dto.OrderRequestDto;
import com.om.dto.OrderResponseDto;
import com.om.model.Order;

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
        orderRequest.setOrderPrice(totalPrice);
        Order order = OrderRequestBuilder.buildOrderResponseFromOrderRequestDto(orderRequest);
        Order savedOrder =orderRepository.save(order);
        OrderResponseDto orderResponseDto = OrderResponseBuilder.buildOrderResponseDtoFromOrder(savedOrder);
        String restauratName = fetchRestaurantNameFromId(savedOrder.getRestaurantId());
        orderResponseDto.setRestaurantName(restauratName);
        return orderResponseDto;

    } 

    private String fetchRestaurantNameFromId(long restaurantId){
       return restTemplate.getForObject("http://localhost:8001/restaurants/getrestaurant/name/"+ restaurantId, String.class);
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
