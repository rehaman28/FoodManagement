package com.om.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request data for placing an order.")
public class OrderRequestDto {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than zero")
    @Schema(description = "Identifier of the user placing the order.")
    private Long userId;
    @NotNull(message = "Restaurant ID is required")
    @Positive(message = "Restaurant ID must be greater than zero")
    @Schema(description = "Identifier of the restaurant.")
    private Long restaurantId;
    
    @JsonProperty("OrderingItems")
    @NotEmpty 
    @Valid 
    @Schema(description = "Items included in the order.")
    private List<OrderItemRequestDto> orderItemsRequest;    
}
