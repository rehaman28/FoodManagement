package com.rm.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {
    
    private String itemName;
    private Double itemPrice;
    private String itemCategory;
    private String itemType;
    private Double itemRating;
}
