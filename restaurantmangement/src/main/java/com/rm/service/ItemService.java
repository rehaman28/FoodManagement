package com.rm.service;

import java.util.List;

import com.rm.dto.RequestDto.ItemRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;

public interface ItemService {

    ItemResponseDto getItemByRestaurantIdAndItemId(Long restaurantId, Long itemId);

    RestaurantInfoResponseDto addItemsToRestaurant(Long restaurantId, List<ItemRequestDto> itemRequestDtos);
}
