package com.dm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dm.dto.DeliveryAssignmentRequestDto;
import com.dm.dto.DeliveryAssignmentResponseDto;
import com.dm.dto.DeliveryStatusRequestDto;

/**
 * DeliveryAssignmentService
 */
public interface DeliveryAssignmentService {

    ResponseEntity<DeliveryAssignmentResponseDto> postDeliveryAssignments(DeliveryAssignmentRequestDto request);

    ResponseEntity<DeliveryAssignmentResponseDto> getDeliveryAssignments(Long deliveryAssignmentId);

    ResponseEntity<List<DeliveryAssignmentResponseDto>> getAllDeliveryAssignments();

    ResponseEntity<List<DeliveryAssignmentResponseDto>> getAssignmentsByDeliveryPerson(Long deliveryAgentId);

    ResponseEntity<DeliveryAssignmentResponseDto> updateDeliveryStatus(Long deliveryAssignmentId,
            DeliveryStatusRequestDto orderStatus);

    
}
