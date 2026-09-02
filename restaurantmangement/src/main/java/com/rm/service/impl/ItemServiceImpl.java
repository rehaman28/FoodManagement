package com.rm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.rm.Exception.RestaurantNotFoundException;
import com.rm.builder.RestaurantInfoBuilder;
import com.rm.dao.RestaurantRepository;
import com.rm.dto.RequestDto.ItemRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.model.Item;
import com.rm.model.Restaurant;
import com.rm.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    private final RestaurantRepository restaurantRepository;

    public ItemServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ItemResponseDto getItemByRestaurantIdAndItemId(Long restaurantId, Long itemId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        Item item = restaurant.getItem().stream()
                .filter(existingItem -> existingItem.getItemId() == itemId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Item not found in the restaurant: " + restaurant.getRestaurantName()));

        ItemResponseDto responseDto = new ItemResponseDto();
        BeanUtils.copyProperties(item, responseDto);
        return responseDto;
    }

    @Override
    public RestaurantInfoResponseDto addItemsToRestaurant(Long restaurantId, List<ItemRequestDto> itemRequestDtos) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        List<Item> items = restaurant.getItem();
        if (items == null) {
            items = new ArrayList<>();
            restaurant.setItem(items);
        }

        if (itemRequestDtos != null) {
            for (ItemRequestDto itemRequestDto : itemRequestDtos) {
                Item item = new Item();
                BeanUtils.copyProperties(itemRequestDto, item);
                items.add(item);
            }
        }

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantInfoBuilder.buildRestaurantFromRestaurantResponse(updatedRestaurant);
    }

    private Restaurant findRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        "Restaurant not found with id " + restaurantId));
    }
}
