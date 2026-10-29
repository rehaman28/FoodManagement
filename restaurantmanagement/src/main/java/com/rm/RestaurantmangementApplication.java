package com.rm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition (
	info = @Info (
		title = "Restaurant Mangement API v1",
		version = "v1",
		description = "Restaurant Management owns restaurants, restaurant addresses, food items, ratings, and restaurant/item retrieval used by other services."
	)
)
public class RestaurantmangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantmangementApplication.class, args);
	}

}
