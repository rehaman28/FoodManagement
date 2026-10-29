package com.dm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dm.dto.DeliveryAssignmentRequestDto;
import com.dm.dto.DeliveryAssignmentResponseDto;
import com.dm.dto.DeliveryStatusRequestDto;
import com.dm.service.DeliveryAssignmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequestMapping ("/delivery-assignments")
@Produces(MediaType.APPLICATION_JSON)
@Tag(
    name = "Delivery Assignment Resource",
    description = "Delivery Assignment Resource Controller"
)
public class DeliveryAssignmentController {

    private  final DeliveryAssignmentService deliveryAssignmentService;

    public DeliveryAssignmentController(DeliveryAssignmentService deliveryAssignmentService) {
        this.deliveryAssignmentService = deliveryAssignmentService;
    }

    @Operation(summary = "Post Delivery Assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Delivery Assignment Posted Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<DeliveryAssignmentResponseDto> postDeliveryAssignments(
            @Valid @RequestBody DeliveryAssignmentRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deliveryAssignmentService.postDeliveryAssignments(request));
    }

    @GetMapping("/{deliveryAssignmentId}")
    @Operation(summary = "Get a delivery assignment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery assignment retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Delivery assignment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DeliveryAssignmentResponseDto> getDeliveryAssignments(@PathVariable (name = "deliveryAssignmentId") Long deliveryAssignmentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deliveryAssignmentService.getDeliveryAssignments(deliveryAssignmentId));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all delivery assignments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery assignments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DeliveryAssignmentResponseDto>> getAllDeliveryAssignments(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(deliveryAssignmentService.getAllDeliveryAssignments());
    }

    @GetMapping("/delivery-person/{deliveryAgentId}/assignments")
    @Operation(summary = "Get delivery assignments for a delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery assignments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DeliveryAssignmentResponseDto>>
    getAssignmentsByDeliveryPerson(
            @PathVariable(name = "deliveryAgentId") Long deliveryAgentId) {

        return ResponseEntity.status(HttpStatus.OK).body(deliveryAssignmentService
                .getAssignmentsByDeliveryPerson(deliveryAgentId));
    }

    @PatchMapping("/{deliveryAssignmentId}/status")
    @Operation(summary = "Update delivery assignment status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery assignment status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Delivery assignment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DeliveryAssignmentResponseDto> updateOrderStatus(
            @PathVariable (name = "deliveryAssignmentId") Long deliveryAssignmentId,
            @Valid @RequestBody DeliveryStatusRequestDto orderStatus) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deliveryAssignmentService.updateDeliveryStatus(deliveryAssignmentId, orderStatus));
    }

    
}
