package com.om.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.om.Exception.InvalidOrderStatusTransitionException;
import com.om.Exception.ItemNotFoundException;
import com.om.Exception.OrderNotFoundException;
import com.om.Exception.RestaurantNotFoundException;
import com.om.builder.OrderRequestBuilder;
import com.om.builder.OrderResponseBuilder;
import com.om.dao.OrderRepository;
import com.om.dto.ItemResponseDto;
import com.om.dto.OrderItemRequestDto;
import com.om.dto.OrderRequestDto;
import com.om.dto.OrderResponseDto;
import com.om.dto.OrderStatusRequestDto;
import com.om.model.Order;
import com.om.model.OrderStatus;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public OrderResponseDto placeorder(OrderRequestDto orderRequest) {
        String restaurantName;
        try {
            restaurantName = fetchRestaurantNameFromId(orderRequest.getRestaurantId());
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RestaurantNotFoundException("Restaurant was not found with Id: "
                    + orderRequest.getRestaurantId());
        }

        double totalPrice = calculateOrderPrice(orderRequest);
        Order order = OrderRequestBuilder.buildOrderResponseFromOrderRequestDto(orderRequest);
        order.setOrderPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);
        OrderResponseDto orderResponseDto = OrderResponseBuilder.buildOrderResponseDtoFromOrder(savedOrder, totalPrice);
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
        orderResponseDto.setRestaurantName(fetchRestaurantNameFromId(order.getRestaurantId()));
        return orderResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found"));
        OrderStatus currentOrderStatus =order.getOrderStatus();
        OrderStatus requestedOrderStatus =orderStatusRequestDto.getOrderStatus();

        if (!isValidTransition(currentOrderStatus, requestedOrderStatus)) {

            throw new InvalidOrderStatusTransitionException(
                    "Invalid order status transition from "
                            + currentOrderStatus
                            + " to "
                            + requestedOrderStatus);

        }

        order.setOrderStatus(orderStatusRequestDto.getOrderStatus());
        Order savedOrder = orderRepository.save(order);
        return OrderResponseBuilder.buildOrderResponseDtoFromOrder(savedOrder);
    }

    // RestTemplate for Inter-service Communications
    private String fetchRestaurantNameFromId(long restaurantId) {
        return restTemplate.getForObject("http://localhost:8001/restaurants/" + restaurantId + "/name", String.class);
    }

    private ItemResponseDto fetchItemsFromRestaurantIdAndItemId(long restaurantId, long itemId) {
        String url = "http://localhost:8001/restaurants/{restaurantId}/items/{itemId}";
        return restTemplate.getForObject(url, ItemResponseDto.class, restaurantId, itemId);
    }

    // Helper Method to calculate the order price
    private double calculateOrderPrice(OrderRequestDto orderRequestDto) {
        double totalPrice = 0;
        for (OrderItemRequestDto orderItem : orderRequestDto.getOrderItemsRequest()) {
            try {
                ItemResponseDto itemResponseDto = fetchItemsFromRestaurantIdAndItemId(orderRequestDto.getRestaurantId(),
                        orderItem.getItemId());
                double itemPrice = itemResponseDto.getItemPrice();
                long quantity = orderItem.getQuantity();
                double itemTotal = itemPrice * quantity;
                totalPrice = totalPrice + itemTotal;

            } catch (HttpClientErrorException.NotFound ex) {
                throw new ItemNotFoundException("Item id " + orderItem.getItemId() + " not found in Restaurant "
                        + orderRequestDto.getRestaurantId());
            }
        }
        return totalPrice;
    }

    //Helper Method to validate if the transition was valid & allowed
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
