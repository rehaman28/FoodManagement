package com.om;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableFeignClients 
@OpenAPIDefinition(
	info = @Info(
		title = "Order Management API v1",
		version = "v1",
		description = "Order Management creates and manages food orders, calculates prices using restaurant data, and exposes order lifecycle operations."
	)
)
public class OrdermanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdermanagementApplication.class, args);
	}

}
