package com.dm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dm.dto.DeliveryPersonRequestDto;
import com.dm.dto.DeliveryPersonResponeDto;

/**
 * DeliveryPersonService
 */
public interface DeliveryPersonService {

    ResponseEntity<DeliveryPersonResponeDto> AddDeliveryPerson(DeliveryPersonRequestDto requestDto);

    ResponseEntity<DeliveryPersonResponeDto> getDeliveryPersonDetais(Long deliveryAgentId);

    List<DeliveryPersonResponeDto> getAllDeliveryPersonDetais();

    ResponseEntity<DeliveryPersonResponeDto> updateDeliveryPersonDetails(Long deliveryAgentId,DeliveryPersonRequestDto requestDto);


}
