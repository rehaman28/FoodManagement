# API Gateway

## Wiki navigation

| Section | Link |
|---|---|
| Description | [Service description](#description) |
| Phase participation | [Phase participation](#phase-participation) |
| Planned phases | [Master phase plan](../README.md#planned-phases) |
| Completed phases | [Completed phases](../README.md#completed-phases) |
| Pending tasks | [Pending Task Matrix](../README.md#pending-task-matrix) |
| API testing | [API testing documentation](#api-testing-with-sample-data) |
| Full project wiki | [FoodManagement README](../README.md) |

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

## API testing with sample data

Canonical endpoint:

    http://localhost:8080

All Postman collections use this endpoint through testBaseUrl.

### Login through Gateway

    POST {{testBaseUrl}}/auth/login

Request:

    {
      "email": "<valid-user-or-admin-email>",
      "password": "<valid-password>"
    }

Response:

    {
      "token": "<JWT>"
    }

### Protected route without JWT

    GET {{testBaseUrl}}/restaurants

Response:

    HTTP 401 Unauthorized

### User route through Gateway

    GET {{testBaseUrl}}/users/{{currentUserId}}

Header:

    Authorization: Bearer <JWT>

Response:

    {
      "userId": 101,
      "userName": "Jacob",
      "userPhone": "9876543210",
      "userRoles": "USER"
    }

Direct service URLs remain available in Postman environments for troubleshooting.


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
- User administration promotion/demotion regression through Gateway — passed
- Last-ADMIN protection path validated through the User Management service — passed
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


## Phase participation

The master [planned phase roadmap](../README.md#planned-phases) is the authoritative project scope. This table shows how this service participates in the planned work.

| Phase | Planned status | Sub-phases / implementation steps |
|---|---|---|
| [Phase 11A — API Gateway](../README.md#phase-11a--api-gateway) | Completed | Gateway routes, Eureka-backed lb:// discovery, JWT edge validation, preserved paths, Postman regression. |
| [Phase 15 — Docker](../README.md#phase-15--docker) | Pending | Containerize the Gateway and externalize runtime configuration. |
| [Phase 16 — Kubernetes](../README.md#phase-16--kubernetes) | Pending | Deployment, Service, configuration, secrets, probes, ingress/load-balancer integration. |
| [Phase 17 — CI/CD](../README.md#phase-17--ci-cd) | Pending | Build/test/security, image publication, deployment, release and rollback automation. |
| [Phase 18A — Observability Foundation](../README.md#phase-18a--observability-foundation) | Completed | Actuator, metrics, Prometheus and Grafana baseline. |
| [Phase 18C — Observability Expansion](../README.md#phase-18c--observability-expansion) | Pending | Centralized logging, tracing, correlation IDs and alerts. |
| [Phase 19D — Containers, ECR, and EKS](../README.md#phase-19d--containers-ecr-and-eks) | Pending | ECR images and EKS deployment of the Gateway. |
| [Phase 19G — API, Edge, and Delivery](../README.md#phase-19g--api-edge-and-delivery) | Pending | ALB/API Gateway/Route 53/ACM/WAF integration and edge-architecture comparisons. |
| [Phase 19L — Final AWS Deployment](../README.md#phase-19l--final-aws-deployment) | Pending | Production routing, HTTPS, security, observability and final deployment validation. |

## Roadmap status

See the [master phase plan](../README.md#planned-phases), [completed phases](../README.md#completed-phases), and [Pending Task Matrix](../README.md#pending-task-matrix). The phase-participation table above is the service-level view.

Project architecture reference: [FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md](../FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md).
