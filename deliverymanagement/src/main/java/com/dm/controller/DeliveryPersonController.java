package com.dm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dm.dto.DeliveryPersonRequestDto;
import com.dm.dto.DeliveryPersonResponeDto;
import com.dm.service.DeliveryPersonService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequestMapping("/delivery-person")
public class DeliveryPersonController {
    
    private  final DeliveryPersonService deliveryPersonService;

    public DeliveryPersonController(DeliveryPersonService deliveryPersonService) {
        this.deliveryPersonService = deliveryPersonService;
    }
    
    //Post delivery person
    @PostMapping("/addperson")
    public ResponseEntity<DeliveryPersonResponeDto> AddDeliveryPerson(@RequestBody DeliveryPersonRequestDto requestDto) {
        return  deliveryPersonService.AddDeliveryPerson(requestDto);
    }
    

    //Get Delivery Person using ID 
    @GetMapping("/{deliveryAgentId}")
    public ResponseEntity<DeliveryPersonResponeDto> getDeliveryPerson(@PathVariable (name = "deliveryAgentId") Long deliveryAgentId) {
        return deliveryPersonService.getDeliveryPersonDetais(deliveryAgentId);
    }

    //Get All Delivery Persons 
    @GetMapping()
    public ResponseEntity<List<DeliveryPersonResponeDto>> getAllDeliveryPerson() {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPersonService.getAllDeliveryPersonDetais());
    }

    //UpdateDeliveryPersonDetails
    @PatchMapping("/update/{deliveryAgentId}")
     public ResponseEntity<DeliveryPersonResponeDto> updateDeliveryPerson(@PathVariable (name = "deliveryAgentId") Long deliveryAgentId, @RequestBody DeliveryPersonRequestDto requestDto) {
        return  deliveryPersonService.updateDeliveryPersonDetails(deliveryAgentId,requestDto);
    }

}