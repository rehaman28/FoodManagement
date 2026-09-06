package com.rm.dto.ResponseDto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    "itemName",
    "itemPrice",
    "itemCategory",
    "itemType",
    "itemRating"
})
public class ItemResponseDto {
    
    private String itemName;
    private Double itemPrice;
    private String itemCategory;
    private String itemType;
    private Double itemRating;
}
