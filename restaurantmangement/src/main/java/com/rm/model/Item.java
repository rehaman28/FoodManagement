package com.rm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;
    private String itemName;
    private String restaurantId;
    private double itemPrice;
    private String itemCategory;
    private String itemType;
    private double itemRating;
    
    public Item(String itemName, String restaurantId, double itemPrice, String itemCategory, String itemType,
            double itemRating) {
        this.itemName = itemName;
        this.restaurantId = restaurantId;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        this.itemRating = itemRating;
    }

}
