package com.rm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.dto.RequestDto.ItemRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.service.ItemService;


@RestController
@RequestMapping("/restaurants/{restaurantId}/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Fetch an item belonging to a specific restaurant.
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> getItemByRestaurantIdAndItemId(
            @PathVariable Long restaurantId, @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.getItemByRestaurantIdAndItemId(restaurantId, itemId));
    }

    //Get all Items of restaurant
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>>getAllRestaurantItems(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(itemService.getRestaurantItems(restaurantId));
    }
    

    // Add items to a restaurant; existing items are preserved for bulk updates
    @PostMapping("/bulk")
    public ResponseEntity<RestaurantInfoResponseDto> addItemsToRestaurant(
            @PathVariable Long restaurantId, @RequestBody List<ItemRequestDto> itemRequestDtos) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.addItemsToRestaurant(restaurantId, itemRequestDtos));
    }
    //single item updates
    @PostMapping
    public ResponseEntity<RestaurantInfoResponseDto> addItemToRestaurant(
            @PathVariable Long restaurantId, @RequestBody ItemRequestDto itemRequestDtos) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.addItemToRestaurant(restaurantId, itemRequestDtos));
    }
    
    //Delete Item by Item Id 
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItemById(
            @PathVariable Long restaurantId, @PathVariable Long itemId) {
        itemService.deleteItemById(restaurantId, itemId);
        return ResponseEntity.noContent().build();
    }

    //update Rating
    @PutMapping("/{itemId}/rating")
    public ResponseEntity<ItemResponseDto> updateRating(@PathVariable Long restaurantId,
        @PathVariable Long itemId, @RequestBody ItemRequestDto itemRequestDto){
        return ResponseEntity.status(HttpStatus.OK)
        .body(itemService.updateRating(restaurantId,itemId,itemRequestDto));
    }

    // update item details 
    @PutMapping("/{itemId}")
    public ResponseEntity<RestaurantInfoResponseDto> updateItemsToRestaurant(
            @PathVariable Long itemId,
            @PathVariable Long restaurantId, @RequestBody ItemRequestDto itemRequestDtos) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.updateItemsToRestaurant(itemId,restaurantId, itemRequestDtos));
    }
}
