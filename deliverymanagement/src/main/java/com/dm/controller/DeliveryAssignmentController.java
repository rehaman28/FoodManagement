package com.dm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dm.dto.DeliveryAssignmentRequestDto;
import com.dm.dto.DeliveryAssignmentResponseDto;
import com.dm.dto.DeliveryStatusRequestDto;
import com.dm.service.DeliveryAssignmentService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequestMapping ("/delivery-assignments")
public class DeliveryAssignmentController {

    private  final DeliveryAssignmentService deliveryAssignmentService;

    public DeliveryAssignmentController(DeliveryAssignmentService deliveryAssignmentService) {
        this.deliveryAssignmentService = deliveryAssignmentService;
    }

    @PostMapping()
    public ResponseEntity<DeliveryAssignmentResponseDto> postDeliveryAssignments(@RequestBody DeliveryAssignmentRequestDto request) {
        
        return deliveryAssignmentService.postDeliveryAssignments(request);
    }

    @GetMapping("/{deliveryAssignmentId}")
    public ResponseEntity<DeliveryAssignmentResponseDto> getDeliveryAssignments(@PathVariable (name = "deliveryAssignmentId") Long deliveryAssignmentId) {
        return deliveryAssignmentService.getDeliveryAssignments(deliveryAssignmentId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryAssignmentResponseDto>> getAllDeliveryAssignments(){
        return deliveryAssignmentService.getAllDeliveryAssignments();
    }

    @GetMapping("/delivery-person/{deliveryAgentId}/assignments")
    public ResponseEntity<List<DeliveryAssignmentResponseDto>>
    getAssignmentsByDeliveryPerson(
            @PathVariable Long deliveryAgentId) {

        return deliveryAssignmentService
                .getAssignmentsByDeliveryPerson(deliveryAgentId);
    }

    @PatchMapping("/{deliveryAssignmentId}/status")
    public ResponseEntity<DeliveryAssignmentResponseDto> updateOrderStatus(
            @PathVariable (name = "deliveryAssignmentId") Long deliveryAssignmentId,
            @RequestBody DeliveryStatusRequestDto orderStatus) {
        return deliveryAssignmentService.updateDeliveryStatus(deliveryAssignmentId, orderStatus);
    }

    
}
