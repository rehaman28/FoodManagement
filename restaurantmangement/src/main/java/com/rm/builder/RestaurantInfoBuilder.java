package com.rm.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.rm.dto.ResponseDto.AddressResponseDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.model.Address;
import com.rm.model.Item;
import com.rm.model.Restaurant;

public class RestaurantInfoBuilder {

    public static RestaurantInfoResponseDto buildRestaurantFromRestaurantResponse(Restaurant restaurantresponse) {
        return RestaurantInfoResponseDto.builder()
                .restaurantId(restaurantresponse.getRestaurantId())
                .restaurantName(restaurantresponse.getRestaurantName())
                .restaurantRating(restaurantresponse.getRestaurantRating())
                .restaurantPhoneNumber(restaurantresponse.getRestaurantPhoneNumber())
                .addressResponseDto(buildAddressResponseFromAddress(restaurantresponse.getRestaurantAddress()))
                .itemResponseDto(buildItemResponseFromItem(restaurantresponse.getItem()))
                .build();
    }

    private static AddressResponseDto buildAddressResponseFromAddress(Address addressinfo) {
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        BeanUtils.copyProperties(addressinfo, addressResponseDto);
        return addressResponseDto;
    }

    private static List<ItemResponseDto> buildItemResponseFromItem(List<Item> items) {
       List<ItemResponseDto> itemsResponseDtosList= new ArrayList<>();
       for (Item item : items) {
        ItemResponseDto itemResponseDto = new ItemResponseDto();
        BeanUtils.copyProperties(item, itemResponseDto); 
        itemsResponseDtosList.add(itemResponseDto);
       }
       return itemsResponseDtosList;
    }
}
