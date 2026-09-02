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
import com.rm.dto.RequestDto.RestaurantRequestDto;
import com.rm.dto.ResponseDto.ItemResponseDto;
import com.rm.dto.ResponseDto.RestaurantInfoResponseDto;
import com.rm.dto.ResponseDto.RestaurantResponseDto;
import com.rm.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

   private final RestaurantService restaurantService ;
   public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    } 

    //Add restaurant
    @PostMapping("/addrestaurant")    
    public ResponseEntity<RestaurantResponseDto> addRestaurant(@RequestBody RestaurantRequestDto requestDto){
        return  ResponseEntity.status(HttpStatus.CREATED)
                            .body(restaurantService.addRestaurant(requestDto));
    } 

    //Fetch Restaurant By Id
    @GetMapping("/getrestaurant/{restaurantId}")
    public ResponseEntity<RestaurantInfoResponseDto> getRestaurant(@PathVariable(name = "restaurantId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                            .body(restaurantService.getRestaurant(id)); 
    }

    //Fetch RestaurantName by Id
    @GetMapping("/getrestaurant/name/{restaurantId}")
    public ResponseEntity<String> getRestaurantName(@PathVariable(name = "restaurantId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                            .body(restaurantService.getRestaurant(id).getRestaurantName()); 
    }
    
    //Fetch Items from particular Restaurant using RestaurantId and Item Id
    @GetMapping("/{restaurantId}/items/{itemId}")
    public ResponseEntity<ItemResponseDto> getItemByRestaurantIdAndItemId(
            @PathVariable(name = "restaurantId") long restaurant_id,
            @PathVariable(name = "itemId") long itemId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.getItemByRestaurantIdAndItemId(restaurant_id, itemId));
    }

    //Get All Restaurants-Expose only Restaurant Names and rating
    @GetMapping("/findallrestaurants")
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getAllRestaurants());
    }
    

   //Update Restaurant - restaurantName, Address,phone number dynamically as through resquest
    @PutMapping("/update/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@PathVariable(name = "restaurantId") Long id,
    @RequestBody RestaurantRequestDto requestDto) {
        System.out.println("Request:"+requestDto.getRating());
        return ResponseEntity.status(HttpStatus.OK)
                            .body(restaurantService.updateRestaurant(id,requestDto)); 
    }

    //Delete Restaurant - Delete entire restaurant
    @DeleteMapping("/deleterestaurant/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable(name = "restaurantId") Long id) {
        return restaurantService.deleteRestaurant(id); 
    }

   //Add Item to Restaurant
   @PutMapping("/addItem/{restaurantId}")
   public ResponseEntity<RestaurantInfoResponseDto> AddItemToRestaurant(@PathVariable(name = "restaurantId") Long id,
        @RequestBody List<ItemRequestDto> itemRequestDto){       
       return ResponseEntity.status(HttpStatus.OK)
                    .body(restaurantService.addItemToRestaurant(id, itemRequestDto));
   }

   //Update Item - 

   //update restaurant rating 
   @PutMapping("rating/{restaurantId}")
   public ResponseEntity<RestaurantResponseDto> updateRestaurantRating(@PathVariable(name = "restaurantId") Long id,
    @RequestBody RestaurantRequestDto requestDto) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(restaurantService.updateRestaurantRating(id, requestDto.getRating()));
   }
   //Delete Item - Delete item from particular restaurant
   //search Item - search item with name or Id or category

   //Get Items by Category 

   //Get Vegetarian / Non-Vegetarian Items

   
}
