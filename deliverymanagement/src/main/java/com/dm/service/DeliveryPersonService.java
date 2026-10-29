package com.dm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dm.dto.request.DeliveryPersonRequestDto;
import com.dm.dto.response.DeliveryPersonResponeDto;

/**
 * DeliveryPersonService
 */
public interface DeliveryPersonService {

    ResponseEntity<DeliveryPersonResponeDto> AddDeliveryPerson(DeliveryPersonRequestDto requestDto);

    ResponseEntity<DeliveryPersonResponeDto> getDeliveryPersonDetais(Long deliveryAgentId);

    List<DeliveryPersonResponeDto> getAllDeliveryPersonDetais();

    ResponseEntity<DeliveryPersonResponeDto> updateDeliveryPersonDetails(Long deliveryAgentId,DeliveryPersonRequestDto requestDto);


}
