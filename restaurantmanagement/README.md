# Restaurant Management Service

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


## Phase participation

The master [planned phase roadmap](../README.md#planned-phases) is the authoritative project scope. This table shows how this service participates in the planned work.

| Phase | Planned status | Sub-phases / implementation steps |
|---|---|---|
| [Phase 2 — Restaurant Management](../README.md#phase-2--restaurant-management) | Completed | Restaurant/item CRUD, ratings, validation and persistence. |
| [Phase 5 — API Test Baseline](../README.md#phase-5--api-test-baseline) | Completed | Functional and negative-path Postman baseline. |
| [Phase 6 — JWT Authentication](../README.md#phase-6--jwt-authentication) | Completed | Protected Restaurant APIs with JWT. |
| [Phase 7 — Role-Based Authorization](../README.md#phase-7--role-based-authorization) | Completed | USER read and ADMIN management boundaries. |
| [Phase 8 — Security Postman Regression](../README.md#phase-8--security-postman-regression) | Completed | Security regression coverage. |
| [Phase 9 — Eureka Service Discovery](../README.md#phase-9--eureka-service-discovery) | Completed | Service registration and discovery. |
| [Phase 10 — OpenFeign + Eureka](../README.md#phase-10--openfeign--eureka) | Completed | Order Management integration through Feign/Eureka. |
| [Phase 11A — API Gateway](../README.md#phase-11a--api-gateway) | Completed | Gateway Restaurant routing and security validation. |
| [Phase 13 — Data and Transaction Hardening](../README.md#phase-13--data-and-transaction-hardening) | Pending | Schema indexes/constraints, pagination, audit fields, ownership and concurrency hardening. |
| [Phase 14 — Testing and Quality Engineering](../README.md#phase-14--testing-and-quality-engineering) | Pending | Automated service and integration test expansion. |
| [Phase 15–17](../README.md#phase-15--docker) | Pending | Containerization, Kubernetes, CI/CD. |
| [Phase 18A–18C](../README.md#phase-18--observability) | Pending | Foundation is complete; resilience visibility and observability expansion remain. |
| [Phase 19 — AWS Cloud Deployment](../README.md#phase-19--aws-cloud-deployment) | Pending | EKS/ECR, managed data, edge delivery, observability, CI/CD and production validation. |

## Roadmap status

See the [master phase plan](../README.md#planned-phases), [completed phases](../README.md#completed-phases), and [Pending Task Matrix](../README.md#pending-task-matrix). The phase-participation table above is the service-level view.

Project architecture reference: [FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md](../FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md).
