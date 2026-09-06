package com.rm.service;

import java.util.List;

import com.rm.dto.RequestDto.ItemRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;

public interface ItemService {

    ItemResponseDto getItemByRestaurantIdAndItemId(Long restaurantId, Long itemId);

    RestaurantInfoResponseDto addItems(Long restaurantId, List<ItemRequestDto> itemRequestDtos);

    void deleteItemById(Long restaurantId, Long itemId);

    ItemResponseDto updateItem(Long itemId, Long restaurantId, ItemRequestDto itemRequestDtos);

    List<ItemResponseDto> getRestaurantItems(Long restaurantId);

    ItemResponseDto addItem(Long restaurantId, ItemRequestDto itemRequestDtos);
}
