# Order Management Service

## Description

Order Management creates and manages food orders. It calculates order prices from Restaurant Management data and exposes order lifecycle operations.

Application / service ID:

    ordermanagement

Port:

    8003

Base URL:

    http://localhost:8003

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

## Restaurant dependency

The current implementation performs synchronous Restaurant Management calls for:

    GET /restaurants/{restaurantId}/name

    GET /restaurants/{restaurantId}/items/{itemId}

These calls are currently implemented through RestTemplate.

Phase 10 changes this to:

    Order Management
        |
        v
    OpenFeign
        |
        v
    Eureka
        |
        v
    Restaurant Management

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
