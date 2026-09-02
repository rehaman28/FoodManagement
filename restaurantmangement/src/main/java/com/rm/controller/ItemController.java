package com.rm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.dto.RequestDto.ItemRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Fetch an item belonging to a specific restaurant.
    @GetMapping("/restaurant/{restaurantId}/{itemId}")
    public ResponseEntity<ItemResponseDto> getItemByRestaurantIdAndItemId(
            @PathVariable Long restaurantId, @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.getItemByRestaurantIdAndItemId(restaurantId, itemId));
    }

    // Add items to a restaurant; existing items are preserved.
    @PutMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantInfoResponseDto> addItemsToRestaurant(
            @PathVariable Long restaurantId, @RequestBody List<ItemRequestDto> itemRequestDtos) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.addItemsToRestaurant(restaurantId, itemRequestDtos));
    }

    // Possible future endpoints: create one item, update item details or price,
    // delete an item, search by name, filter by category/type, and update rating.
}
