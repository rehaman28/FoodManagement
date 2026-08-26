package com.rm.restaurantmangement.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long restaurantId;
    private String restaurantName;
    private Address restaurant_address;
    private String restaurant_phoneNumber;
    
    @Override
    public String toString() {
        return "restaurant [restaurantId=" + restaurantId + ", restaurantName=" + restaurantName
                + ", restaurant_address=" + restaurant_address + ", restaurant_phoneNumber=" + restaurant_phoneNumber
                + ", item=" + item + "]";
    }
    private List<Item> item;
    

}
