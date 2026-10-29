package com.dm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dm.dto.request.DeliveryAssignmentRequestDto;
import com.dm.dto.response.DeliveryAssignmentResponseDto;
import com.dm.dto.request.DeliveryStatusRequestDto;
import com.dm.dto.response.ErrorResponseDto;
import com.dm.service.DeliveryAssignmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dm.constants.ApplicationConstants;


@RestController 
@RequestMapping(value = ApplicationConstants.DELIVERY_ASSIGNMENTS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
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
            @ApiResponse(responseCode = "201", description = "Delivery Assignment Posted Successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryAssignmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
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
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeliveryAssignmentResponseDto> postDeliveryAssignments(
            @Valid @RequestBody DeliveryAssignmentRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deliveryAssignmentService.postDeliveryAssignments(request));
    }

    @GetMapping(ApplicationConstants.DELIVERY_ASSIGNMENT_ID_PATH)
    @Operation(summary = "Get a delivery assignment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery assignment retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryAssignmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Delivery assignment not found",
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
    public ResponseEntity<DeliveryAssignmentResponseDto> getDeliveryAssignments(@PathVariable (name = "deliveryAssignmentId") Long deliveryAssignmentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deliveryAssignmentService.getDeliveryAssignments(deliveryAssignmentId));
    }

    @GetMapping(ApplicationConstants.ALL_ASSIGNMENTS_PATH)
    @Operation(summary = "Get all delivery assignments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery assignments retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DeliveryAssignmentResponseDto.class)))),
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
    public ResponseEntity<List<DeliveryAssignmentResponseDto>> getAllDeliveryAssignments(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(deliveryAssignmentService.getAllDeliveryAssignments());
    }

    @GetMapping(ApplicationConstants.PERSON_ASSIGNMENTS_PATH)
    @Operation(summary = "Get delivery assignments for a delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery assignments retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DeliveryAssignmentResponseDto.class)))),
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
    public ResponseEntity<List<DeliveryAssignmentResponseDto>>
    getAssignmentsByDeliveryPerson(
            @PathVariable(name = "deliveryAgentId") Long deliveryAgentId) {

        return ResponseEntity.status(HttpStatus.OK).body(deliveryAssignmentService
                .getAssignmentsByDeliveryPerson(deliveryAgentId));
    }

    @PatchMapping(value = ApplicationConstants.ASSIGNMENT_STATUS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update delivery assignment status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery assignment status updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryAssignmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Delivery assignment not found",
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
    public ResponseEntity<DeliveryAssignmentResponseDto> updateOrderStatus(
            @PathVariable (name = "deliveryAssignmentId") Long deliveryAssignmentId,
            @Valid @RequestBody DeliveryStatusRequestDto orderStatus) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deliveryAssignmentService.updateDeliveryStatus(deliveryAssignmentId, orderStatus));
    }

    
}
