# Delivery Management Service

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


## API testing with sample data

Canonical endpoint:

    http://localhost:8080

Postman uses testBaseUrl for all requests. Direct Delivery Management port 8004 remains available for troubleshooting.

### Create delivery agent

    POST {{testBaseUrl}}/delivery-person/addperson

Request:

    {
      "deliveryAgentName": "Rahul Kumar",
      "deliveryAgentAadhar": "234567890123",
      "deliveryAgentPhone": "9876543211",
      "deliveryAgentEmail": "rahul@example.com"
    }

Response:

    {
      "deliveryAgentId": 601,
      "deliveryAgentName": "Rahul Kumar",
      "deliveryAgentAadhar": "234567890123",
      "deliveryAgentPhone": "9876543211",
      "deliveryAgentEmail": "rahul@example.com",
      "isAgentAvailable": true
    }

### Create assignment

    POST {{testBaseUrl}}/delivery-assignments

Request:

    {
      "orderId": "{{orderId}}",
      "deliveryAgentId": "{{deliveryAgentId}}"
    }

Response:

    {
      "deliveryAssignmentId": 701,
      "deliveryStatus": "ASSIGNED",
      "orderId": 401,
      "assignmentDate": "2026-11-09T16:00:00",
      "deliveryAgentId": 601,
      "deliveryAgentName": "Rahul Kumar"
    }


## Phase participation

The master [planned phase roadmap](../README.md#planned-phases) is the authoritative project scope. This table shows how this service participates in the planned work.

| Phase | Planned status | Sub-phases / implementation steps |
|---|---|---|
| [Phase 4 — Delivery Management](../README.md#phase-4--delivery-management) | Pending | Core delivery flows are implemented; event-driven integration and broader hardening remain. |
| [Phase 5 — API Test Baseline](../README.md#phase-5--api-test-baseline) | Completed | Baseline Postman happy-path and negative-path API coverage. |
| [Phase 6 — JWT Authentication](../README.md#phase-6--jwt-authentication) | Completed | JWT authentication for protected delivery operations. |
| [Phase 7 — Role-Based Authorization](../README.md#phase-7--role-based-authorization) | Completed | USER/ADMIN access boundaries on delivery APIs. |
| [Phase 8 — Security Postman Regression](../README.md#phase-8--security-postman-regression) | Completed | Repeatable 401/403 security regression coverage. |
| [Phase 11B — Kafka / Event-Driven Architecture](../README.md#phase-11b--kafka--event-driven-architecture) | Pending | Order-to-Delivery events, producers/consumers, topics, retries, idempotency and integration tests. |
| [Phase 14 — Testing and Quality Engineering](../README.md#phase-14--testing-and-quality-engineering) | Pending | Automated unit, controller, repository, integration, security and contract-style tests. |
| [Phase 15 — Docker](../README.md#phase-15--docker) | Pending | Containerize the Delivery Management service. |
| [Phase 16 — Kubernetes](../README.md#phase-16--kubernetes) | Pending | Deployments, Services, configuration, secrets, probes and autoscaling. |
| [Phase 17 — CI/CD](../README.md#phase-17--ci-cd) | Pending | Automated build, test, image and deployment pipeline. |
| [Phase 18A — Observability Foundation](../README.md#phase-18a--observability-foundation) | Completed | Health, metrics, Prometheus and Grafana baseline. |
| [Phase 18C — Observability Expansion](../README.md#phase-18c--observability-expansion) | Pending | Centralized logs, tracing, correlation IDs, dashboards and alerts. |
| [Phase 19F — Application Integration and Event-Driven AWS](../README.md#phase-19f--application-integration-and-event-driven-aws) | Pending | AWS messaging integration and event-driven failure handling. |
| [Phase 19L — Final AWS Deployment](../README.md#phase-19l--final-aws-deployment) | Pending | Production deployment and end-to-end validation. |

## Roadmap status

See the [master phase plan](../README.md#planned-phases), [completed phases](../README.md#completed-phases), and [Pending Task Matrix](../README.md#pending-task-matrix). The phase-participation table above is the service-level view.

Project architecture reference: [FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md](../FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md).
