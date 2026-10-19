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
    // The restaurant_id foreign key is managed by Restaurant.item's @JoinColumn.
    // Keeping a second restaurantId property here would map the same database
    // column twice and cause Hibernate's DuplicateMappingException.
    private double itemPrice;
    private String itemCategory;
    private String itemType;
    private double itemRating;
    
    public Item(String itemName, double itemPrice, String itemCategory, String itemType,
            double itemRating) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        this.itemRating = itemRating;
    }

}
