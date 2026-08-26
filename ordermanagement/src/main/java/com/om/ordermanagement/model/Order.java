package com.om.ordermanagement.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {
       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private long userId;
    private long restaurantId;
    private double orderPrice;
    private String orderStatus;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private List<OrderItem> orderItems;
}
