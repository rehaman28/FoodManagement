package com.dm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(DeliveryPersonServiceImpl.class);

    private  final DeliveryPersonRepository deliveryPersonRepository;
    

    public DeliveryPersonServiceImpl(DeliveryPersonRepository deliveryPersonRepository) {
        this.deliveryPersonRepository = deliveryPersonRepository;
    }


    @Override
    public ResponseEntity<DeliveryPersonResponeDto> AddDeliveryPerson(DeliveryPersonRequestDto requestDto) {

        logger.info("AddDeliveryPerson request received: {}", requestDto);

        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setDeliveryAgentName(requestDto.getDeliveryAgentName());
        deliveryPerson.setDeliveryAgentAadhar(requestDto.getDeliveryAgentAadhar());
        deliveryPerson.setDeliveryAgentPhone(requestDto.getDeliveryAgentPhone());
        deliveryPerson.setDeliveryAgentEmail(requestDto.getDeliveryAgentEmail());
        deliveryPerson.setAgentAvailable(requestDto.isAgentAvailable());

        logger.info("Mapped DeliveryPerson entity with agentAvailable={}", deliveryPerson.isAgentAvailable());

        DeliveryPerson savedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
        logger.info("Saved DeliveryPerson entity: {}", savedDeliveryPerson);

        DeliveryPersonResponeDto responseDto = buildDeliveryPersonResponeDto(savedDeliveryPerson);
        logger.info("Built response DTO: {}", responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Override
    public List<DeliveryPersonResponeDto> getAllDeliveryPersonDetais() {
        logger.info("getAllDeliveryPersonDetais called");

        List<DeliveryPersonResponeDto> responseDtos = deliveryPersonRepository.findAll()
                .stream()
                .map(deliveryPerson -> {
                    logger.info("Processing deliveryPerson in list: id={}, agentAvailable={}",
                            deliveryPerson.getDeliveryAgentId(), deliveryPerson.isAgentAvailable());
                    return buildDeliveryPersonResponeDto(deliveryPerson);
                })
                .toList();

        logger.info("getAllDeliveryPersonDetais returning {} items", responseDtos.size());
        return responseDtos;
    }

    @Override
    public ResponseEntity<DeliveryPersonResponeDto> getDeliveryPersonDetais(Long deliveryAgentId) {
        logger.info("getDeliveryPersonDetais called for deliveryAgentId={}", deliveryAgentId);

        DeliveryPerson deliveryPerson = findDeliveryPersonById(deliveryAgentId);
        logger.info("Fetched deliveryPerson from DB: id={}, agentAvailable={}",
                deliveryPerson.getDeliveryAgentId(), deliveryPerson.isAgentAvailable());

        DeliveryPersonResponeDto responseDto = buildDeliveryPersonResponeDto(deliveryPerson);
        logger.info("Returning responseDto for deliveryAgentId={}, responseDto={}", deliveryAgentId, responseDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    
    @Override
    public ResponseEntity<DeliveryPersonResponeDto> updateDeliveryPersonDetails(Long deliveryAgentId,DeliveryPersonRequestDto requestDto) {
        DeliveryPerson deliveryPerson = findDeliveryPersonById(deliveryAgentId);
        if(requestDto.getDeliveryAgentAadhar()!=null){
            deliveryPerson.setDeliveryAgentAadhar(requestDto.getDeliveryAgentAadhar());
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
        logger.info("buildDeliveryPersonResponeDto called for deliveryAgentId={}, agentAvailable={}",
                agent.getDeliveryAgentId(), agent.isAgentAvailable());

        return new DeliveryPersonResponeDto(
                agent.getDeliveryAgentId(),
                agent.getDeliveryAgentName(),
                agent.getDeliveryAgentAadhar(),
                agent.getDeliveryAgentPhone(),
                agent.getDeliveryAgentEmail(),
                agent.isAgentAvailable());
    }
        
}
