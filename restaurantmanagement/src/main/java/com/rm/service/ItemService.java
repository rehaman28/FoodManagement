package com.rm.service;

import java.util.List;

import com.rm.dto.request.ItemRequestDto;
import com.rm.dto.request.ItemUpdateRequestDto;
import com.rm.dto.response.ItemResponseDto;
import com.rm.dto.response.RestaurantInfoResponseDto;

public interface ItemService {

    ItemResponseDto getItemByRestaurantIdAndItemId(Long restaurantId, Long itemId);

    RestaurantInfoResponseDto addItems(Long restaurantId, List<ItemRequestDto> itemRequestDtos);

    void deleteItemById(Long restaurantId, Long itemId);

    ItemResponseDto updateItem(Long itemId, Long restaurantId, ItemUpdateRequestDto itemRequestDto);

    List<ItemResponseDto> getRestaurantItems(Long restaurantId);

    ItemResponseDto addItem(Long restaurantId, ItemRequestDto itemRequestDto);
}
