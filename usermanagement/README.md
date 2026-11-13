# User Management Service

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
| PATCH | \`/users/{userId}/role\` | ADMIN-only role management |

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

The public registration API intentionally creates USER accounts only. The first ADMIN must be bootstrapped through a controlled database/bootstrap process. After at least one ADMIN exists, an ADMIN can promote or demote users through `PATCH /users/{userId}/role`.

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

## User administration security

The security model is deliberately split between profile updates and role management.

### Registration
`POST /users` is public and always persists `Role.USER`. A client-supplied `role=ADMIN` cannot create an ADMIN account.

### Profile update
`PATCH /users/{userId}`
- USER -> authenticated user's own profile only
- ADMIN -> any user profile
- USER -> another user's profile returns 403 Forbidden
- role is not part of `UpdateUserRequestDto`

### Role management
`PATCH /users/{userId}/role`
- USER -> 403 Forbidden
- ADMIN -> allowed
- `ADMIN` and `USER` role transitions are explicit and separate from profile updates

Example:

    PATCH http://localhost:8002/users/{userId}/role

    {
      "role": "ADMIN"
    }

### Last-ADMIN protection
The service prevents demoting the final remaining ADMIN. At least one ADMIN must always remain.

### Postman validation
Use:
- `postman/FoodManagement-UserManagement.postman_collection.json`
- `postman/FoodManagement-API-Gateway.postman_collection.json`
- `postman/FoodManagement-Overall.postman_collection.json`

Set:
- `currentUserId` to the logged-in user's ID
- `otherUserId` is populated by the role-injection registration request
- `FoodManagement-USER` for USER tests
- `FoodManagement-ADMIN` for ADMIN tests

Validation status: **Complete — direct-service, Gateway, and Postman security regression scenarios passed.**

Validated:
- Public registration always creates USER
- USER cannot update another user's profile
- ADMIN can update another user's profile
- USER cannot change another user's role
- ADMIN can promote a USER
- ADMIN can demote another ADMIN
- Last-ADMIN protection works
- Gateway routing preserves the same USER / ADMIN authorization behavior

## API testing with sample data

Canonical endpoint:

    http://localhost:8080

Postman uses testBaseUrl for all requests. Direct User Management port 8002 remains available for troubleshooting.

### Create USER

    POST {{testBaseUrl}}/users

Request:

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

Response:

    {
      "userId": 101,
      "userName": "Jacob",
      "userPhone": "9876543210",
      "userRoles": "USER"
    }

### Update own profile

    PATCH {{testBaseUrl}}/users/{{currentUserId}}

Request:

    {
      "userName": "Jacob Updated"
    }

Response:

    {
      "userId": 101,
      "userName": "Jacob Updated",
      "userPhone": "9876543210",
      "userRoles": "USER"
    }

### ADMIN role management

    PATCH {{testBaseUrl}}/users/{{otherUserId}}/role

Request:

    {
      "role": "ADMIN"
    }

Response:

    {
      "userId": 102,
      "userName": "Admin Candidate",
      "userPhone": "9876543211",
      "userRoles": "ADMIN"
    }

Only ADMIN may change roles. The final ADMIN cannot be demoted.


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


## Phase participation

The master [planned phase roadmap](../README.md#planned-phases) is the authoritative project scope. This table shows how this service participates in the planned work.

| Phase | Planned status | Sub-phases / implementation steps |
|---|---|---|
| [Phase 1 — User Management](../README.md#phase-1--user-management) | Completed | Registration, profile CRUD, validation, persistence, password encoding and roles. |
| [Phase 5 — API Test Baseline](../README.md#phase-5--api-test-baseline) | Completed | Functional and negative-path Postman baseline. |
| [Phase 6 — JWT Authentication](../README.md#phase-6--jwt-authentication) | Completed | Login, JWT generation, validation and expiry handling. |
| [Phase 7 — Role-Based Authorization](../README.md#phase-7--role-based-authorization) | Completed | USER/ADMIN endpoint authorization. |
| [Phase 7A — User Administration Hardening](../README.md#phase-7a--user-administration-hardening) | Completed | Registration role hardening, ownership checks, role separation and last-ADMIN protection. |
| [Phase 8 — Security Postman Regression](../README.md#phase-8--security-postman-regression) | Completed | USER/ADMIN security regression coverage. |
| [Phase 9 — Eureka Service Discovery](../README.md#phase-9--eureka-service-discovery) | Completed | Service registration in Eureka. |
| [Phase 11A — API Gateway](../README.md#phase-11a--api-gateway) | Completed | Gateway auth and user-route validation. |
| [Phase 11B — Kafka / Event-Driven Architecture](../README.md#phase-11b--kafka--event-driven-architecture) | Pending | Future asynchronous integration with downstream business flows. |
| [Phase 13 — Data and Transaction Hardening](../README.md#phase-13--data-and-transaction-hardening) | Pending | Cross-cutting data integrity and query hardening as applicable. |
| [Phase 14 — Testing and Quality Engineering](../README.md#phase-14--testing-and-quality-engineering) | Pending | Automated unit, controller, repository, integration and security testing. |
| [Phase 15–17](../README.md#phase-15--docker) | Pending | Containerization, Kubernetes and CI/CD. |
| [Phase 18A–18C](../README.md#phase-18--observability) | Pending | Foundation complete; expansion remains. |
| [Phase 19 — AWS Cloud Deployment](../README.md#phase-19--aws-cloud-deployment) | Pending | IAM/security, networking, EKS, managed data, observability and CI/CD. |

## Roadmap status

See the [master phase plan](../README.md#planned-phases), [completed phases](../README.md#completed-phases), and [Pending Task Matrix](../README.md#pending-task-matrix). The phase-participation table above is the service-level view.

Project architecture reference: [FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md](../FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md).
