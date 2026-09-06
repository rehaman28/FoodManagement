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
                copyItemProperties(itemRequestDto, item);
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
    public ItemResponseDto updateItem(Long itemId,
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
    public ItemResponseDto addItem(Long restaurantId, 
                                    ItemRequestDto itemRequestDtos) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        List<Item> items = restaurant.getItem();
        if (items == null) {
            items = new ArrayList<>();
            restaurant.setItem(items);
        }

        if (itemRequestDtos == null) {
            throw new IllegalArgumentException("Item request must not be null");
        }

        Item item = new Item();
        copyItemProperties(itemRequestDtos, item);
        items.add(item);
        restaurantRepository.saveAndFlush(restaurant);
        return toItemResponse(item);
    }


    //Helper Methods starts
    private void copyItemProperties(ItemRequestDto source, Item target) {
        BeanUtils.copyProperties(source, target, "itemPrice", "itemRating");
        if (source.getItemPrice() != null) {
            target.setItemPrice(source.getItemPrice());
        }
        if (source.getItemRating() != null) {
            target.setItemRating(source.getItemRating());
        }
    }

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
