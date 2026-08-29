package com.rm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rm.dto.RequestDto.RestaurantRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;

public interface RestaurantService {
    
    RestaurantResponseDto addRestaurant(RestaurantRequestDto requestDto);    
    RestaurantInfoResponseDto getRestaurant(Long id);
    ItemResponseDto getItemByRestaurantIdAndItemId(long restaurant_id, long itemId);
    List<RestaurantResponseDto> getAllRestaurants();
    RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto requestDto);
    ResponseEntity<Void> deleteRestaurant(Long id);
}
