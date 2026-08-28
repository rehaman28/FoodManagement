package com.om.service;

import com.om.dto.OrderRequestDto;
import com.om.dto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto placeorder(OrderRequestDto orderRequest);    

}
