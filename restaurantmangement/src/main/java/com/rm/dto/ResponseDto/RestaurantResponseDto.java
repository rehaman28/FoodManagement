package com.rm.dto.ResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponseDto {

    @JsonProperty("Id")
    private long restaurantId;

    @JsonProperty("Name")
    private String restaurantName;

    @JsonProperty("Rating")
    private double rating;

    public RestaurantResponseDto(long restaurantId, String restaurantName) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}
