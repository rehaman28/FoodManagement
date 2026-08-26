package com.rm.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

    private String itemName;
    private double itemPrice;
    private String itemCategory;
    private String itemType;
}
