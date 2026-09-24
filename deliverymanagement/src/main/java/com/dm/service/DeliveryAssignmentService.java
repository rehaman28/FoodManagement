package com.dm.service;

import java.util.List;

import com.dm.dto.DeliveryAssignmentRequestDto;
import com.dm.dto.DeliveryAssignmentResponseDto;
import com.dm.dto.DeliveryStatusRequestDto;

/**
 * DeliveryAssignmentService
 */
public interface DeliveryAssignmentService {

    DeliveryAssignmentResponseDto postDeliveryAssignments(DeliveryAssignmentRequestDto request);

    DeliveryAssignmentResponseDto getDeliveryAssignments(Long deliveryAssignmentId);

    List<DeliveryAssignmentResponseDto> getAllDeliveryAssignments();

    List<DeliveryAssignmentResponseDto> getAssignmentsByDeliveryPerson(Long deliveryAgentId);

    DeliveryAssignmentResponseDto updateDeliveryStatus(Long deliveryAssignmentId,
            DeliveryStatusRequestDto orderStatus);

    
}
