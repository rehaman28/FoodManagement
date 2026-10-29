package com.om.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.om.dto.response.ItemResponseDto;
import com.om.constants.ApplicationConstants;

@FeignClient(name = ApplicationConstants.RESTAURANT_SERVICE_NAME)
public interface RestaurantClient {

   @GetMapping(ApplicationConstants.RESTAURANT_NAME_PATH)
    String getRestaurantName(
            @PathVariable("restaurantId") Long restaurantId);

    @GetMapping(ApplicationConstants.RESTAURANT_ITEM_PATH)
    ItemResponseDto getItem(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("itemId") Long itemId);

}