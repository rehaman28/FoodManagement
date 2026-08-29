package com.rm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rm.Exception.RestaurantNotFoundException;
import com.rm.builder.RestaurantBuilder;
import com.rm.builder.RestaurantInfoBuilder;
import com.rm.dao.RestaurantRepository;
import com.rm.dto.RequestDto.AddressRequestDto;
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
        Restaurant restaurant = restaurantRepository.findById(id)
                                    .orElseThrow(()->new RestaurantNotFoundException("Restaurant not Found Found with Id: "+ id));
        return RestaurantInfoBuilder.buildRestaurantFromRestaurantResponse(restaurant);
    }

    @Override
    public ItemResponseDto getItemByRestaurantIdAndItemId(long restaurant_id, long itemId) {
       Restaurant restaurant = restaurantRepository.findById(restaurant_id)
                                    .orElseThrow(()-> new RestaurantNotFoundException("Restaurant not Found with Id: "+ restaurant_id)) ;
                                
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
        Restaurant restaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new RestaurantNotFoundException("Restaurant NotFound with id "+ id));

        if(requestDto.getRestaurantName()!= null){
            restaurant.setRestaurantName(requestDto.getRestaurantName());
        }
        if (requestDto.getPhoneNumber()!=null){
            restaurant.setRestaurantPhoneNumber(requestDto.getPhoneNumber());
        }
        System.out.println("Before updating rating" +
        requestDto.getRating());
        if (requestDto.getRating()!=null) 
        {
            System.out.println("attempting to  updating rating");
            System.out.println(
                "GetRating" +requestDto.getRating()
            );
            restaurant.setRestaurantRating(requestDto.getRating());
            System.out.println(
                "setRating" +restaurant.getRestaurantRating()
            );
        }
        System.out.println("updated rating");
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
    
}
