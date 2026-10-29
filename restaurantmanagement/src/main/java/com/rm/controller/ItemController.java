package com.rm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.dto.request.ItemRequestDto;
import com.rm.dto.request.ItemUpdateRequestDto;
import com.rm.dto.response.ItemResponseDto;
import com.rm.constants.ApplicationConstants;
import com.rm.dto.response.RestaurantInfoResponseDto;
import com.rm.dto.response.ErrorResponseDto;
import com.rm.service.ItemService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = ApplicationConstants.RESTAURANT_ITEMS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Item Resource", description = "Item resource controller")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(ApplicationConstants.ITEM_ID_PATH)
    @Operation(summary = "Get an item by restaurant and item ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant or item not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
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
            @ApiResponse(responseCode = "200", description = "Items retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ItemResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<ItemResponseDto>> getItemsByRestaurant(
            @PathVariable(name = "restaurantId") Long restaurantId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.getRestaurantItems(restaurantId));
    }

    @PostMapping(value = ApplicationConstants.BULK_ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add multiple items to a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Items added successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantInfoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<RestaurantInfoResponseDto> addItems(
            @PathVariable(name = "restaurantId") Long restaurantId,
            @RequestBody
            @NotEmpty(message = "At least one item is required")
            @Valid List<ItemRequestDto> itemRequestDtos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.addItems(restaurantId, itemRequestDtos));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add an item to a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ItemResponseDto> addItemToRestaurant(
            @PathVariable(name = "restaurantId") Long restaurantId,
            @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.addItem(restaurantId, itemRequestDto));
    }

    @DeleteMapping(ApplicationConstants.ITEM_ID_PATH)
    @Operation(summary = "Delete an item from a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant or item not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<Void> deleteItemById(
            @PathVariable(name = "restaurantId") Long restaurantId,
            @PathVariable(name = "itemId") Long itemId) {
        itemService.deleteItemById(restaurantId, itemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = ApplicationConstants.ITEM_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant or item not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ItemResponseDto> updateItem(
            @PathVariable(name = "itemId") Long itemId,
            @PathVariable(name = "restaurantId") Long restaurantId,
            @Valid @RequestBody ItemUpdateRequestDto itemRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.updateItem(itemId, restaurantId, itemRequestDto));
    }
}
