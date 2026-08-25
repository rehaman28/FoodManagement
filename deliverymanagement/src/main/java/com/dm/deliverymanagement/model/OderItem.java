package com.dm.deliverymanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OderItem
 */
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private long quantity;
    private long itemId;
    private double orderItemPrice;
}
