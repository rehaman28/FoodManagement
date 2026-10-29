package com.dm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Delivery Management API v1",
		version = "v1",
		description = "Delivery Management handles delivery agents, delivery assignments, assignment retrieval, status transitions, and agent availability."
	)
)
public class DeliverymanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliverymanagementApplication.class, args);
	}

}
