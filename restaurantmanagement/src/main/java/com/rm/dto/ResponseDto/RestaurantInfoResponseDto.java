package com.rm.dto.ResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Detailed restaurant response including address and menu items.")
public class RestaurantInfoResponseDto {
    @Schema(description = "Identifier of the restaurant.")
    private long restaurantId;
    // @JsonProperty changes only the JSON key; Java builder/accessor names stay unchanged.
    @JsonProperty("Name")
    @Schema(description = "Name of the restaurant.")
    private String restaurantName;
    @JsonProperty("Rating")
    @Schema(description = "Restaurant rating.")
    private double restaurantRating;
    @JsonProperty("PhoneNumber")
    @Schema(description = "Restaurant phone number.")
    private String restaurantPhoneNumber;
    @JsonProperty("Address")
    @Schema(description = "Restaurant address.")
    private AddressResponseDto addressResponseDto;
    @JsonProperty("Menu Items")
    @Schema(description = "Restaurant menu items.")
    private List<ItemResponseDto> itemResponseDto;
}
