# FoodManagement

FoodManagement is a Java 21 / Spring Boot microservices project for managing users, restaurants, food items, orders, and delivery operations. The project is built incrementally to demonstrate authentication, authorization, service discovery, synchronous service communication, event-driven communication, resilience, testing, containerization, CI/CD, observability, and cloud deployment.

## Project architecture

The current system contains:

- User Management
- Restaurant Management
- Order Management
- Delivery Management
- Eureka Service Discovery
- JWT authentication
- USER / ADMIN role-based authorization
- Postman API regression collections

Current local ports:

| Component | Application / Service ID | Port |
|---|---|---:|
| Eureka Server | \`eureka\` | 8761 |
| User Management | \`usermanagement\` | 8002 |
| Restaurant Management | \`restaurantmangement\` | 8001 |
| Order Management | \`ordermanagement\` | 8003 |
| Delivery Management | \`deliverymanagement\` | 8004 |

> The repository directory is \`restaurantmanagement\`, while the existing Spring application/service ID is \`restaurantmangement\`. The service ID is intentionally kept unchanged because it is used for discovery.

## Technology stack

- Java 21
- Spring Boot 4.1.x
- Spring Data JPA / Hibernate
- Spring Security
- JWT
- MySQL
- Gradle
- Spring Cloud Netflix Eureka
- OpenFeign / Spring Cloud LoadBalancer
- Apache Kafka
- JUnit 5 / Mockito
- Postman
- Docker
- Kubernetes
- CI/CD tooling
- Observability tooling

## Project roadmap

### Phase 1 — User Management
Status: Complete

Implementation:
- User registration
- User retrieval
- User update
- User deletion
- Validation
- MySQL persistence
- Password encoding
- User role model

### Phase 2 — Restaurant Management
Status: Complete

Implementation:
- Restaurant CRUD
- Restaurant items
- Bulk item creation
- Restaurant rating
- Validation
- Exception handling

### Phase 3 — Order Management
Status: Core complete

Implementation:
- Place order
- Order retrieval
- Order listing
- Orders by user
- Orders by restaurant
- Orders by status
- Order status transitions
- Restaurant/item lookup
- Order price calculation

Future hardening:
- BigDecimal for monetary values
- Price snapshot
- Ownership validation
- Improved service-to-service abstraction

### Phase 4 — Delivery Management
Status: Core complete

Implementation:
- Delivery person management
- Delivery assignments
- Assignment retrieval
- Assignment listing
- Delivery status transitions
- Availability handling

### Phase 5 — API Test Baseline
Status: Complete

Implementation:
- Individual Postman collections
- Overall Postman collection
- Happy-path scenarios
- Negative scenarios
- HTTP status assertions
- Response assertions

### Phase 6 — JWT Authentication
Status: Complete

Implementation:
- Login
- JWT generation
- JWT signature validation
- JWT expiration
- JWT role claim
- Bearer-token authentication
- Invalid JWT handling

### Phase 7 — Role-Based Authorization
Status: Complete

Implementation:
- USER role
- ADMIN role
- Endpoint-level authorization
- ADMIN-only management operations
- USER-accessible operations

### Phase 8 — Security Postman Regression
Status: Complete

Implementation:
- Login collection
- USER environment
- ADMIN environment
- JWT token variable
- 401 scenarios
- 403 scenarios
- Existing collections synchronized with security scenarios

### Phase 9 — Eureka Service Discovery
Status: Complete

Implementation:
- Eureka Server
- Eureka client registration
- Service names
- Service discovery configuration
- Service registration validation

### Phase 10 — OpenFeign + Eureka
Status: Complete — implementation, runtime validation, and regression validation successful

Implemented:
1. OpenFeign and LoadBalancer dependencies in Order Management
2. Feign client enablement
3. Restaurant Feign client for restaurant-name and item lookup
4. Order -> Restaurant communication migrated from active RestTemplate calls to Feign
5. JWT Authorization propagation through Feign
6. Downstream Feign 404 mapping to existing business exceptions
7. Active hard-coded Restaurant URL removed from the Order service flow
8. Restaurant service ID aligned with the current Eureka registration name

Validation completed successfully:
1. Order -> Restaurant happy-path Postman scenario — passed
2. USER and ADMIN JWT regression through the Feign path — passed
3. Restaurant/item 404 regression — passed
4. Eureka discovery with multiple Restaurant instances — passed
5. Order continued working after stopping one Restaurant instance — passed

Cleanup decision:
- Phase 10 validation is complete; the old `Config.java` RestTemplate bean is no longer invoked.
- Legacy RestTemplate imports/comments kept in `OrderServiceImpl` are not part of the remaining Phase 10 work.
- Cleanup/removal of these unused artifacts is explicitly deferred and does not block Phase 10 completion.

### Phase 11 — Kafka / Event-Driven Architecture
Status: Synchronous communication baseline complete — implementation and validation successful

Completed synchronous communication:
1. Order Management -> Restaurant Management through OpenFeign + Eureka — complete and validated in Phase 10
2. Order Management -> User Management through OpenFeign + Eureka — implemented in the latest commit and validated successfully
3. Authorization header/JWT propagation from Order Management to User Management — validated successfully
4. Existing User Management `GET /users/{userId}` reused for user existence validation
5. Non-existent user mapped to Order Management `UserNotFoundException` and HTTP 404
6. Order persistence occurs only after successful user and restaurant validation

Current communication strategy:
- Synchronous: Order -> User
- Synchronous: Order -> Restaurant
- Asynchronous Kafka communication will be introduced next between Order and Delivery services.

Plan:
1. Introduce Kafka infrastructure
2. Define domain events
3. Implement producers
4. Implement consumers
5. Define topics and consumer groups
6. Add retry/error handling
7. Add idempotency
8. Add integration tests

Candidate events:
- OrderPlaced
- OrderAccepted
- OrderPreparing
- OrderOutForDelivery
- OrderDelivered
- OrderCancelled

### Phase 12 — Resilience and Fault Tolerance
Plan:
1. Timeouts
2. Retry policies
3. Circuit breakers
4. Fallbacks
5. Bulkhead isolation
6. Rate limiting
7. Failure-path testing

### Phase 13 — Data and Transaction Hardening
Plan:
1. BigDecimal for money
2. Price snapshots
3. Transaction boundaries
4. Database indexes and constraints
5. Pagination and sorting
6. Audit fields
7. Ownership checks
8. Concurrency handling

### Phase 14 — Testing and Quality Engineering
Plan:
1. Unit tests
2. Controller tests
3. Repository tests
4. Integration tests
5. Security tests
6. Service-to-service tests
7. Kafka tests
8. Testcontainers
9. WireMock / contract-style testing
10. Mutation testing

### Phase 15 — Docker
Plan:
1. Dockerfiles
2. Containerize each service
3. Containerize Eureka
4. Compose local infrastructure
5. Configure service networking
6. Externalize environment configuration

### Phase 16 — Kubernetes
Plan:
1. Deployments
2. Services
3. ConfigMaps
4. Secrets
5. Ingress
6. Health probes
7. Autoscaling
8. Namespaces

### Phase 17 — CI/CD
Plan:
1. Build
2. Unit testing
3. Integration testing
4. Security checks
5. Container build
6. Image publication
7. Deployment
8. Release automation

### Phase 18 — Observability
Plan:
1. Spring Boot Actuator
2. Metrics
3. Centralized logging
4. Correlation IDs
5. Distributed tracing
6. Dashboards
7. Alerts

### Phase 19 — AWS / Cloud Deployment
Plan:
1. Cloud infrastructure
2. Managed database
3. Container platform
4. Container registry
5. Kafka deployment
6. Secrets management
7. Load balancing
8. Monitoring

### Phase 20 — Production Hardening
Plan:
1. Externalize secrets
2. Environment-specific configuration
3. HTTPS
4. Security headers
5. API versioning
6. Caching
7. Database migration strategy
8. Performance/load testing
9. Dependency/security scanning
10. Backup and recovery

## Basic API testing

The project uses Postman for basic request/response testing.

### Start the system

Start services in this order:

    MySQL
    Eureka Server :8761
    User Management :8002
    Restaurant Management :8001
    Order Management :8003
    Delivery Management :8004

### Swagger documentation

Each API service exposes a public Swagger UI and OpenAPI specification:

| Service | Swagger UI | OpenAPI specification |
|---|---|---|
| User Management | http://localhost:8002/swagger-ui/index.html | http://localhost:8002/v3/api-docs |
| Restaurant Management | http://localhost:8001/swagger-ui/index.html | http://localhost:8001/v3/api-docs |
| Order Management | http://localhost:8003/swagger-ui/index.html | http://localhost:8003/v3/api-docs |
| Delivery Management | http://localhost:8004/swagger-ui/index.html | http://localhost:8004/v3/api-docs |

### Generate a USER JWT

Send:

    POST http://localhost:8002/auth/login

Body:

    {
      "email": "<valid-user-email>",
      "password": "<valid-user-password>"
    }

Expected response:

    {
      "token": "<JWT>"
    }

Use the returned token for secured requests:

    Authorization: Bearer <JWT>

### Generate an ADMIN JWT

Use the same login endpoint with an existing ADMIN account:

    POST http://localhost:8002/auth/login

Body:

    {
      "email": "<valid-admin-email>",
      "password": "<valid-admin-password>"
    }

Expected response:

    {
      "token": "<JWT>"
    }

Use that token for ADMIN-protected requests:

    Authorization: Bearer <JWT>

The project does not expose a public ADMIN-registration endpoint, so an ADMIN account must already exist in the local database.

### Basic response-generation examples

User:

    POST /users
    -> 201 Created
    -> UserResponseDto

Restaurant:

    POST /restaurants
    -> 201 Created
    -> RestaurantResponseDto

Order:

    POST /orders/placeorder
    -> 201 Created
    -> OrderResponseDto

Delivery person:

    POST /delivery-person/addperson
    -> 2xx response
    -> DeliveryPersonResponseDto

Delivery assignment:

    POST /delivery-assignments
    -> 201 Created
    -> DeliveryAssignmentResponseDto

For the exact request payloads, endpoint lists, and service-specific response examples, refer to each microservice README.

## Postman collections

Canonical Postman assets are stored under:

    /postman

Collections:

    FoodManagement-Login.postman_collection.json
    FoodManagement-UserManagement.postman_collection.json
    FoodManagement-RestaurantManagement.postman_collection.json
    FoodManagement-OrderManagement.postman_collection.json
    FoodManagement-DeliveryManagement.postman_collection.json
    FoodManagement-Overall.postman_collection.json

Environments:

    FoodManagement-USER.postman_environment.json
    FoodManagement-ADMIN.postman_environment.json
    FoodManagement-environment.postman_environment.json

Basic Postman sequence:

    Select USER or ADMIN environment
    -> Run Login - Current Environment
    -> Copy/use generated TOKEN automatically
    -> Run the required service collection
    -> Review response status and response body

## Project documentation

Each service directory contains its own README describing:

- Service responsibility
- API endpoints
- Request examples
- Response examples
- JWT usage
- Basic Postman flow
- Role expectations
- Implementation scope
