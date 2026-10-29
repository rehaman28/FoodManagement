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
import com.rm.dto.RequestDto.ItemUpdateRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.service.ItemService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/restaurants/{restaurantId}/items")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Item Resource", description = "Item resource controller")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "Get an item by restaurant and item ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant or item not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ItemResponseDto> getItemByRestaurantIdAndItemId(
            @PathVariable(name = "restaurantId") Long restaurantId,
            @PathVariable(name = "itemId") Long itemId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.getItemByRestaurantIdAndItemId(restaurantId, itemId));
    }

    @GetMapping
    @Operation(summary = "Get all items for a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ItemResponseDto>> getItemsByRestaurant(
            @PathVariable(name = "restaurantId") Long restaurantId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.getRestaurantItems(restaurantId));
    }

    @PostMapping("/bulk")
    @Operation(summary = "Add multiple items to a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Items added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RestaurantInfoResponseDto> addItems(
            @PathVariable(name = "restaurantId") Long restaurantId,
            @RequestBody
            @NotEmpty(message = "At least one item is required")
            @Valid List<ItemRequestDto> itemRequestDtos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.addItems(restaurantId, itemRequestDtos));
    }

    @PostMapping
    @Operation(summary = "Add an item to a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ItemResponseDto> addItemToRestaurant(
            @PathVariable(name = "restaurantId") Long restaurantId,
            @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.addItem(restaurantId, itemRequestDto));
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Delete an item from a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant or item not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteItemById(
            @PathVariable(name = "restaurantId") Long restaurantId,
            @PathVariable(name = "itemId") Long itemId) {
        itemService.deleteItemById(restaurantId, itemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{itemId}")
    @Operation(summary = "Update an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Restaurant or item not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ItemResponseDto> updateItem(
            @PathVariable(name = "itemId") Long itemId,
            @PathVariable(name = "restaurantId") Long restaurantId,
            @Valid @RequestBody ItemUpdateRequestDto itemRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.updateItem(itemId, restaurantId, itemRequestDto));
    }
}
