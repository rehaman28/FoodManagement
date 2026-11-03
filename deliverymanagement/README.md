# Delivery Management Service

## Description

Delivery Management handles delivery agents, delivery assignments, assignment retrieval, delivery status transitions, and agent availability.

Application / service ID:

    deliverymanagement

Port:

    8004

Base URL:

    http://localhost:8004

Swagger UI:

    http://localhost:8004/swagger-ui/index.html

OpenAPI specification:

    http://localhost:8004/v3/api-docs

## Responsibilities

- Create delivery agents
- Retrieve delivery agents
- Update delivery agents
- Create delivery assignments
- Retrieve assignments
- Retrieve all assignments
- Retrieve assignments by delivery agent
- Update delivery status
- Track delivery-agent availability

## Delivery person APIs

| Method | Endpoint | Purpose |
|---|---|---|
| POST | \`/delivery-person/addperson\` | Create delivery agent |
| GET | \`/delivery-person/{deliveryAgentId}\` | Get delivery agent |
| GET | \`/delivery-person\` | Get all delivery agents |
| PATCH | \`/delivery-person/update/{deliveryAgentId}\` | Update delivery agent |

## Assignment APIs

| Method | Endpoint | Purpose |
|---|---|---|
| POST | \`/delivery-assignments\` | Create assignment |
| GET | \`/delivery-assignments/{deliveryAssignmentId}\` | Get assignment |
| GET | \`/delivery-assignments/all\` | Get all assignments |
| GET | \`/delivery-assignments/delivery-person/{deliveryAgentId}/assignments\` | Get assignments for agent |
| PATCH | \`/delivery-assignments/{deliveryAssignmentId}/status\` | Update delivery status |

## Authentication

Generate JWT through User Management:

    POST http://localhost:8002/auth/login

USER:

    {
      "email": "<valid-user-email>",
      "password": "<valid-user-password>"
    }

ADMIN:

    {
      "email": "<valid-admin-email>",
      "password": "<valid-admin-password>"
    }

Response:

    {
      "token": "<JWT>"
    }

Use:

    Authorization: Bearer <JWT>

Current authorization model gives Delivery Management administration endpoints to ADMIN.

## Create delivery person

Request:

    POST http://localhost:8004/delivery-person/addperson

Header:

    Authorization: Bearer <ADMIN_JWT>

Body:

    {
      "deliveryAgentName": "Rahul",
      "deliveryAgentAadhar": "234567890123",
      "deliveryAgentPhone": "9876543210",
      "deliveryAgentEmail": "rahul@example.com"
    }

Expected:

    2xx response

Example response shape:

    {
      "deliveryAgentId": 1,
      "deliveryAgentName": "Rahul",
      "deliveryAgentAadhar": "234567890123",
      "deliveryAgentPhone": "9876543210",
      "deliveryAgentEmail": "rahul@example.com",
      "isAgentAvailable": true
    }

## Get delivery person

Request:

    GET http://localhost:8004/delivery-person/{deliveryAgentId}

Header:

    Authorization: Bearer <ADMIN_JWT>

Expected:

    2xx response

## Create assignment

Request:

    POST http://localhost:8004/delivery-assignments

Header:

    Authorization: Bearer <ADMIN_JWT>

Body:

    {
      "orderId": 101,
      "deliveryAgentId": 1
    }

Expected:

    201 Created

Example response shape:

    {
      "orderId": 101,
      "deliveryAgentId": 1,
      "deliveryAgentName": "Rahul",
      "deliveryAssignmentId": 1,
      "assignmentDate": "2026-10-21T22:30:00",
      "deliveryStatus": "..."
    }

The exact status value depends on the current delivery lifecycle implementation.

## Update delivery status

Request:

    PATCH http://localhost:8004/delivery-assignments/{deliveryAssignmentId}/status

Header:

    Authorization: Bearer <ADMIN_JWT>

Body:

    {
      "deliveryStatus": "ACCEPTED"
    }

Expected:

    200 OK

Subsequent valid states depend on the current delivery state machine.

## Basic testing flow

    Generate ADMIN JWT
        |
        v
    Create delivery agent
        |
        v
    Create order first
        |
        v
    Create delivery assignment
        |
        v
    Update delivery status
        |
        v
    Retrieve assignment / agent
        |
        v
    Read JSON response

## Postman collection

Use:

    /postman/FoodManagement-DeliveryManagement.postman_collection.json

Environment:

    FoodManagement-ADMIN

Use:

    /postman/FoodManagement-Overall.postman_collection.json

for the complete baseline.


## Phase 11 validation status

Status: Complete — Delivery Management API Gateway edge validation successful

Validated successfully:

- API Gateway `/delivery-person/**` routing — passed
- API Gateway `/delivery-assignments/**` routing — passed
- Gateway protected Delivery endpoint without JWT -> 401 — passed
- Gateway invalid JWT handling -> 401 — passed
- Gateway USER / ADMIN downstream RBAC preservation — passed
- Existing direct Delivery Management Postman regression collection remains intact.
- Delivery Management remains ready for the planned Kafka event-driven Phase 11B work.


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


## Observability Foundation — Complete

Management port: 9104

    http://localhost:9104/actuator/health
    http://localhost:9104/actuator/prometheus

Both were validated locally with HTTP 200.

Prometheus target:

    host.docker.internal:9104

Actuator is exposed on the dedicated management port while Delivery Management business APIs retain JWT / USER / ADMIN protection.

The current Docker stack runs Prometheus and Grafana only. Full service containerization is deferred to the later Docker phase.