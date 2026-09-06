package com.rm.service;

import java.util.List;

import com.rm.dto.RequestDto.RestaurantCreateRequestDto;
import com.rm.dto.RequestDto.RestaurantUpdateRequestDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;

public interface RestaurantService {
    
    RestaurantResponseDto addRestaurant(RestaurantCreateRequestDto requestDto);
    RestaurantInfoResponseDto getRestaurant(Long id);
    List<RestaurantResponseDto> getAllRestaurants();
    RestaurantResponseDto updateRestaurant(Long id, RestaurantUpdateRequestDto requestDto);
    void deleteRestaurant(Long id);
    RestaurantResponseDto updateRestaurantRating(Long id, Double rating);
}
