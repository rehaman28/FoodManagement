package com.dm.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dm.Exception.DeliverAssignmentNotFoundException;
import com.dm.Exception.DeliveryAgentNotAvailableException;
import com.dm.Exception.DeliveryPersonNotFoundException;
import com.dm.Exception.InvalidDeliveryStatusTransitionException;
import com.dm.Exception.OrderAlreadyAssignedException;
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
    public DeliveryAssignmentResponseDto postDeliveryAssignments(DeliveryAssignmentRequestDto request) {
        DeliveryAssignment deliveryAssignment = buildDeliveryAssignmentFromRequest  (request);
        DeliveryAssignment savedDeliveryAssignment = deliveryAssignmentRepository.save(deliveryAssignment);
        return  buildDeliveryAssignmentResponeDtoFromDeliveryAssignment(savedDeliveryAssignment);
    }

    @Override
    public DeliveryAssignmentResponseDto getDeliveryAssignments(Long deliveryAssignmentId) {
            DeliveryAssignment deliveryAssignment = findDeliveryAssignmentById(deliveryAssignmentId);
      return  buildDeliveryAssignmentResponeDtoFromDeliveryAssignment(deliveryAssignment);
    }

    @Override
    public List<DeliveryAssignmentResponseDto> getAllDeliveryAssignments() {
        return deliveryAssignmentRepository.findAll()
            .stream()
            .map(this::buildDeliveryAssignmentResponeDtoFromDeliveryAssignment)
            .toList();        
    }

    @Override
    public List<DeliveryAssignmentResponseDto> getAssignmentsByDeliveryPerson(Long deliveryAgentId) {
        
        return deliveryAssignmentRepository.findByDeliveryPersonDeliveryAgentId(deliveryAgentId)
            .stream()
            .map(this::buildDeliveryAssignmentResponeDtoFromDeliveryAssignment)
            .toList();

    }

    @Override
    public DeliveryAssignmentResponseDto updateDeliveryStatus(Long deliveryAssignmentId,
            DeliveryStatusRequestDto status) {

            DeliveryAssignment deliveryAssignment = findDeliveryAssignmentById(deliveryAssignmentId);

            DeliveryStatus currentStatus = deliveryAssignment.getDeliveryStatus();

            DeliveryStatus requestedStatus =status.getDeliveryStatus();

    
            if (!DeliveryStatus.isValidTransition(currentStatus, requestedStatus)) {

                throw new InvalidDeliveryStatusTransitionException(
                        "Invalid delivery status transition from "
                                + currentStatus + " to " + requestedStatus);
            }
            deliveryAssignment.setDeliveryStatus(requestedStatus);

            if (requestedStatus == DeliveryStatus.DELIVERED
                    || requestedStatus == DeliveryStatus.REFUSED) {

                DeliveryPerson deliveryPerson = deliveryAssignment.getDeliveryPerson();

                deliveryPerson.setAgentAvailable(true);
                deliveryPersonRepository.save(deliveryPerson);

            }
            
        return  buildDeliveryAssignmentResponeDtoFromDeliveryAssignment(deliveryAssignmentRepository.save(deliveryAssignment));
    
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

    private DeliveryAssignment buildDeliveryAssignmentFromRequest(DeliveryAssignmentRequestDto request){
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(request.getDeliveryAgentId())
                .orElseThrow(() -> new DeliveryPersonNotFoundException(
                        "Delivery Agent not Found with Id: " + request.getDeliveryAgentId()));

        if (!deliveryPerson.isAgentAvailable()) {
            throw new DeliveryAgentNotAvailableException(
                    "Delivery Agent is currently unavailable with Id: "
                            + request.getDeliveryAgentId());
        }

        if (deliveryAssignmentRepository
                .findByOrderId(request.getOrderId())
                .isPresent()) {

            throw new OrderAlreadyAssignedException(
                    "Order is already assigned: "
                            + request.getOrderId());
        }
        deliveryPerson.setAgentAvailable(false);
        deliveryPersonRepository.save(deliveryPerson);

        DeliveryAssignment deliveryAssignment = new  DeliveryAssignment();
        deliveryAssignment.setAssignmentDate(LocalDateTime.now());
        deliveryAssignment.setDeliveryStatus(DeliveryStatus.ASSIGNED);
        deliveryAssignment.setOrderId(request.getOrderId());
        deliveryAssignment.setDeliveryPerson(deliveryPerson);
        return  deliveryAssignment;

    }

 
}
