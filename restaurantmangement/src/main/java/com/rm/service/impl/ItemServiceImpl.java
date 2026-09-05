package com.rm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.Exception.ItemNotFoundException;
import com.rm.Exception.RestaurantNotFoundException;
import com.rm.builder.RestaurantInfoBuilder;
import com.rm.dao.RestaurantRepository;
import com.rm.dto.RequestDto.ItemRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.model.Item;
import com.rm.model.Restaurant;
import com.rm.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    private final RestaurantRepository restaurantRepository;

    public ItemServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ItemResponseDto getItemByRestaurantIdAndItemId(Long restaurantId, Long itemId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        return toItemResponse(findItemInRestaurant(restaurant, itemId));
    }

    @Override
    public RestaurantInfoResponseDto addItems(Long restaurantId, List<ItemRequestDto> itemRequestDtos) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        List<Item> items = restaurant.getItem();
        if (items == null) {
            items = new ArrayList<>();
            restaurant.setItem(items);
        }

        if (itemRequestDtos != null) {
            for (ItemRequestDto itemRequestDto : itemRequestDtos) {
                Item item = new Item();
                BeanUtils.copyProperties(itemRequestDto, item);
                items.add(item);
            }
        }

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantInfoBuilder.buildRestaurantFromRestaurantResponse(updatedRestaurant);
    }

    @Override
    @Transactional
    public void deleteItemById(Long restaurantId, Long itemId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        List<Item> items = restaurant.getItem();
        Item item = items == null ? null : items.stream()
                .filter(existingItem -> existingItem.getItemId() == itemId)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException(
                        "Item not found with id " + itemId + " in restaurant " + restaurantId));
        items.remove(item);
        restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public ItemResponseDto updateRating(Long restaurantId, 
                                        Long itemId, 
                                        ItemRequestDto itemRequestDto) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        Item item = findItemInRestaurant(restaurant, itemId);
        item.setItemRating(itemRequestDto.getItemRating());
        restaurantRepository.save(restaurant);
        return toItemResponse(item);
    }

    @Override
    public ItemResponseDto updateItems(Long itemId,
        Long restaurantId, 
        ItemRequestDto itemRequestDtos) 
    {
        Restaurant restaurant = findRestaurantById(restaurantId);
        Item item = findItemInRestaurant(restaurant, itemId);
        applyUpdates(item, itemRequestDtos);
        restaurantRepository.save(restaurant);
        return toItemResponse(item);
    }

    @Override
    public List<ItemResponseDto> getRestaurantItems(Long restaurantId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        return toItemResponses(restaurant.getItem());
    }

    

    
    @Override
    public RestaurantInfoResponseDto addItem(Long restaurantId, 
                                    ItemRequestDto itemRequestDtos) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        List<Item> items = restaurant.getItem();
        if (items == null) {
            items = new ArrayList<>();
            restaurant.setItem(items);
        }

        if (itemRequestDtos != null) {
            Item item = new Item();
            BeanUtils.copyProperties(itemRequestDtos, item);
            items.add(item);
        }

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantInfoBuilder.buildRestaurantFromRestaurantResponse(updatedRestaurant);
    }


    //Helper Methods starts
    private Item findItemInRestaurant(Restaurant restaurant, Long itemId) {
        List<Item> items = restaurant.getItem();
        if (items == null) {
            throw itemNotFound(restaurant.getRestaurantId(), itemId);
        }

        return items.stream()
                .filter(existingItem -> existingItem.getItemId() == itemId)
                .findFirst()
                .orElseThrow(() -> itemNotFound(restaurant.getRestaurantId(), itemId));
    }

    private ItemNotFoundException itemNotFound(long restaurantId, Long itemId) {
        return new ItemNotFoundException(
                "Item not found with id " + itemId + " in restaurant " + restaurantId);
    }

    private void applyUpdates(Item item, ItemRequestDto request) {
        if (request.getItemName() != null) {
            item.setItemName(request.getItemName());
        }
        if (request.getItemCategory() != null) {
            item.setItemCategory(request.getItemCategory());
        }
        if (request.getItemPrice() != null) {
            item.setItemPrice(request.getItemPrice());
        }
        if (request.getItemType() != null) {
            item.setItemType(request.getItemType());
        }
        if (request.getItemRating() != null) {
            item.setItemRating(request.getItemRating());
        }
    }

    private ItemResponseDto toItemResponse(Item item) {
        ItemResponseDto responseDto = new ItemResponseDto();
        BeanUtils.copyProperties(item, responseDto);
        return responseDto;
    }

    private List<ItemResponseDto> toItemResponses(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return new ArrayList<>();
        }

        List<ItemResponseDto> responses = new ArrayList<>();
        for (Item item : items) {
            responses.add(toItemResponse(item));
        }
        return responses;
    }


    private Restaurant findRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        "Restaurant not found with id " + restaurantId));
    }  

   
}
