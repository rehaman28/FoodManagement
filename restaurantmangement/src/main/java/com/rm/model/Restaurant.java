package com.rm.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter 
@Setter 
@NoArgsConstructor
@Table(name = "restaurants")
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long restaurantId;

    @NotBlank
    @Size(min = 3,max = 50)
    @Column(name = "restaurant_name", nullable = false, length = 50)
    private String restaurantName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @NotEmpty
    private Address restaurantAddress;

    @Column(name = "restaurant_rating", precision = 2, scale = 1)
    private Double restaurantRating;

    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone Number must contain 10 digits"
    )
    @Column(name = "restaurant_phone_number", length = 10)
    private String restaurantPhoneNumber;

    /*
     * The restaurant owns the one-to-many relationship, and the foreign key is
     * stored directly in the item table. Specifying the join column here
     * prevents Hibernate from creating a separate restaurants_item join table.
     * CascadeType.ALL also persists the submitted items when the restaurant is
     * persisted.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "restaurant_id")
    private List<Item> items;


    public Restaurant(String restaurantName, Address restaurantAddress, double restaurantRating,
            String restaurantPhoneNumber, List<Item> items) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantRating = restaurantRating;
        this.restaurantPhoneNumber = restaurantPhoneNumber;
        this.items = items;
    }



    // @Override
    // public String toString() {
    //     return "restaurant [restaurantId=" + restaurantId + ", restaurantName=" + restaurantName
    //             + ", restaurant_address=" + restaurant_address + ", restaurant_phoneNumber=" + restaurant_phoneNumber
    //             + ", item=" + item + "]";
    // }

}
