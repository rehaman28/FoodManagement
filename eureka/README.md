# Eureka Service Discovery

## Description

The Eureka service is the service-discovery component of FoodManagement. It maintains the registry of the business services so that services can discover each other by logical service name instead of relying on fixed host/port addresses.

Application:

    eureka

Port:

    8761

Dashboard:

    http://localhost:8761

## Responsibilities

- Maintain the service registry
- Accept Eureka client registrations
- Track service instances
- Support service discovery for Spring Cloud clients

## Current registered services

| Service | Service ID | Port |
|---|---|---:|
| User Management | usermanagement | 8002 |
| Restaurant Management | restaurantmangement | 8001 |
| Order Management | ordermanagement | 8003 |
| Delivery Management | deliverymanagement | 8004 |

## Configuration

The Eureka server is configured not to register itself as a client:

    eureka.client.register-with-eureka=false
    eureka.client.fetch-registry=false

Business services are configured to register with:

    http://localhost:8761/eureka/

## Start the server

Run the Eureka application and open:

    http://localhost:8761

After starting the four business services, their application names should appear in the registry.

## Basic verification

    Start Eureka
        |
        v
    Open http://localhost:8761
        |
        v
    Start User Management
        |
        v
    Start Restaurant Management
        |
        v
    Start Order Management
        |
        v
    Start Delivery Management
        |
        v
    Review registered applications

## Role of Eureka in the roadmap

Eureka is the foundation for service discovery.

Phase 9:

    Services register with Eureka

Phase 10:

    Order Management uses OpenFeign
        |
        v
    Eureka resolves restaurantmangement
        |
        v
    Restaurant Management instance

Later phases can use the same discovery model when additional synchronous service clients are introduced.

## No business APIs

Eureka does not own users, restaurants, orders, or delivery data. It is infrastructure for service registration and discovery.
