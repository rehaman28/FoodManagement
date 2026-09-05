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

import com.rm.dto.RequestDto.RestaurantRequestDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;
import com.rm.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Add restaurant
    @PostMapping
    public ResponseEntity<RestaurantResponseDto> addRestaurant(@RequestBody RestaurantRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantService.addRestaurant(requestDto));
    }

    // Fetch Restaurant By Id
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantInfoResponseDto> getRestaurant(@PathVariable(name = "restaurantId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.getRestaurant(id));
    }

    // Fetch RestaurantName by Id
    @GetMapping("/{restaurantId}/name")
    public ResponseEntity<String> getRestaurantName(@PathVariable(name = "restaurantId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.getRestaurant(id).getRestaurantName());
    }

    // Get All Restaurants-Expose only Restaurant Names and rating
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getAllRestaurants());
    }

    // Update Restaurant - restaurantName, Address,phone number dynamically as
    // through request
    @PatchMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@PathVariable(name = "restaurantId") Long id,
            @RequestBody RestaurantRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.updateRestaurant(id, requestDto));
    }

    // Delete Restaurant - Delete entire restaurant
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable(name = "restaurantId") Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    // update restaurant rating
    @PutMapping("/{restaurantId}/rating")
    public ResponseEntity<RestaurantResponseDto> updateRestaurantRating(@PathVariable(name = "restaurantId") Long id,
            @RequestBody RestaurantRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.updateRestaurantRating(id, requestDto.getRating()));
    }
    // Possible future endpoints: restaurant search, availability, cuisine filters,
    // and operating hours.

}
