package com.dm.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dm.Exception.DeliverAssignmentNotFoundException;
import com.dm.Exception.DeliveryPersonNotFoundException;
import com.dm.Exception.InvalidDeliveryStatusTransitionException;
import com.dm.dao.DeliveryAssignmentRepository;
import com.dm.dao.DeliveryPersonRepository;
import com.dm.dto.DeliveryAssignmentRequestDto;
import com.dm.dto.DeliveryAssignmentResponseDto;
import com.dm.dto.DeliveryStatusRequestDto;
import com.dm.model.DeliveryAssignment;
import com.dm.model.DeliveryPerson;
import com.dm.model.DeliveryStatus;

@Service 
public class DeliveryAssignmentServiceImpl implements DeliveryAssignmentService {

    private  final DeliveryAssignmentRepository deliveryAssignmentRepository;
    private final DeliveryPersonRepository deliveryPersonRepository;
    

    public DeliveryAssignmentServiceImpl(DeliveryAssignmentRepository deliveryAssignmentRepository,
            DeliveryPersonRepository deliveryPersonRepository) {
        this.deliveryAssignmentRepository = deliveryAssignmentRepository;
        this.deliveryPersonRepository = deliveryPersonRepository;
    }


    @Override
    public ResponseEntity<DeliveryAssignmentResponseDto> postDeliveryAssignments(DeliveryAssignmentRequestDto request) {
        DeliveryAssignment deliveryAssignment = buidlDeliveryAssignmentFromRequest(request);
        DeliveryAssignment savedDeliveryAssignment = deliveryAssignmentRepository.save(deliveryAssignment);
        return  ResponseEntity.status(HttpStatus.CREATED)
                            .body(buildDeliveryAssignmentResponeDtoFromDeliveryAssignment(savedDeliveryAssignment));
    }

    @Override
    public ResponseEntity<DeliveryAssignmentResponseDto> getDeliveryAssignments(Long deliveryAssignmentId) {
            DeliveryAssignment deliveryAssignment = findDeliveryAssignmentById(deliveryAssignmentId);
      return  ResponseEntity.status(HttpStatus.OK)
                            .body(buildDeliveryAssignmentResponeDtoFromDeliveryAssignment(deliveryAssignment));
    }

    @Override
    public ResponseEntity<List<DeliveryAssignmentResponseDto>> getAllDeliveryAssignments() {
        List<DeliveryAssignmentResponseDto> responseDtos = deliveryAssignmentRepository.findAll()
            .stream()
            .map(this::buildDeliveryAssignmentResponeDtoFromDeliveryAssignment)
            .toList();

        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @Override
    public ResponseEntity<List<DeliveryAssignmentResponseDto>> getAssignmentsByDeliveryPerson(Long deliveryAgentId) {
        List<DeliveryAssignmentResponseDto> responseDtos = deliveryAssignmentRepository.findByDeliveryPersonDeliveryAgentId(deliveryAgentId)
            .stream()
            .map(this::buildDeliveryAssignmentResponeDtoFromDeliveryAssignment)
            .toList();

        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @Override
    public ResponseEntity<DeliveryAssignmentResponseDto> updateDeliveryStatus(Long deliveryAssignmentId,
            DeliveryStatusRequestDto status) {

            DeliveryAssignment deliveryAssignment = findDeliveryAssignmentById(deliveryAssignmentId);
            DeliveryStatus currentStatus = deliveryAssignment.getDeliveryStatus();

            DeliveryStatus requestedStatus =status.getDeliveryStatus();

            if (!deliveryAssignment.getDeliveryPerson().isAgentAvailable()) {
                throw new DeliverAssignmentNotFoundException("Delivery Agent was not available");
            } else {
                if (!DeliveryStatus.isValidTransition(currentStatus, requestedStatus)) {

                    throw new InvalidDeliveryStatusTransitionException(
                            "Invalid delivery status transition from "
                                    + currentStatus + " to " + requestedStatus);
                }
                deliveryAssignment.setDeliveryStatus(requestedStatus);
            }
        return  ResponseEntity.status(HttpStatus.OK)
            .body(buildDeliveryAssignmentResponeDtoFromDeliveryAssignment(deliveryAssignmentRepository.save(deliveryAssignment)));
    
    }

    //Helper Methods
    private DeliveryAssignment findDeliveryAssignmentById(Long deliveryAssignmentId) {
        return deliveryAssignmentRepository.findById(deliveryAssignmentId)
                .orElseThrow(() -> new DeliverAssignmentNotFoundException(
                        "Delivery Assignment not found with id: " + deliveryAssignmentId));
    }

    private DeliveryAssignmentResponseDto buildDeliveryAssignmentResponeDtoFromDeliveryAssignment(DeliveryAssignment savedDeliveryAssignment) {
        return  new  DeliveryAssignmentResponseDto(
            savedDeliveryAssignment.getDeliveryAssignmentId(),
            savedDeliveryAssignment.getDeliveryStatus(),
            savedDeliveryAssignment.getOrderId(),
            savedDeliveryAssignment.getAssignmentDate(),
            savedDeliveryAssignment.getDeliveryPerson().getDeliveryAgentId(),
            savedDeliveryAssignment.getDeliveryPerson().getDeliveryAgentName()
        );
    }

    private DeliveryAssignment buidlDeliveryAssignmentFromRequest(DeliveryAssignmentRequestDto request){
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(request.getDeliveryAgentId())
                .orElseThrow(() -> new DeliveryPersonNotFoundException(
                        "Delivery Agent not Found with Id: " + request.getDeliveryAgentId()));

        DeliveryAssignment deliveryAssignment = new  DeliveryAssignment();
        deliveryAssignment.setAssignmentDate(LocalDateTime.now());
        deliveryAssignment.setDeliveryStatus(DeliveryStatus.ASSIGNED);
        deliveryAssignment.setOrderId(request.getOrderId());
        deliveryAssignment.setDeliveryPerson(deliveryPerson);
        return  deliveryAssignment;

    }

 
}
