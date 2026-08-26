package com.rm.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {

    private String landmark;
    private String city;
    private String state;
    private String pincode;
    
}
