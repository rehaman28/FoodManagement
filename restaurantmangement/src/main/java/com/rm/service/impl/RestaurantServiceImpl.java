package com.rm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rm.Exception.RestaurantNotFoundException;
import com.rm.builder.RestaurantBuilder;
import com.rm.builder.RestaurantInfoBuilder;
import com.rm.dao.RestaurantRepository;
import com.rm.dto.RequestDto.AddressRequestDto;
import com.rm.dto.RequestDto.ItemRequestDto;
import com.rm.dto.RequestDto.RestaurantRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;
import com.rm.model.Address;
import com.rm.model.Item;
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
        Restaurant restaurant = findRestaurantById(id);
        return RestaurantInfoBuilder.buildRestaurantFromRestaurantResponse(restaurant);
    }

    @Override
    public ItemResponseDto getItemByRestaurantIdAndItemId(long restaurant_id, long itemId) {
       Restaurant restaurant = findRestaurantById(restaurant_id);
                                
        Item item = restaurant.getItem()
                            .stream()
                            .filter(existingItem -> existingItem.getItemId() == itemId)
                            .findFirst()
                            .orElseThrow(()-> new RuntimeException("Item not Found in the Restaurant: " + restaurant.getRestaurantName()));

        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setItemCategory(item.getItemCategory());
        responseDto.setItemName(item.getItemName());
        responseDto.setItemPrice(item.getItemPrice());
        responseDto.setItemType(item.getItemType());
        return responseDto;
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
    public RestaurantResponseDto updateRestaurant(Long id,RestaurantRequestDto requestDto ) 
    {
        Restaurant restaurant = findRestaurantById(id);

        if(requestDto.getRestaurantName()!= null){
            restaurant.setRestaurantName(requestDto.getRestaurantName());
        }
        if (requestDto.getPhoneNumber()!=null){
            restaurant.setRestaurantPhoneNumber(requestDto.getPhoneNumber());
        }
        if (requestDto.getRating()!=null) 
        {
            restaurant.setRestaurantRating(requestDto.getRating());
        }
        if(requestDto.getAddressRequestDto()!= null)
        {
            Address existingAddress = restaurant.getRestaurantAddress();
            AddressRequestDto requestAddress = requestDto.getAddressRequestDto();

            if (requestAddress.getCity() != null) {
                existingAddress.setCity(requestAddress.getCity());
            }
            if (requestAddress.getLandmark() != null) {
                existingAddress.setLandmark(requestAddress.getLandmark());
            }
            if (requestAddress.getPincode()!=null) {
                existingAddress.setPincode(requestAddress.getPincode());
            }
            if (requestAddress.getState()!=null) {
                existingAddress.setState(requestAddress.getState());
            }
        }
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantBuilder.buildRestaurantResponseDtoFromRestaurant(updatedRestaurant);

    }

    @Override
    public ResponseEntity<Void> deleteRestaurant(Long id) {
        Restaurant restaurant = findRestaurantById(id);
        restaurantRepository.delete(restaurant);
        return ResponseEntity.noContent().build();
    }

    @Override
    public RestaurantInfoResponseDto addItemToRestaurant(Long Id, List<ItemRequestDto> itemRequestDto) {
        Restaurant restaurant = findRestaurantById(Id);
        if(itemRequestDto != null){
           List<Item> items = restaurant.getItem();
           for (ItemRequestDto itemRequest : itemRequestDto) {
               Item item = new Item();
               BeanUtils.copyProperties(itemRequest, item);
               items.add(item);
           }
           Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
           return RestaurantInfoBuilder.buildRestaurantFromRestaurantResponse(updatedRestaurant);
        }
        return RestaurantInfoBuilder.buildRestaurantFromRestaurantResponse(restaurant);
    }


    @Override
    public RestaurantResponseDto updateRestaurantRating(Long id, Double rating) {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setRestaurantRating(rating);
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantBuilder.buildRestaurantResponseDtoFromRestaurant(updatedRestaurant);
    }

    private Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id " + id));
    }

    
}
