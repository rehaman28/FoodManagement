package com.rm.dto.ResponseDto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
 * Jackson normally discovers bean properties in an implementation-dependent
 * order. This annotation makes the public API predictable: restaurant details
 * appear first, followed by the address and menu items.
 */
@JsonPropertyOrder({
    "restaurantId",
    "Name",
    "Rating",
    "PhoneNumber",
    "Address",
    "Menu Items"
})
public class RestaurantInfoResponseDto {
    private long restaurantId;
    // @JsonProperty changes only the JSON key; Java builder/accessor names stay unchanged.
    @JsonProperty("Name")
    private String restaurantName;
    @JsonProperty("Rating")
    private double restaurantRating;
    @JsonProperty("PhoneNumber")
    private String restaurant_phoneNumber;
    @JsonProperty("Address")
    private AddressResponseDto addressResponseDto;
    @JsonProperty("Menu Items")
    private List<ItemResponseDto> itemResponseDto;
}
