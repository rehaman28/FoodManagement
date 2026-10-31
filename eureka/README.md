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
| Restaurant Management | restaurantmanagement | 8001 |
| Order Management | ordermanagement | 8003 |
| Delivery Management | deliverymanagement | 8004 |
| API Gateway | api-gateway | 8080 |

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
    Eureka resolves restaurantmanagement
        |
        v
    Restaurant Management instance

Later phases can use the same discovery model when additional synchronous service clients are introduced.

## No business APIs

Eureka does not own users, restaurants, orders, or delivery data. It is infrastructure for service registration and discovery.


## Phase 11 validation status

Status: Complete — service discovery and API Gateway edge validation successful

Validated successfully:

- API Gateway registered with Eureka as `api-gateway`
- Gateway resolved User Management through Eureka
- Gateway resolved Restaurant Management through Eureka
- Gateway resolved Order Management through Eureka
- Gateway resolved Delivery Management through Eureka
- Existing synchronous OpenFeign discovery remained functional
- Gateway routing and downstream authorization regression passed

The Kafka/event-driven portion remains planned under Phase 11B and does not change the completed Eureka/Gateway validation baseline.


## Platform roadmap sequence after Phase 11

The next implementation sequence is intentionally interleaved rather than completing all observability first:

1. Observability Foundation
   - Spring Boot Actuator
   - Micrometer metrics
   - Prometheus-format metrics
   - Basic Grafana dashboard
   - Baseline request rate, latency, and error rate
   - Optional early Correlation ID filter

2. Fault Tolerance
   - Timeouts
   - Retry policies
   - Circuit breakers
   - Fallbacks
   - Bulkhead isolation
   - Rate limiting

3. Failure-Path Testing
   - Deliberately exercise dependency failures and slow responses
   - Validate timeout, retry, breaker, fallback, isolation, and rate-limit behavior
   - Observe resilience metrics against the baseline

4. Observability Expansion
   - Centralized logging
   - Correlation IDs if not already introduced
   - Distributed tracing
   - Resilience/dependency dashboards
   - Alerts

5. Kafka / Event-Driven Architecture
   - Kafka starts only after the observability foundation, fault-tolerance implementation, failure-path testing, and observability expansion are complete.

This ordering is the project baseline for the next phases and prevents resilience tuning from being done without measurable baseline and failure visibility.
