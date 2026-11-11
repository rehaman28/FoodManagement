package com.rm.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
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
    @Column(name = "item_price", 
        precision = 19, 
        scale = 2,
        nullable = false)
    private BigDecimal itemPrice;
    
    private String itemCategory;
    private String itemType;
    private double itemRating;
    
    public Item(String itemName, BigDecimal itemPrice, String itemCategory, String itemType,
            double itemRating) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        this.itemRating = itemRating;
    }

}
