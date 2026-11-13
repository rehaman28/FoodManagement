package com.om.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import com.om.builder.OrderRequestBuilder;
import com.om.dto.response.ItemResponseDto;
import com.om.dto.request.OrderRequestDto;
import com.om.dto.response.OrderResponseDto;
import com.om.dto.request.OrderStatusRequestDto;
import com.om.model.Order;
import com.om.model.OrderItem;
import com.om.model.OrderStatus;
import com.om.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final RestaurantValidationService restaurantValidationService;
    private final UserValidationService userValidationService;
    private final OrderPersistenceService orderPersistenceService;

    public OrderServiceImpl(RestaurantValidationService restaurantValidationService,
            UserValidationService userValidationService,
            OrderPersistenceService orderPersistenceService) {
        this.restaurantValidationService = restaurantValidationService;
        this.userValidationService = userValidationService;
        this.orderPersistenceService = orderPersistenceService;
    }

    @Override
    public OrderResponseDto placeorder(OrderRequestDto orderRequest) {
        userValidationService.validateUser(orderRequest.getUserId());
        String restaurantName = restaurantValidationService.getRestaurantName(orderRequest.getRestaurantId());

        Order order = OrderRequestBuilder.buildOrderResponseFromOrderRequestDto(orderRequest);
        BigDecimal totalPrice = calculateOrderPrice(order);
        order.setOrderPrice(totalPrice);
        OrderResponseDto orderResponseDto = orderPersistenceService.saveOrder(order);
        if (!restaurantName.isBlank()) {
            orderResponseDto.setRestaurantName(restaurantName);
        }

        return orderResponseDto;

    }

    @Override
    public OrderResponseDto getOrder(Long orderId) {
        OrderPersistenceService.OrderReadResult orderReadResult = orderPersistenceService.getOrder(orderId);
        OrderResponseDto orderResponseDto = orderReadResult.response();
        orderResponseDto.setRestaurantName(
                restaurantValidationService.getRestaurantName(orderReadResult.restaurantId()));
        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> getAllOrder() {
        return orderPersistenceService.getAllOrder()
                .stream()
                .map(order -> {
                    OrderResponseDto response = order.response();
                    response.setRestaurantName(
                            restaurantValidationService.getRestaurantName(order.restaurantId()));
                    return response;
                })
                .toList();
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        return orderPersistenceService.getOrdersByUserId(userId)
                .stream()
                .map(result -> {
                    OrderResponseDto response = result.response();
                    response.setRestaurantName(
                            restaurantValidationService.getRestaurantName(result.restaurantId()));
                    return response;
                })
                .toList();
    }

    @Override
    public List<OrderResponseDto> getOrdersByRestaurantId(Long restaurantId) {
        String restaurantName = restaurantValidationService.getRestaurantName(restaurantId);
        return orderPersistenceService.getOrdersByRestaurantId(restaurantId).stream()
                .map(order -> {
                    OrderResponseDto response = order.response();
                    response.setRestaurantName(restaurantName);
                    return response;
                })
                .toList();
    }

    @Override
    
    public List<OrderResponseDto> getOrdersByStatus(OrderStatus orderStatus) {
        return orderPersistenceService.getOrdersByStatus(orderStatus)
                .stream()
                .map(order -> {
                    OrderResponseDto response = order.response();
                    response.setRestaurantName(
                            restaurantValidationService.getRestaurantName(order.restaurantId()));
                    return response;
                })
                .toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatusRequestDto orderStatusRequestDto) {
        return orderPersistenceService.updateOrderStatus(orderId, orderStatusRequestDto);
    }

    // Helper Method to calculate the order price
    private BigDecimal calculateOrderPrice(Order order) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : order.getOrderItems()) {
            ItemResponseDto itemResponseDto = restaurantValidationService.getItem(
                    order.getRestaurantId(),
                    orderItem.getItemId());
            BigDecimal unitPrice = itemResponseDto.getItemPrice();
            orderItem.setUnitPriceAtOrder(unitPrice);
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
        }
        return totalPrice;
    }

    // Legacy RestTemplate calls are kept here for reference only; they are not used.
    // The extracted implementation is in com.om.client.legacy.RestTemplateOrderClient.
    // private final RestTemplate restTemplate;
    // public RestTemplateOrderClient(RestTemplate restTemplate) {
    //     this.restTemplate = restTemplate;
    // }
    // private String fetchRestaurantNameFromId(long restaurantId) {
    //     return restTemplate.getForObject(
    //             "http://localhost:8001/restaurants/" + restaurantId + "/name",
    //             String.class);
    // }
    // private ItemResponseDto fetchItemsFromRestaurantIdAndItemId(long
    // restaurantId, long itemId) {
    //     String url = "http://localhost:8001/restaurants/{restaurantId}/items/{itemId}";
    //     return restTemplate.getForObject(url, ItemResponseDto.class, restaurantId, itemId);
    // }

}
