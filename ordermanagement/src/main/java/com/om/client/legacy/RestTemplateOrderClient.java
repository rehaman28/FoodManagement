package com.om.client.legacy;

import org.springframework.web.client.RestTemplate;

import com.om.dto.response.ItemResponseDto;

public class RestTemplateOrderClient {

    private final RestTemplate restTemplate;

    public RestTemplateOrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchRestaurantNameFromId(long restaurantId) {
        return restTemplate.getForObject(
                "http://localhost:8001/restaurants/" + restaurantId + "/name",
                String.class);
    }

    public ItemResponseDto fetchItemFromRestaurant(long restaurantId, long itemId) {
        String url = "http://localhost:8001/restaurants/{restaurantId}/items/{itemId}";
        return restTemplate.getForObject(url, ItemResponseDto.class, restaurantId, itemId);
    }
}
