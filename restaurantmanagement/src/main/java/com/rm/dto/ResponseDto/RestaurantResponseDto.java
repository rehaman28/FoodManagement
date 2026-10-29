package com.rm.dto.ResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Summary response data describing a restaurant.")
public class RestaurantResponseDto {

    @JsonProperty("Id")
    @Schema(description = "Identifier of the restaurant.")
    private long restaurantId;

    @JsonProperty("Name")
    @Schema(description = "Name of the restaurant.")
    private String restaurantName;

    @JsonProperty("Rating")
    @Schema(description = "Restaurant rating from 0.0 to 5.0.")
    private double rating;

    public RestaurantResponseDto(long restaurantId, String restaurantName) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}
