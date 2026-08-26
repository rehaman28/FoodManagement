package com.rm.service;

import com.rm.dto.RequestDto.RestaurantRequestDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;

public interface RestaurantService {
    RestaurantResponseDto addRestaurant(RestaurantRequestDto requestDto);
}
