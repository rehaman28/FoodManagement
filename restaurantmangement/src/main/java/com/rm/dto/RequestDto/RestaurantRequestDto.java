package com.rm.dto.RequestDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDto {
    
    private String restaurantName;
    private String phoneNumber;
    private Double rating;
    private AddressRequestDto addressRequestDto;
    private List<ItemRequestDto> itemRequestDto;

}
