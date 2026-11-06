# User Management Service

## Description

User Management is responsible for user registration, user profile operations, authentication, password encoding, and user roles.

Application:

    usermanagement

Port:

    8002

Base URL:

    http://localhost:8002

Swagger UI:

    http://localhost:8002/swagger-ui/index.html

OpenAPI specification:

    http://localhost:8002/v3/api-docs

## Responsibilities

- Register users
- Retrieve users
- Update users
- Delete users
- Authenticate users
- Generate JWT tokens
- Assign USER / ADMIN roles
- Protect ADMIN-only APIs

## Main API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | \`/auth/login\` | Authenticate and generate JWT |
| POST | \`/users\` | Register a user |
| GET | \`/users\` | Get all users |
| GET | \`/users/{userId}\` | Get user by ID |
| PATCH | \`/users/{userId}\` | Update user |
| DELETE | \`/users/{userId}\` | Delete user |

## Authentication

### Generate USER JWT

Request:

    POST http://localhost:8002/auth/login

Body:

    {
      "email": "<valid-user-email>",
      "password": "<valid-user-password>"
    }

Response:

    {
      "token": "<JWT>"
    }

Use:

    Authorization: Bearer <JWT>

### Generate ADMIN JWT

Request:

    POST http://localhost:8002/auth/login

Body:

    {
      "email": "<valid-admin-email>",
      "password": "<valid-admin-password>"
    }

Response:

    {
      "token": "<JWT>"
    }

The ADMIN account must already exist.

## Register user

Request:

    POST http://localhost:8002/users

Body:

    {
      "userName": "Jacob",
      "userPhone": "9876543210",
      "email": "jacob@example.com",
      "password": "Password@123",
      "userAddresses": [
        {
          "doorNumber": "12-3",
          "street": "MG Road",
          "city": "Hyderabad",
          "district": "Hyderabad",
          "country": "India",
          "pincode": "500001"
        }
      ]
    }

Expected:

    201 Created

Example response:

    {
      "userId": 1,
      "userName": "Jacob",
      "userPhone": "9876543210"
    }

Registration is publicly accessible and creates the user as USER.

## Get user

Request:

    GET http://localhost:8002/users/{userId}

Header:

    Authorization: Bearer <JWT>

Expected:

    200 OK

Response:

    {
      "userId": 1,
      "userName": "Jacob",
      "userPhone": "9876543210"
    }

## Get all users

Request:

    GET http://localhost:8002/users

Header:

    Authorization: Bearer <ADMIN_JWT>

Expected:

    200 OK

Response:

    [
      {
        "userId": 1,
        "userName": "Jacob",
        "userPhone": "9876543210"
      }
    ]

## Update user

Request:

    PATCH http://localhost:8002/users/{userId}

Body:

    {
      "userName": "Jacob Updated"
    }

Expected:

    200 OK

## Delete user

Request:

    DELETE http://localhost:8002/users/{userId}

Header:

    Authorization: Bearer <ADMIN_JWT>

Expected:

    204 No Content

## Basic testing flow

    Start User Management :8002
        |
        v
    POST /auth/login
        |
        v
    Receive { "token": "<JWT>" }
        |
        +--> USER JWT -> authenticated USER APIs
        |
        +--> ADMIN JWT -> ADMIN APIs
        |
        v
    Run CRUD requests in Postman

## Postman collection

Use:

    /postman/FoodManagement-Login.postman_collection.json

and:

    /postman/FoodManagement-UserManagement.postman_collection.json

Environment:

    FoodManagement-USER
    FoodManagement-ADMIN

## Role model

- USER: normal authenticated user
- ADMIN: administrative user with access to user-management administration endpoints

## Implementation scope

Current implementation includes:

- Spring Boot REST APIs
- JPA / MySQL
- Bean validation
- Password encoding
- JWT authentication
- Role-based authorization
- Eureka client registration

Future platform phases add service-to-service communication, messaging, resilience, testing, containerization, CI/CD, and observability.


## Phase 11 validation status

Status: Complete — User Management participation in synchronous and Gateway validation successful

Validated successfully:

- Order Management -> User Management synchronous validation via OpenFeign + Eureka — passed
- Authorization header/JWT propagation — passed
- Existing-user lookup flow — passed
- Non-existent user lookup mapped to HTTP 404 — passed
- API Gateway `/auth/**` and `/users/**` routing — passed
- Gateway login flow — passed
- Gateway protected User Management endpoints without JWT -> 401 — passed
- Gateway downstream USER / ADMIN authorization behavior — passed

Existing direct-service Postman testing remains supported through `userServiceUrl`.


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

Management port: 9102

    http://localhost:9102/actuator/health
    http://localhost:9102/actuator/prometheus

Both were validated locally with HTTP 200.

Prometheus target:

    host.docker.internal:9102

Actuator is permitted on the dedicated management port so monitoring does not require a business JWT. User Management business endpoints remain protected by the existing JWT / USER / ADMIN rules.

The service emits HTTP request histogram metrics used by the Grafana P95/P99 latency panels.

Full service containerization is deferred to the later Docker phase; the current Docker Compose stack runs Prometheus and Grafana only.
## Current roadmap status

Project-level architecture and execution roadmap:

    /FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md

Current gate:

    API Gateway + synchronous communication -> Complete
    Observability Foundation -> Complete
    Fault Tolerance -> Next: Timeouts
    Failure-Path Testing -> After resilience controls
    Observability Expansion -> After failure-path testing
    Kafka -> Deferred until the resilience/observability gates pass

Full application Dockerization, Kubernetes, CI/CD, AWS/cloud deployment, and production hardening remain later platform phases.

