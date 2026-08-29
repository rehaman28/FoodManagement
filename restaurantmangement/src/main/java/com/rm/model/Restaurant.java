package com.rm.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "restaurants")
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long restaurantId;

    private String restaurantName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address restaurantAddress;

    private Double restaurantRating;

    private String restaurantPhoneNumber;

    /*
     * The restaurant owns the one-to-many relationship, and the foreign key is
     * stored directly in the item table. Specifying the join column here
     * prevents Hibernate from creating a separate restaurants_item join table.
     * CascadeType.ALL also persists the submitted items when the restaurant is
     * persisted.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Item> item;


    public Restaurant(String restaurantName, Address restaurantAddress, double restaurantRating,
            String restaurantPhoneNumber, List<Item> item) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantRating = restaurantRating;
        this.restaurantPhoneNumber = restaurantPhoneNumber;
        this.item = item;
    }



    // @Override
    // public String toString() {
    //     return "restaurant [restaurantId=" + restaurantId + ", restaurantName=" + restaurantName
    //             + ", restaurant_address=" + restaurant_address + ", restaurant_phoneNumber=" + restaurant_phoneNumber
    //             + ", item=" + item + "]";
    // }

}
