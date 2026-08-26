package com.om.ordermanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderItem
 */
@Data
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;
    private long quantity;
    private long itemId;
    private double orderItemPrice;
     
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}