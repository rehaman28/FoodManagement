package com.om.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {
    
    private String itemName;
    private double itemPrice;
    private String itemCategory;
    private String itemType;
}
