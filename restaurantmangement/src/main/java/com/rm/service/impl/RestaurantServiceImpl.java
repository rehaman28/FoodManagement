package com.rm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rm.Exception.RestaurantNotFoundException;
import com.rm.builder.RestaurantBuilder;
import com.rm.builder.RestaurantInfoBuilder;
import com.rm.dao.RestaurantRepository;
import com.rm.dto.RequestDto.AddressRequestDto;
import com.rm.dto.RequestDto.RestaurantCreateRequestDto;
import com.rm.dto.RequestDto.RestaurantUpdateRequestDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;
import com.rm.model.Address;
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
    public RestaurantResponseDto addRestaurant(RestaurantCreateRequestDto requestDto) {
        Restaurant restaurant = RestaurantBuilder.buildRestaurantFromRestaurantDto(requestDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return new RestaurantResponseDto(savedRestaurant.getRestaurantId(),savedRestaurant.getRestaurantName());
    }

    @Override
    public RestaurantInfoResponseDto getRestaurant(Long id) {
        Restaurant restaurant = findRestaurantById(id);
        return RestaurantInfoBuilder.buildRestaurantFromRestaurantResponse(restaurant);
    }

    @Override
    public List<RestaurantResponseDto> getAllRestaurants() {
        // List<Restaurant> restaurantsList =restaurantRepository.findAll();
        // List<RestaurantResponseDto> responseDtosList = new ArrayList<>(); 
        // for (Restaurant restaurant : restaurantsList) {
        //     RestaurantResponseDto responseDto = RestaurantBuilder.buildRestaurantResponseDtoFromRestaurant(restaurant);
        //     responseDtosList.add(responseDto);
        // }
        // return responseDtosList;

        return restaurantRepository.findAll()
            .stream()
            .map(RestaurantBuilder::buildRestaurantResponseDtoFromRestaurant)
            .toList();

    }

    @Override
    public RestaurantResponseDto updateRestaurant(Long id,RestaurantUpdateRequestDto requestDto )
    {
        Restaurant restaurant = findRestaurantById(id);

        updateRestaurantFields(restaurant, requestDto);
        updateAddressFields(restaurant, requestDto.getAddressRequestDto());
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantBuilder.buildRestaurantResponseDtoFromRestaurant(updatedRestaurant);

    }

    @Override
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = findRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public RestaurantResponseDto updateRestaurantRating(Long id, Double rating) {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setRestaurantRating(rating);
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantBuilder.buildRestaurantResponseDtoFromRestaurant(updatedRestaurant);
    }

    private void updateRestaurantFields(Restaurant restaurant, RestaurantUpdateRequestDto requestDto) {
        if (requestDto.getRestaurantName() != null) {
            restaurant.setRestaurantName(requestDto.getRestaurantName());
        }
        if (requestDto.getPhoneNumber() != null) {
            restaurant.setRestaurantPhoneNumber(requestDto.getPhoneNumber());
        }
        if (requestDto.getRating() != null) {
            restaurant.setRestaurantRating(requestDto.getRating());
        }
    }

    private void updateAddressFields(Restaurant restaurant, AddressRequestDto requestAddress) {
        if (requestAddress == null) {
            return;
        }

        Address existingAddress = restaurant.getRestaurantAddress();
        if (requestAddress.getCity() != null) {
            existingAddress.setCity(requestAddress.getCity());
        }
        if (requestAddress.getLandmark() != null) {
            existingAddress.setLandmark(requestAddress.getLandmark());
        }
        if (requestAddress.getPincode() != null) {
            existingAddress.setPincode(requestAddress.getPincode());
        }
        if (requestAddress.getState() != null) {
            existingAddress.setState(requestAddress.getState());
        }
    }

    private Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id " + id));
    }

    
}
