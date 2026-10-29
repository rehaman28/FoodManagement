package com.dm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dm.dto.request.DeliveryPersonRequestDto;
import com.dm.dto.response.DeliveryPersonResponeDto;
import com.dm.dto.response.ErrorResponseDto;
import com.dm.service.DeliveryPersonService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dm.constants.ApplicationConstants;


@RestController 
@RequestMapping(value = ApplicationConstants.DELIVERY_PERSON_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Delivery Person Resource", description = "Delivery person resource controller")
public class DeliveryPersonController {
    
    private  final DeliveryPersonService deliveryPersonService;

    public DeliveryPersonController(DeliveryPersonService deliveryPersonService) {
        this.deliveryPersonService = deliveryPersonService;
    }
    
    //Post delivery person
    @PostMapping(value = ApplicationConstants.ADD_PERSON_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Delivery person added successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryPersonResponeDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<DeliveryPersonResponeDto> AddDeliveryPerson(@Valid @RequestBody DeliveryPersonRequestDto requestDto) {
        return  deliveryPersonService.AddDeliveryPerson(requestDto);
    }
    

    //Get Delivery Person using ID 
    @GetMapping(ApplicationConstants.DELIVERY_PERSON_ID_PATH)
    @Operation(summary = "Get a delivery person by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery person retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryPersonResponeDto.class))),
            @ApiResponse(responseCode = "404", description = "Delivery person not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<DeliveryPersonResponeDto> getDeliveryPerson(@PathVariable (name = "deliveryAgentId") Long deliveryAgentId) {
        return deliveryPersonService.getDeliveryPersonDetais(deliveryAgentId);
    }

    //Get All Delivery Persons 
    @GetMapping()
    @Operation(summary = "Get all delivery persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery persons retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DeliveryPersonResponeDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<DeliveryPersonResponeDto>> getAllDeliveryPerson() {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPersonService.getAllDeliveryPersonDetais());
    }

    //UpdateDeliveryPersonDetails
    @PatchMapping(value = ApplicationConstants.UPDATE_PERSON_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery person updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryPersonResponeDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Delivery person not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access to this service",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
     public ResponseEntity<DeliveryPersonResponeDto> updateDeliveryPerson(@PathVariable (name = "deliveryAgentId") Long deliveryAgentId, @Valid @RequestBody DeliveryPersonRequestDto requestDto) {
        return  deliveryPersonService.updateDeliveryPersonDetails(deliveryAgentId,requestDto);
    }

}