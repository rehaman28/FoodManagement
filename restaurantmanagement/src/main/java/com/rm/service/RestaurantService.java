package com.rm.service;

import java.util.List;

import com.rm.dto.request.RestaurantCreateRequestDto;
import com.rm.dto.request.RestaurantUpdateRequestDto;
import com.rm.dto.response.RestaurantInfoResponseDto;
import com.rm.dto.response.RestaurantResponseDto;

public interface RestaurantService {
    
    RestaurantResponseDto addRestaurant(RestaurantCreateRequestDto requestDto);
    RestaurantInfoResponseDto getRestaurant(Long id);
    List<RestaurantResponseDto> getAllRestaurants();
    RestaurantResponseDto updateRestaurant(Long id, RestaurantUpdateRequestDto requestDto);
    void deleteRestaurant(Long id);
    RestaurantResponseDto updateRestaurantRating(Long id, Double rating);
}
