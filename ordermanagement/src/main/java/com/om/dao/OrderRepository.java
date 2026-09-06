package com.om.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.om.model.Order;
import com.om.model.OrderStatus;

/**
 * OrderRepository
 */
public interface OrderRepository extends JpaRepository<Order,Long>{

    List<Order> findByUserId(Long userId);
    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByOrderStatus(OrderStatus orderStatus);

}
