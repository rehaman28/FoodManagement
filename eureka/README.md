# Eureka Service Discovery

## Wiki navigation

| Section | Link |
|---|---|
| Description | [Service description](#description) |
| Phase participation | [Phase participation](#phase-participation) |
| Planned phases | [Master phase plan](../README.md#planned-phases) |
| Completed phases | [Completed phases](../README.md#completed-phases) |
| Pending tasks | [Pending Task Matrix](../README.md#pending-task-matrix) |
| Full project wiki | [FoodManagement README](../README.md) |

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


## Phase participation

The master [planned phase roadmap](../README.md#planned-phases) is the authoritative project scope. This table shows how this service participates in the planned work.

| Phase | Planned status | Sub-phases / implementation steps |
|---|---|---|
| [Phase 9 — Eureka Service Discovery](../README.md#phase-9--eureka-service-discovery) | Completed | Eureka server, service registration, service IDs, discovery configuration and validation. |
| [Phase 10 — OpenFeign + Eureka](../README.md#phase-10--openfeign--eureka) | Completed | Order Management resolves downstream services through Eureka/OpenFeign. |
| [Phase 11A — API Gateway](../README.md#phase-11a--api-gateway) | Completed | Gateway uses Eureka-backed load-balanced routing. |
| [Phase 16 — Kubernetes](../README.md#phase-16--kubernetes) | Pending | Evaluate Kubernetes-native service discovery alongside existing Eureka. |
| [Phase 19D — Containers, ECR, and EKS](../README.md#phase-19d--containers-ecr-and-eks) | Pending | Container platform transition and EKS deployment architecture. |
| [Phase 19G — API, Edge, and Delivery](../README.md#phase-19g--api-edge-and-delivery) | Pending | Kubernetes discovery vs Eureka and gateway/edge architecture comparison. |
| [Phase 19L — Final AWS Deployment](../README.md#phase-19l--final-aws-deployment) | Pending | Finalize the service-discovery approach used in production deployment. |

## Roadmap status

See the [master phase plan](../README.md#planned-phases), [completed phases](../README.md#completed-phases), and [Pending Task Matrix](../README.md#pending-task-matrix). The phase-participation table above is the service-level view.

Project architecture reference: [FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md](../FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md).
