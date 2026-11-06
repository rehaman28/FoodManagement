# Restaurant Management Service

## Description

Restaurant Management owns restaurants, restaurant addresses, food items, ratings, and restaurant/item retrieval used by other services.

Application / service ID:

    restaurantmanagement

Repository directory:

    restaurantmanagement

Port:

    8001

Base URL:

    http://localhost:8001

Swagger UI:

    http://localhost:8001/swagger-ui/index.html

OpenAPI specification:

    http://localhost:8001/v3/api-docs

## Responsibilities

- Create restaurants
- Retrieve restaurants
- Update restaurants
- Delete restaurants
- Update restaurant ratings
- Retrieve restaurant names
- Create restaurant items
- Bulk create items
- Retrieve items
- Update items
- Delete items

## Restaurant APIs

| Method | Endpoint | Purpose |
|---|---|---|
| POST | \`/restaurants\` | Create restaurant |
| GET | \`/restaurants/{restaurantId}\` | Get restaurant |
| GET | \`/restaurants/{restaurantId}/name\` | Get restaurant name |
| GET | \`/restaurants\` | Get all restaurants |
| PATCH | \`/restaurants/{restaurantId}\` | Update restaurant |
| DELETE | \`/restaurants/{restaurantId}\` | Delete restaurant |
| PUT | \`/restaurants/{restaurantId}/rating\` | Update rating |

## Item APIs

| Method | Endpoint | Purpose |
|---|---|---|
| GET | \`/restaurants/{restaurantId}/items\` | Get restaurant items |
| GET | \`/restaurants/{restaurantId}/items/{itemId}\` | Get one item |
| POST | \`/restaurants/{restaurantId}/items\` | Add item |
| POST | \`/restaurants/{restaurantId}/items/bulk\` | Add multiple items |
| PATCH | \`/restaurants/{restaurantId}/items/{itemId}\` | Update item |
| DELETE | \`/restaurants/{restaurantId}/items/{itemId}\` | Delete item |

## Authentication

Restaurant Management is secured by JWT.

Generate a USER JWT through User Management:

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

Generate an ADMIN JWT the same way with an existing ADMIN account.

Use:

    Authorization: Bearer <JWT>

Typical access model:

- USER: restaurant read operations
- ADMIN: restaurant/item create, update, and delete operations

## Create restaurant

Request:

    POST http://localhost:8001/restaurants

Header:

    Authorization: Bearer <ADMIN_JWT>

Body:

    {
      "restaurantName": "Arugu",
      "phoneNumber": "9123456789",
      "rating": 4.2,
      "addressRequestDto": {
        "landmark": "InOrbit Mall",
        "city": "Hyderabad",
        "state": "Telangana",
        "pincode": "500081"
      },
      "itemRequestDto": [
        {
          "itemName": "Paneer Bowl",
          "itemPrice": 180,
          "itemCategory": "Main Course",
          "itemType": "Veg",
          "itemRating": 4
        }
      ]
    }

Expected:

    201 Created

Example response:

    {
      "Id": 1,
      "Name": "Arugu",
      "Rating": 4.2
    }

## Get restaurant

Request:

    GET http://localhost:8001/restaurants/{restaurantId}

Header:

    Authorization: Bearer <USER_JWT>

Expected:

    200 OK

The response contains restaurant information and associated data exposed by the service.

## Get restaurant name

Request:

    GET http://localhost:8001/restaurants/{restaurantId}/name

Header:

    Authorization: Bearer <USER_JWT>

Example response:

    "Arugu"

This endpoint is also used by Order Management for restaurant lookups.

## Get all restaurants

Request:

    GET http://localhost:8001/restaurants

Header:

    Authorization: Bearer <USER_JWT>

Example response:

    [
      {
        "Id": 1,
        "Name": "Arugu",
        "Rating": 4.2
      }
    ]

## Add item

Request:

    POST http://localhost:8001/restaurants/{restaurantId}/items

Header:

    Authorization: Bearer <ADMIN_JWT>

Body:

    {
      "itemName": "Chicken Bowl",
      "itemPrice": 270,
      "itemCategory": "Main Course",
      "itemType": "Non-Veg",
      "itemRating": 4.5
    }

Expected:

    201 Created

Example response:

    {
      "itemId": 2,
      "itemName": "Chicken Bowl",
      "itemPrice": 270.0,
      "itemCategory": "Main Course",
      "itemType": "Non-Veg",
      "itemRating": 4.5
    }

## Basic testing flow

    Generate USER or ADMIN JWT from User Management
        |
        v
    Set Authorization: Bearer <JWT>
        |
        v
    Call Restaurant APIs
        |
        v
    Read HTTP status + JSON response

## Postman collection

Use:

    /postman/FoodManagement-RestaurantManagement.postman_collection.json

Environment:

    FoodManagement-USER
    FoodManagement-ADMIN

## Inter-service responsibility

Order Management consumes these Restaurant APIs through the Eureka/OpenFeign service-discovery path:

    GET /restaurants/{restaurantId}/name
    GET /restaurants/{restaurantId}/items/{itemId}

The current Eureka service ID is:

    restaurantmanagement

Order Management resolves this service by name rather than using an active hard-coded `:8001` URL.


## Phase 10 status

Status: Complete — implementation, runtime validation, and regression validation successful

Order Management successfully communicates with this service through OpenFeign and Eureka service discovery. JWT propagation and Restaurant/item lookup error handling were validated successfully, including multi-instance discovery/failover with the Order flow continuing after one Restaurant instance was stopped.


## Phase 11 validation status

Status: Complete — Restaurant synchronous and API Gateway validation successful

Validated successfully:

- Order Management -> Restaurant Management OpenFeign communication — passed
- Eureka-based `restaurantmanagement` service resolution — passed
- JWT propagation to Restaurant Management — passed
- Restaurant-name and item lookup flow — passed
- Restaurant/item 404 regression — passed
- API Gateway `/restaurants/**` routing — passed
- Gateway protected Restaurant endpoint without JWT -> 401 — passed
- Gateway invalid JWT handling -> 401 — passed
- Gateway USER / ADMIN downstream RBAC preservation — passed
- Existing direct-service Postman collection remains intact for troubleshooting.


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

Management port: 9101

    http://localhost:9101/actuator/health
    http://localhost:9101/actuator/prometheus

Both were validated locally with HTTP 200.

Prometheus target:

    host.docker.internal:9101

HTTP request histogram metrics are enabled for P95/P99 latency calculations. Actuator is exposed on the dedicated management port while restaurant business APIs retain JWT / USER / ADMIN protection.

The current Docker stack runs Prometheus and Grafana only. Full service containerization is deferred to the later Docker phase.
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

