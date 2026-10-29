package com.rm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.dto.RequestDto.RestaurantCreateRequestDto;
import com.rm.dto.RequestDto.RestaurantRatingRequestDto;
import com.rm.dto.RequestDto.RestaurantUpdateRequestDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;
import com.rm.service.RestaurantService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Restaurant Resource", description = "Restaurant resource controller")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Add restaurant
    @PostMapping
    @Operation(summary = "Create a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RestaurantResponseDto> addRestaurant(@Valid @RequestBody RestaurantCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantService.addRestaurant(requestDto));
    }

    // Fetch Restaurant By Id
    @GetMapping("/{restaurantId}")
    @Operation(summary = "Get a restaurant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RestaurantInfoResponseDto> getRestaurant(@PathVariable(name = "restaurantId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.getRestaurant(id));
    }

    // Fetch RestaurantName by Id
    @GetMapping("/{restaurantId}/name")
    @Operation(summary = "Get a restaurant name by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant name retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> getRestaurantName(@PathVariable(name = "restaurantId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.getRestaurant(id).getRestaurantName());
    }

    // Get All Restaurants-Expose only Restaurant Names and rating
    @GetMapping
    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getAllRestaurants());
    }

    // Update Restaurant - restaurantName, Address,phone number dynamically as
    // through request
    @PatchMapping("/{restaurantId}")
    @Operation(summary = "Update a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@PathVariable(name = "restaurantId") Long id,
            @Valid @RequestBody RestaurantUpdateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.updateRestaurant(id, requestDto));
    }

    // Delete Restaurant - Delete entire restaurant
    @DeleteMapping("/{restaurantId}")
    @Operation(summary = "Delete a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteRestaurant(@PathVariable(name = "restaurantId") Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    // update restaurant rating
    @PutMapping("/{restaurantId}/rating")
    @Operation(summary = "Update a restaurant rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant rating updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RestaurantResponseDto> updateRestaurantRating(@PathVariable(name = "restaurantId") Long id,
            @Valid @RequestBody RestaurantRatingRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.updateRestaurantRating(id, requestDto.getRating()));
    }
    // Possible future endpoints: restaurant search, availability, cuisine filters,
    // and operating hours.

}
