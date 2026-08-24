package com.fm.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Orders {

    @id
    private long orderId;
    private long userId;
    private List<orderItems> orderItems;
    private double totalPrice;
    private String OderStatus;
    
}
