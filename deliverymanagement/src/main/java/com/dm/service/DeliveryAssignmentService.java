package com.dm.service;

import java.util.List;

import com.dm.dto.request.DeliveryAssignmentRequestDto;
import com.dm.dto.response.DeliveryAssignmentResponseDto;
import com.dm.dto.request.DeliveryStatusRequestDto;

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
