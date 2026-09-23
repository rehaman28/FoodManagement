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

    public static Restaurant buildRestaurantFromRestaurantDto(RestaurantCreateRequestDto requestDto) {
        return Restaurant.builder()
                .restaurantName(requestDto.getRestaurantName())
                .restaurantPhoneNumber(requestDto.getPhoneNumber())
                .restaurantRating(requestDto.getRating() == null ? 0.0 : requestDto.getRating())
                .restaurantAddress(buildAddressFromAddressDto(requestDto.getAddressRequestDto()))
                .items(buildItemFromItemRequestDto(requestDto.getItemRequestDto()))
                .build();
    }

    private static List<Item> buildItemFromItemRequestDto(List<ItemRequestDto> itemRequestDtos) {
        List<Item> listedItems = new ArrayList<>();

        for (ItemRequestDto requestDto : itemRequestDtos) {
            Item item = new Item();
            BeanUtils.copyProperties(requestDto, item, "itemPrice", "itemRating");

            if (requestDto.getItemPrice() != null) {
                item.setItemPrice(requestDto.getItemPrice());
            }
            if (requestDto.getItemRating() != null) {
                item.setItemRating(requestDto.getItemRating());
            }

            listedItems.add(item);
        }

        return listedItems;
    }

    private static Address buildAddressFromAddressDto(AddressRequestDto addressRequestDto) {
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
