package com.rm.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.rm.dto.RequestDto.AddressRequestDto;
import com.rm.dto.RequestDto.ItemRequestDto;
import com.rm.dto.RequestDto.RestaurantCreateRequestDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;
import com.rm.model.Address;
import com.rm.model.Item;
import com.rm.model.Restaurant;

public class RestaurantBuilder {

    public static Restaurant buildRestaurantFromRestaurantDto(RestaurantCreateRequestDto requestDto){
        return Restaurant.builder()
        .restaurantName(requestDto.getRestaurantName())
        .restaurantPhoneNumber(requestDto.getPhoneNumber())
        .restaurantRating(requestDto.getRating() == null ? 0.0 : requestDto.getRating())
        .restaurantAddress(buildAddressFromAddressDto(requestDto.getAddressRequestDto()))
        .item(buildItemFromItemRequestDto(requestDto.getItemRequestDto()))
        .build();
    }   

    private static List<Item> buildItemFromItemRequestDto(List<ItemRequestDto> itemRequestDto) {
       List<Item> listedItems= new ArrayList<>();
       for (ItemRequestDto itemRequestDtos : itemRequestDto) {
        Item item =new Item();
        copyItemProperties(itemRequestDtos, item);
        listedItems.add(item);
       }
       return listedItems;
    }

    private static void copyItemProperties(ItemRequestDto source, Item target) {
        BeanUtils.copyProperties(source, target, "itemPrice", "itemRating");
        if (source.getItemPrice() != null) {
            target.setItemPrice(source.getItemPrice());
        }
        if (source.getItemRating() != null) {
            target.setItemRating(source.getItemRating());
        }
    }

    private static Address buildAddressFromAddressDto(AddressRequestDto addressRequestDto){
        Address address = new Address();
        BeanUtils.copyProperties(addressRequestDto, address);
        return address;
    }

    public static RestaurantResponseDto buildRestaurantResponseDtoFromRestaurant(Restaurant restaurant) {
        return RestaurantResponseDto.builder()
        .restaurantId(restaurant.getRestaurantId())
        .restaurantName(restaurant.getRestaurantName())
        .rating(restaurant.getRestaurantRating())
        .build();

    }
    
}
