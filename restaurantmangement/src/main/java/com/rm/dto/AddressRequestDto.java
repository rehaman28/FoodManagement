package com.rm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto {
    private String landmark;
    private String city;
    private String state;
    private String pincode;
}
