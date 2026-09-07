package com.dm.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dm.Exception.DeliveryPersonNotFoundException;
import com.dm.dao.DeliveryPersonRepository;
import com.dm.dto.DeliveryPersonRequestDto;
import com.dm.dto.DeliveryPersonResponeDto;
import com.dm.model.DeliveryPerson;

@Service 
public class DeliveryPersonServiceImpl implements DeliveryPersonService{

    private  final DeliveryPersonRepository deliveryPersonRepository;
    

    public DeliveryPersonServiceImpl(DeliveryPersonRepository deliveryPersonRepository) {
        this.deliveryPersonRepository = deliveryPersonRepository;
    }


    @Override
    public ResponseEntity<DeliveryPersonResponeDto> AddDeliveryPerson(DeliveryPersonRequestDto requestDto) {

        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setDeliveryAgentName(requestDto.getDeliveryAgentName());
        deliveryPerson.setDelieryAgentAadhar(requestDto.getDelieryAgentAadhar());
        deliveryPerson.setDeliveryAgentPhone(requestDto.getDeliveryAgentPhone());
        deliveryPerson.setDeliveryAgentEmail(requestDto.getDeliveryAgentEmail());
        deliveryPerson.setAgentAvailable(requestDto.isAgentAvailable());

        DeliveryPerson savedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(buildDeliveryPersonResponeDto(savedDeliveryPerson));
    }

    @Override
    public List<DeliveryPersonResponeDto> getAllDeliveryPersonDetais() {
        return deliveryPersonRepository.findAll()
                .stream()
                .map(deliveryPerson -> buildDeliveryPersonResponeDto(deliveryPerson))
                .toList();
    }

    @Override
    public ResponseEntity<DeliveryPersonResponeDto> getDeliveryPersonDetais(Long deliveryAgentId) {
        DeliveryPerson deliveryPerson = findDeliveryPersonById(deliveryAgentId);
        return  ResponseEntity.status(HttpStatus.OK).body(buildDeliveryPersonResponeDto(deliveryPerson));
    }
    
    @Override
    public ResponseEntity<DeliveryPersonResponeDto> updateDeliveryPersonDetails(Long deliveryAgentId,DeliveryPersonRequestDto requestDto) {
        DeliveryPerson deliveryPerson = findDeliveryPersonById(deliveryAgentId);
        if(requestDto.getDelieryAgentAadhar()!=null){
            deliveryPerson.setDelieryAgentAadhar(requestDto.getDelieryAgentAadhar());
        }
        if (requestDto.getDeliveryAgentEmail()!=null) {
            deliveryPerson.setDeliveryAgentEmail(requestDto.getDeliveryAgentEmail());
        }
        if (requestDto.getDeliveryAgentName()!=null) {
            deliveryPerson.setDeliveryAgentName(requestDto.getDeliveryAgentName());   
        }
        if (requestDto.getDeliveryAgentPhone()!=null) {
            deliveryPerson.setDeliveryAgentPhone(requestDto.getDeliveryAgentPhone());
        }
        return ResponseEntity.status(HttpStatus.OK).body(buildDeliveryPersonResponeDto(deliveryPersonRepository.save(deliveryPerson)));
    }

    private DeliveryPerson findDeliveryPersonById(Long deliveryAgentId) {
        return deliveryPersonRepository.findById(deliveryAgentId)
                .orElseThrow(() -> new DeliveryPersonNotFoundException(
                        "Delivery Agent not Found with Id: " + deliveryAgentId));
    }

    //Helper Method to Return DeliveryPersonResponseDto from DeliveryPerson Object
    private DeliveryPersonResponeDto buildDeliveryPersonResponeDto(DeliveryPerson agent) {
        DeliveryPersonResponeDto responeDto = new DeliveryPersonResponeDto(
            agent.getDeliveryAgentId(),
            agent.getDeliveryAgentName(),
            agent.getDeliveryAgentPhone(),
            agent.isAgentAvailable()
        );
        return  responeDto;
    }
        
}
