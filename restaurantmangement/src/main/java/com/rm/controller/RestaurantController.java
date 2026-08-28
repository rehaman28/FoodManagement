package com.rm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.rm.dto.RequestDto.RestaurantRequestDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;
import com.rm.service.RestaurantService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

   private final RestaurantService restaurantService ;
   public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
   
    @PostMapping("/addrestaurant")    
    public ResponseEntity<RestaurantResponseDto> addRestaurant(@RequestBody RestaurantRequestDto requestDto){
        return  ResponseEntity.status(HttpStatus.CREATED)
                            .body(restaurantService.addRestaurant(requestDto));
    } 

    @GetMapping("/getrestaurant/{restaurantId}")
    public ResponseEntity<RestaurantInfoResponseDto> getRestaurant(@PathVariable(name = "restaurantId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                            .body(restaurantService.getRestaurant(id)); 
    }

    @GetMapping("/getrestaurant/name/{restaurantId}")
    public ResponseEntity<String> getRestaurantName(@PathVariable(name = "restaurantId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                            .body(restaurantService.getRestaurant(id).getRestaurantName()); 
    }
    
}
