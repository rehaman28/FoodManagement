package com.om.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.om.model.Order;

/**
 * OrderRepository
 */
public interface OrderRepository extends JpaRepository<Order,Long>{

}
