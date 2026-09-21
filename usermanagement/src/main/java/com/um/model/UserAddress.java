package com.um.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name="user_address")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    private String doorNumber;
    @NotBlank 
    private String street;
    @NotBlank 
    private String city;
    @NotBlank 
    private String district;
    @NotBlank 
    private String country;

    @NotBlank(message = "Pincode is required")
    @Pattern(
        regexp = "^\\d{6}$",
        message = "Pincode must contain 6 digits"
    )
    private String pincode;

}
