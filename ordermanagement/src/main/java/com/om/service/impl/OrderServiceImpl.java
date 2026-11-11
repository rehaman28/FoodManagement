package com.om.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.client.RestTemplate;

import com.om.exception.OrderNotFoundException;
import com.om.builder.OrderRequestBuilder;
import com.om.builder.OrderResponseBuilder;
import com.om.dao.OrderRepository;
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

    private final OrderRepository orderRepository;
    private final RestaurantValidationService restaurantValidationService;
    private final UserValidationService userValidationService;
    private final OrderPersistenceService orderPersistenceService;

    // private final RestTemplate restTemplate;
    // public OrderServiceImpl(OrderRepository orderRepository, RestTemplate
    // restTemplate) {
    // this.orderRepository = orderRepository;
    // this.restTemplate = restTemplate;
    // }

    public OrderServiceImpl(OrderRepository orderRepository,
            RestaurantValidationService restaurantValidationService,
            UserValidationService userValidationService,
        OrderPersistenceService orderPersistenceService) {
        this.orderRepository = orderRepository;
        this.restaurantValidationService = restaurantValidationService;
        this.userValidationService = userValidationService;
        this.orderPersistenceService= orderPersistenceService;
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
    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found"));
        OrderResponseDto orderResponseDto = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
        orderResponseDto.setRestaurantName(restaurantValidationService.getRestaurantName(order.getRestaurantId()));
        return orderResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrder() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    OrderResponseDto response = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
                    response.setRestaurantName(
                            restaurantValidationService.getRestaurantName(order.getRestaurantId()));
                    return response;
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> {
                    OrderResponseDto response = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
                    response.setRestaurantName(
                            restaurantValidationService.getRestaurantName(order.getRestaurantId()));
                    return response;
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByRestaurantId(Long restaurantId) {
        String restaurantName = restaurantValidationService.getRestaurantName(restaurantId);

        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for restaurant with Id: " + restaurantId);
        }

        return orders.stream()
                .map(order -> {
                    OrderResponseDto response = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
                    response.setRestaurantName(restaurantName);
                    return response;
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus)
                .stream()
                .map(order -> {
                    OrderResponseDto response = OrderResponseBuilder.buildOrderResponseDtoFromOrder(order);
                    response.setRestaurantName(
                            restaurantValidationService.getRestaurantName(order.getRestaurantId()));
                    return response;
                })
                .toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatusRequestDto orderStatusRequestDto) {
        return orderPersistenceService.updateOrderStatus(orderId, orderStatusRequestDto);
    }

    // RestTemplate for Inter-service Communications
    // Moving The below method to FeignClient Service Discovery approach
    // private String fetchRestaurantNameFromId(long restaurantId) {
    // return restTemplate.getForObject("http://localhost:8001/restaurants/" +
    // restaurantId + "/name", String.class);
    // }

    // private ItemResponseDto fetchItemsFromRestaurantIdAndItemId(long
    // restaurantId, long itemId) {
    // String url =
    // "http://localhost:8001/restaurants/{restaurantId}/items/{itemId}";
    // return restTemplate.getForObject(url, ItemResponseDto.class, restaurantId,
    // itemId);
    // }

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
    
}
