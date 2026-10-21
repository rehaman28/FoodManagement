package com.om.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.om.dto.ItemResponseDto;

@FeignClient (name = "restaurantmangement")
public interface RestaurantClient {

   @GetMapping("/restaurants/{restaurantId}/name")
    String getRestaurantName(
            @PathVariable("restaurantId") Long restaurantId);

    @GetMapping("/restaurants/{restaurantId}/items/{itemId}")
    ItemResponseDto getItem(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("itemId") Long itemId);

}