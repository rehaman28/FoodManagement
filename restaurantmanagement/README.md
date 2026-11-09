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

## API testing with sample data

Canonical endpoint:

    http://localhost:8080

Postman uses testBaseUrl for all requests. Direct Restaurant Management port 8001 remains available for troubleshooting.

### Create restaurant

    POST {{testBaseUrl}}/restaurants

Request:

    {
      "restaurantName": "Hyderabad Food Hub",
      "phoneNumber": "9123456789",
      "rating": 4.2,
      "addressRequestDto": {
        "landmark": "Near Metro",
        "city": "Hyderabad",
        "state": "Telangana",
        "pincode": "500081"
      },
      "itemRequestDto": [
        {
          "itemName": "Paneer Bowl",
          "itemPrice": 180,
          "itemCategory": "Main",
          "itemType": "Veg",
          "itemRating": 4
        }
      ]
    }

Response:

    {
      "Id": 201,
      "Name": "Hyderabad Food Hub",
      "Rating": 4.2
    }

### Add item

    POST {{testBaseUrl}}/restaurants/{{restaurantId}}/items

Request:

    {
      "itemName": "Paneer Wrap",
      "itemPrice": 120,
      "itemCategory": "Snack",
      "itemType": "Veg",
      "itemRating": 4.3
    }

Response:

    {
      "itemId": 301,
      "itemName": "Paneer Wrap",
      "itemPrice": 120.0,
      "itemCategory": "Snack",
      "itemType": "Veg",
      "itemRating": 4.3
    }


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


## Current roadmap status

Project-level roadmap and architecture:

    /FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md

Current execution gate:

1. Phase 11 — API Gateway + synchronous communication: Complete and validated
2. Phase 18A — Observability Foundation: Complete and validated
3. Phase 12 — Fault Tolerance: Next, beginning with Timeouts
4. Failure-Path Testing: after resilience controls
5. Phase 18C — Observability Expansion: after failure-path testing
6. Phase 11B — Kafka / Event-Driven Architecture: deferred until the resilience and observability gates pass

Later platform phases:

    Full application Dockerization
        -> Kubernetes
        -> CI/CD
        -> AWS / Cloud Deployment
        -> Production Hardening

This ordering keeps the project measurable before resilience tuning and prevents Kafka/event-driven complexity from being introduced before failure behavior is observable and controlled.

