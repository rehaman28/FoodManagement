package com.rm.dto.ResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDto {

    @JsonProperty("Id")
    private long restaurantId;
    @JsonProperty("Name")
    private String restaurantName;
}
