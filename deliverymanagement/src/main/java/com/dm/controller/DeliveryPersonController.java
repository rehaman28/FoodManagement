package com.dm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dm.dto.DeliveryPersonRequestDto;
import com.dm.dto.DeliveryPersonResponeDto;
import com.dm.service.DeliveryPersonService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequestMapping("/delivery-person")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Delivery Person Resource", description = "Delivery person resource controller")
public class DeliveryPersonController {
    
    private  final DeliveryPersonService deliveryPersonService;

    public DeliveryPersonController(DeliveryPersonService deliveryPersonService) {
        this.deliveryPersonService = deliveryPersonService;
    }
    
    //Post delivery person
    @PostMapping("/addperson")
    @Operation(summary = "Add a delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Delivery person added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DeliveryPersonResponeDto> AddDeliveryPerson(@Valid @RequestBody DeliveryPersonRequestDto requestDto) {
        return  deliveryPersonService.AddDeliveryPerson(requestDto);
    }
    

    //Get Delivery Person using ID 
    @GetMapping("/{deliveryAgentId}")
    @Operation(summary = "Get a delivery person by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery person retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DeliveryPersonResponeDto> getDeliveryPerson(@PathVariable (name = "deliveryAgentId") Long deliveryAgentId) {
        return deliveryPersonService.getDeliveryPersonDetais(deliveryAgentId);
    }

    //Get All Delivery Persons 
    @GetMapping()
    @Operation(summary = "Get all delivery persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery persons retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DeliveryPersonResponeDto>> getAllDeliveryPerson() {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPersonService.getAllDeliveryPersonDetais());
    }

    //UpdateDeliveryPersonDetails
    @PatchMapping("/update/{deliveryAgentId}")
    @Operation(summary = "Update a delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery person updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
     public ResponseEntity<DeliveryPersonResponeDto> updateDeliveryPerson(@PathVariable (name = "deliveryAgentId") Long deliveryAgentId, @Valid @RequestBody DeliveryPersonRequestDto requestDto) {
        return  deliveryPersonService.updateDeliveryPersonDetails(deliveryAgentId,requestDto);
    }

}