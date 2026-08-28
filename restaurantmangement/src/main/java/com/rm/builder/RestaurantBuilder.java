package com.rm.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.rm.dto.RequestDto.AddressRequestDto;
import com.rm.dto.RequestDto.ItemRequestDto;
import com.rm.dto.RequestDto.RestaurantRequestDto;
import com.rm.model.Address;
import com.rm.model.Item;
import com.rm.model.Restaurant;

public class RestaurantBuilder {

    public static Restaurant buildRestaurantFromRestaurantDto(RestaurantRequestDto requestDto){
        return Restaurant.builder()
        .restaurantName(requestDto.getRestaurantName())
        .restaurant_phoneNumber(requestDto.getPhoneNumber())
        .restaurant_address(buildAddressFromAddressDto(requestDto.getAddressRequestDto()))
        .item(buildItemFromItemRequestDto(requestDto.getItemRequestDto()))
        .build();
    }   

    private static List<Item> buildItemFromItemRequestDto(List<ItemRequestDto> itemRequestDto) {
       List<Item> listedItems= new ArrayList<>();
       for (ItemRequestDto itemRequestDtos : itemRequestDto) {
        Item item =new Item();
        BeanUtils.copyProperties(itemRequestDtos, item); 
        listedItems.add(item);
       }
       return listedItems;
    }

    private static Address buildAddressFromAddressDto(AddressRequestDto addressRequestDto){
        Address address = new Address();
        BeanUtils.copyProperties(addressRequestDto, address);
        return address;
    }
    
}
