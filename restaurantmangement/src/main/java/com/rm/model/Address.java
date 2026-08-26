package com.rm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Address
 */

@Table(name = "address")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    private String landmark;
    private String city;
    private String state;
    private String pincode;

    public Address(String landmark, String city, String state, String pincode) {
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }
}
