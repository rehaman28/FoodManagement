# API Gateway

## Description

The API Gateway is the external edge service for FoodManagement. It provides a single entry point for client requests, performs edge-level JWT validation, and routes requests to business services using Eureka service discovery.

Application / service ID:

    api-gateway

Port:

    8080

Base URL:

    http://localhost:8080

Actuator health:

    http://localhost:8080/actuator/health

## Responsibilities

- Provide a single client-facing entry point
- Route requests using Eureka service discovery and Spring Cloud LoadBalancer
- Validate JWTs at the application edge
- Allow public authentication and user-registration endpoints
- Preserve downstream USER / ADMIN authorization
- Keep business-service endpoint paths unchanged

## Gateway routes

| Path | Target service |
|---|---|
| `/auth/**` | User Management |
| `/users/**` | User Management |
| `/restaurants/**` | Restaurant Management |
| `/orders/**` | Order Management |
| `/delivery-person/**` | Delivery Management |
| `/delivery-assignments/**` | Delivery Management |

Routes use Eureka service IDs:

    lb://usermanagement
    lb://restaurantmanagement
    lb://ordermanagement
    lb://deliverymanagement

## JWT edge validation

The Gateway validates the incoming JWT before forwarding protected requests.

Public endpoints:

    POST /auth/login
    POST /users
    OPTIONS

Protected requests require:

    Authorization: Bearer <JWT>

Missing, malformed, invalid, or expired JWTs are rejected at the Gateway with:

    401 Unauthorized

The Gateway validates authentication but does not replace downstream role-based authorization. USER / ADMIN authorization remains enforced by the business services.

## Postman regression

Dedicated collection:

    /postman/FoodManagement-API-Gateway.postman_collection.json

Overall regression:

    /postman/FoodManagement-Overall.postman_collection.json

Gateway environment variable:

    gatewayBaseUrl=http://localhost:8080

Direct-service variables remain available for troubleshooting:

    userServiceUrl=http://localhost:8002
    restaurantServiceUrl=http://localhost:8001
    orderServiceUrl=http://localhost:8003
    deliveryServiceUrl=http://localhost:8004

## Phase 11 validation status

Status: Complete — synchronous communication and API Gateway edge validation successful

### Synchronous validation

- Order -> User through OpenFeign + Eureka — passed
- Order -> Restaurant through OpenFeign + Eureka — passed
- JWT / Authorization propagation through synchronous calls — passed
- Existing-user validation — passed
- Non-existent-user handling with HTTP 404 — passed
- Restaurant/item lookup and error handling — passed
- Eureka service discovery and multi-instance validation — passed

### API Gateway validation

- Gateway registered with Eureka — passed
- User route `/auth/**` and `/users/**` — passed
- Restaurant route `/restaurants/**` — passed
- Order route `/orders/**` — passed
- Delivery routes `/delivery-person/**` and `/delivery-assignments/**` — passed
- Login through Gateway — passed
- Protected request without JWT -> 401 — passed
- Invalid JWT -> 401 — passed
- USER / ADMIN downstream RBAC preservation — passed
- Unknown Gateway route -> 404 — passed
- Existing direct-service Postman regression suites remained intact — passed

## Architecture

    Client
       |
       v
    API Gateway :8080
       |
       +--> Eureka :8761
       |
       +--> User Management :8002
       +--> Restaurant Management :8001
       +--> Order Management :8003
       +--> Delivery Management :8004

## Phase 11B

Kafka / event-driven communication between Order Management and Delivery Management remains the next planned part of Phase 11. The synchronous and Gateway portions of Phase 11 are complete and validated.
