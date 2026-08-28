package com.rm.service.impl;

import org.springframework.stereotype.Service;

import com.rm.builder.RestaurantBuilder;
import com.rm.builder.RestaurantInfoBuilder;
import com.rm.dao.RestaurantRepository;
import com.rm.dto.RequestDto.RestaurantRequestDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;
import com.rm.model.Restaurant;
import com.rm.service.RestaurantService;


@Service
public class RestaurantServiceImpl implements RestaurantService {
    
    private final RestaurantRepository restaurantRepository;
    
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        super();
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantResponseDto addRestaurant(RestaurantRequestDto requestDto) {
        Restaurant restaurant = RestaurantBuilder.buildRestaurantFromRestaurantDto(requestDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return new RestaurantResponseDto(savedRestaurant.getRestaurantId(),savedRestaurant.getRestaurantName());
    }

    @Override
    public RestaurantInfoResponseDto getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                                    .orElseThrow(()->new IllegalArgumentException("No Restaurant Found"));
        return RestaurantInfoBuilder.buildRestaurantFromRestaurantResponse(restaurant);
    }
    
}
