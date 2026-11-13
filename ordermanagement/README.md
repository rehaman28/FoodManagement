# Order Management Service

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

Order Management creates and manages food orders. It calculates order prices from Restaurant Management data and exposes order lifecycle operations.

Application / service ID:

    ordermanagement

Port:

    8003

Base URL:

    http://localhost:8003

Swagger UI:

    http://localhost:8003/swagger-ui/index.html

OpenAPI specification:

    http://localhost:8003/v3/api-docs

## Responsibilities

- Place orders
- Retrieve orders
- Retrieve orders for a user
- Retrieve orders for a restaurant
- Retrieve orders by status
- Update order status
- Retrieve restaurant name from Restaurant Management
- Retrieve item prices from Restaurant Management
- Calculate order price

## API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | \`/orders/placeorder\` | Place order |
| GET | \`/orders/{orderId}\` | Get order |
| GET | \`/orders\` | Get all orders |
| GET | \`/orders/user/{userId}\` | Get orders by user |
| GET | \`/orders/restaurant/{restaurantId}\` | Get orders by restaurant |
| GET | \`/orders/status?orderStatus={status}\` | Get orders by status |
| PATCH | \`/orders/{orderId}/status\` | Update order status |

## Authentication

Generate the JWT from User Management:

    POST http://localhost:8002/auth/login

USER example:

    {
      "email": "<valid-user-email>",
      "password": "<valid-user-password>"
    }

ADMIN example:

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

Current role model:

- USER: place orders and access USER-allowed order APIs
- ADMIN: administrative order APIs

## Place order

Request:

    POST http://localhost:8003/orders/placeorder

Header:

    Authorization: Bearer <USER_JWT>

Body:

    {
      "userId": 1,
      "restaurantId": 1,
      "OrderingItems": [
        {
          "quantity": 2,
          "itemId": 1
        },
        {
          "quantity": 1,
          "itemId": 2
        }
      ]
    }

Expected:

    201 Created

Example response:

    {
      "orderId": 101,
      "restaurantName": "Arugu",
      "orderPrice": 630.0,
      "orderStatus": "Ordered",
      "orderItems": [
        ...
      ]
    }

The actual order item response content depends on the current data in Restaurant Management.

## Get order

Request:

    GET http://localhost:8003/orders/{orderId}

Header:

    Authorization: Bearer <USER_JWT or ADMIN_JWT>

Expected:

    200 OK

Example response shape:

    {
      "orderId": 101,
      "restaurantName": "Arugu",
      "orderPrice": 630.0,
      "orderStatus": "Ordered",
      "orderItems": [
        ...
      ]
    }

## Get all orders

Request:

    GET http://localhost:8003/orders

Header:

    Authorization: Bearer <ADMIN_JWT>

Expected:

    200 OK

## Get orders by user

Request:

    GET http://localhost:8003/orders/user/{userId}

Header:

    Authorization: Bearer <USER_JWT or ADMIN_JWT>

Expected:

    200 OK

## Get orders by restaurant

Request:

    GET http://localhost:8003/orders/restaurant/{restaurantId}

Header:

    Authorization: Bearer <ADMIN_JWT>

Expected:

    200 OK

## Get orders by status

Request:

    GET http://localhost:8003/orders/status?orderStatus=Ordered

Header:

    Authorization: Bearer <ADMIN_JWT>

Expected:

    200 OK

## Update order status

Request:

    PATCH http://localhost:8003/orders/{orderId}/status

Header:

    Authorization: Bearer <ADMIN_JWT>

Body:

    {
      "OrderStatus": "Accepted"
    }

Expected:

    200 OK

Example response shape:

    {
      "orderId": 101,
      "restaurantName": "Arugu",
      "orderPrice": 630.0,
      "orderStatus": "Accepted",
      "orderItems": [
        ...
      ]
    }

## User dependency

Order Management synchronously validates the user before creating an order by calling User Management through OpenFeign and Eureka service discovery.

    Order Management
        |
        | OpenFeign
        v
    Eureka
        |
        v
    usermanagement

Feign operation:

    GET /users/{userId}

The incoming Authorization header is propagated to User Management through the existing Feign authorization interceptor.

Validation behavior:

- Existing user — validation passes and order processing continues.
- Non-existent user — Order Management returns HTTP 404 with `UserNotFoundException`.
- User validation occurs before restaurant/item validation and before order persistence.

## Restaurant dependency

Order Management uses OpenFeign to call Restaurant Management by its Eureka service name.

    Order Management
        |
        | OpenFeign
        v
    Eureka
        |
        v
    restaurantmanagement

Feign operations:

    GET /restaurants/{restaurantId}/name
    GET /restaurants/{restaurantId}/items/{itemId}

The incoming Authorization header is propagated to Restaurant Management so the downstream JWT/RBAC security model remains active.

The old RestTemplate configuration is no longer invoked. Its cleanup is intentionally deferred.

## Basic testing flow

    Generate USER or ADMIN JWT
        |
        v
    Set Authorization header
        |
        v
    Create / retrieve order
        |
        v
    Read order response and status

For a valid order, the restaurant and item data must already exist in Restaurant Management.

## Postman collection

Use:

    /postman/FoodManagement-OrderManagement.postman_collection.json

Recommended environment:

    FoodManagement-USER
    FoodManagement-ADMIN

For a complete end-to-end sequence, use:

    /postman/FoodManagement-Overall.postman_collection.json

## API testing with sample data

Canonical endpoint:

    http://localhost:8080

Postman uses testBaseUrl for all requests. Direct Order Management port 8003 remains available for troubleshooting.

### Place order

    POST {{testBaseUrl}}/orders/placeorder

Request:

    {
      "userId": 101,
      "restaurantId": "{{restaurantId}}",
      "OrderingItems": [
        {
          "quantity": 2,
          "itemId": "{{itemId}}"
        }
      ]
    }

Response:

    {
      "orderId": 401,
      "restaurantName": "Hyderabad Food Hub",
      "orderPrice": 240.0,
      "orderStatus": "Ordered",
      "orderItems": [
        {
          "orderItemId": 501,
          "quantity": 2,
          "itemId": 301
        }
      ]
    }

### Update order status

    PATCH {{testBaseUrl}}/orders/{{orderId}}/status

Request:

    {
      "OrderStatus": "Accepted"
    }

Response:

    {
      "orderId": 401,
      "restaurantName": "Hyderabad Food Hub",
      "orderPrice": 240.0,
      "orderStatus": "Accepted",
      "orderItems": [
        {
          "orderItemId": 501,
          "quantity": 2,
          "itemId": 301
        }
      ]
    }


### Downstream failure-path validation

The Order Management Postman regression includes two controlled downstream failure cases.

#### Restaurant Service Unavailable — 503

    POST {{testBaseUrl}}/orders/placeorder

Validation setup:

- Keep Eureka, User Management, and Order Management running.
- Stop Restaurant Management so no `restaurantmanagement` instance is available through service discovery.
- Execute `Place Order - Restaurant Service Unavailable [503]`.

Expected result:

    HTTP 503 Service Unavailable

#### Restaurant Service Timeout — 504

    POST {{testBaseUrl}}/orders/placeorder

Validation setup:

- Keep Eureka, User Management, Order Management, and Restaurant Management available.
- Use the controlled timeout validation setup that causes the Restaurant Management request to exceed the configured Feign timeout.
- Execute `Place Order - Restaurant Service Timeout [504]`.

Expected result:

    HTTP 504 Gateway Timeout

Validation status:

- Restaurant Service Unavailable [503] -> PASS
- Restaurant Service Timeout [504] -> PASS

These are the only downstream failure-path cases added in this update. Retry, circuit-breaker, fallback, bulkhead, and rate-limiting behavior remain separate Phase 12 work.

### Retry policy validation

Retry policy is implemented at the Spring Cloud LoadBalancer layer for the `restaurantmanagement` and `usermanagement` Feign clients.

Current retry configuration:

- Retry enabled per downstream LoadBalancer client
- One retry allowed on the same service instance
- No retry on a next service instance in the current local configuration
- Retry is not enabled for all exceptions
- Retry is not enabled for all HTTP operations
- Existing 503 service-unavailable mapping is preserved
- Existing 504 timeout mapping is preserved

Validation status:

- Retry policy -> PASS
- Restaurant Service Unavailable [503] -> PASS
- Restaurant Service Timeout [504] -> PASS
- Permanent not-found behavior -> PASS

The next Phase 12 resilience control is Circuit Breaker.

## Phase 11 validation status

Status: Complete — synchronous communication and API Gateway edge validation successful

Validated:
- Order -> User Eureka/OpenFeign service resolution
- Order -> Restaurant Eureka/OpenFeign service resolution
- OpenFeign UserClient and Restaurant client flows
- JWT/Authorization propagation
- Existing-user order flow
- Non-existent-user 404 handling
- User validation before order persistence
- Restaurant/item lookup through Eureka/OpenFeign
- API Gateway `/orders/**` routing
- Gateway protected Order endpoint without JWT -> 401
- Gateway invalid JWT handling -> 401
- Gateway USER / ADMIN downstream RBAC preservation
- End-to-end order flow through the validated synchronous architecture

## Phase 10 status

Status: Complete — implementation, runtime validation, and regression validation successful

Completed implementation and validation:

- OpenFeign
- Spring Cloud LoadBalancer
- Eureka-based Restaurant service resolution
- JWT propagation through Feign
- Feign 404 mapping to existing application exceptions
- Removal of active hard-coded Restaurant URLs from the Order flow
- USER place-order flow through Feign — passed
- ADMIN regression flow — passed
- Restaurant/item 404 scenarios — passed
- Eureka multi-instance discovery/failover — passed
- Order continued working after one Restaurant instance was stopped — passed

Unused legacy RestTemplate code/comments are intentionally retained. Cleanup is deferred and does not block Phase 10 completion.


## Phase participation

The master [planned phase roadmap](../README.md#planned-phases) is the authoritative project scope. This table shows how this service participates in the planned work.

| Phase | Planned status | Sub-phases / implementation steps |
|---|---|---|
| [Phase 3 — Order Management](../README.md#phase-3--order-management) | Pending | Core order creation/retrieval/status flows are complete; data hardening continues in Phase 13. |
| [Phase 5 — API Test Baseline](../README.md#phase-5--api-test-baseline) | Completed | Functional and negative-path Postman baseline. |
| [Phase 6 — JWT Authentication](../README.md#phase-6--jwt-authentication) | Completed | Protected order APIs with JWT. |
| [Phase 7 — Role-Based Authorization](../README.md#phase-7--role-based-authorization) | Completed | USER/ADMIN endpoint access controls. |
| [Phase 8 — Security Postman Regression](../README.md#phase-8--security-postman-regression) | Completed | Security regression coverage. |
| [Phase 10 — OpenFeign + Eureka](../README.md#phase-10--openfeign--eureka) | Completed | Feign-based Restaurant/User communication with Eureka and JWT propagation. |
| [Phase 11A — API Gateway](../README.md#phase-11a--api-gateway) | Completed | Gateway routing and edge authentication validated for Order APIs. |
| [Phase 11B — Kafka / Event-Driven Architecture](../README.md#phase-11b--kafka--event-driven-architecture) | Pending | Asynchronous Order events for downstream delivery processing. |
| [Phase 12 — Resilience and Fault Tolerance](../README.md#phase-12--resilience-and-fault-tolerance) | Pending | Core resilience is implemented; deferred bulkhead, rate limiting, correlation, tracing and alerts remain. |
| [Phase 13 — Data and Transaction Hardening](../README.md#phase-13--data-and-transaction-hardening) | Pending | Database indexes/constraints, pagination/sorting, audit fields, ownership checks and concurrency handling. |
| [Phase 14 — Testing and Quality Engineering](../README.md#phase-14--testing-and-quality-engineering) | Pending | Full automated testing and contract/mutation coverage. |
| [Phase 15–17](../README.md#phase-15--docker) | Pending | Containerization, Kubernetes deployment, and CI/CD. |
| [Phase 18A–18C](../README.md#phase-18--observability) | Pending | Foundation is complete; resilience visibility and observability expansion remain. |
| [Phase 19 — AWS Cloud Deployment](../README.md#phase-19--aws-cloud-deployment) | Pending | EKS/RDS/ECR, messaging, edge delivery, observability, CI/CD and final production deployment. |

## Roadmap status

See the [master phase plan](../README.md#planned-phases), [completed phases](../README.md#completed-phases), and [Pending Task Matrix](../README.md#pending-task-matrix). The phase-participation table above is the service-level view.

Project architecture reference: [FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md](../FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md).
