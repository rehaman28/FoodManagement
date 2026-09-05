package com.rm.service;

import java.util.List;

import com.rm.dto.RequestDto.RestaurantRequestDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;

public interface RestaurantService {
    
    RestaurantResponseDto addRestaurant(RestaurantRequestDto requestDto);    
    RestaurantInfoResponseDto getRestaurant(Long id);
    List<RestaurantResponseDto> getAllRestaurants();
    RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto requestDto);
    void deleteRestaurant(Long id);
    RestaurantResponseDto updateRestaurantRating(Long id, Double rating);
}
