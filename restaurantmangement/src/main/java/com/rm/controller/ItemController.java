package com.rm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.getItemByRestaurantIdAndItemId(restaurantId, itemId));
    }

    //Get all Items of restaurant
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>>getAllRestaurantItems(@PathVariable Long restaurantId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.getRestaurantItems(restaurantId));
    }
    

    // Add items to a restaurant; existing items are preserved for bulk updates
    @PostMapping("/bulk")
    public ResponseEntity<RestaurantInfoResponseDto> addItems(
            @PathVariable Long restaurantId, @RequestBody List<ItemRequestDto> itemRequestDtos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.addItems(restaurantId, itemRequestDtos));
    }
    //single item updates
    @PostMapping
    public ResponseEntity<ItemResponseDto> addItemToRestaurant(
            @PathVariable Long restaurantId, @RequestBody ItemRequestDto itemRequestDtos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.addItem(restaurantId, itemRequestDtos));
    }
    
    //Delete Item by Item Id 
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItemById(
            @PathVariable Long restaurantId, @PathVariable Long itemId) {
        itemService.deleteItemById(restaurantId, itemId);
        return ResponseEntity.noContent().build();
    }

    // Update item details, including the optional rating.
    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItems(
            @PathVariable Long itemId,
            @PathVariable Long restaurantId, @RequestBody ItemRequestDto itemRequestDtos) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.updateItems(itemId,restaurantId, itemRequestDtos));
    }
}
