# FoodManagement

> **Project wiki:** a single, production-readable guide to the FoodManagement microservices architecture, implementation phases, validation, testing, operational foundations, and planned AWS deployment.

## Description

FoodManagement is a Java 21 / Spring Boot microservices application for users, restaurants, food items, orders, and delivery operations. The project is implemented incrementally so that each architecture capability is introduced, validated, documented, and then used as a foundation for the next phase.

The master project wiki is this README. Service-specific implementation and API details remain in each service's README.

## Wiki navigation

| Section | Link |
|---|---|
| Description | [Project description](#description) |
| Planned phases | [Phase plan](#planned-phases) |
| Completed phases | [Completed phases](#completed-phases) |
| Pending task matrix | [Pending Task Matrix](#pending-task-matrix) |
| Implementation details | [Phase implementation details](#phase-implementation-details) |
| API testing | [API testing documentation](#api-testing-with-sample-data) |
| Architecture | [Architecture roadmap](FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md) |
| Service documentation | [API Gateway](apigateway/README.md) · [Delivery](deliverymanagement/README.md) · [Eureka](eureka/README.md) · [Observability](observability/README.md) · [Order](ordermanagement/README.md) · [Restaurant](restaurantmanagement/README.md) · [User](usermanagement/README.md) |

## Planned phases

> A phase is marked **Completed** only when its planned scope is complete and the documented validation/definition of done has been satisfied. A phase with remaining work stays **Pending** even when part of the phase is already implemented.

| Phase | Planned status | Sub-phases / implementation steps |
|---|---|---|
| Phase 0 — Project Foundation | Pending | Define an explicit Phase 0 scope only if the project needs one; the current repository has no formal Phase 0 definition. |
| Phase 1 — User Management | Completed | Registration, CRUD, validation, password encoding, role model, MySQL persistence. |
| Phase 2 — Restaurant Management | Completed | Restaurant CRUD, item management, bulk item creation, ratings, validation, exception handling. |
| Phase 3 — Order Management | Pending | Core order flow is implemented; remaining hardening is tracked in Phase 13. |
| Phase 4 — Delivery Management | Pending | Core delivery flow is implemented; event-driven integration and quality hardening remain. |
| Phase 5 — API Test Baseline | Completed | Postman collections, happy/negative paths, HTTP status assertions, response assertions. |
| Phase 6 — JWT Authentication | Completed | Login, JWT generation/validation, expiry, role claims, bearer authentication, invalid-token handling. |
| Phase 7 — Role-Based Authorization | Completed | USER/ADMIN access boundaries and endpoint-level authorization. |
| Phase 7A — User Administration Hardening | Completed | Safe registration, ownership checks, role-management separation, last-ADMIN protection. |
| Phase 8 — Security Postman Regression | Completed | USER/ADMIN environments, JWT variables, 401/403 scenarios, regression synchronization. |
| Phase 9 — Eureka Service Discovery | Completed | Eureka server, client registration, service IDs, discovery configuration and validation. |
| Phase 10 — OpenFeign + Eureka | Completed | Feign clients, load balancing, JWT propagation, 404 mapping, discovered service calls. |
| Phase 11 — Synchronous + Edge + Event-Driven | Pending | **11A** API Gateway completed; **11B** Kafka/event-driven architecture pending. |
| Phase 11A — API Gateway | Completed | Gateway routing, JWT edge validation, Eureka-backed lb:// routing, preserved paths, regression coverage. |
| Phase 11B — Kafka / Event-Driven Architecture | Pending | Kafka infrastructure, domain events, producers/consumers, topics/groups, retries, idempotency, tests. |
| Phase 12 — Resilience and Fault Tolerance | Pending | Core timeouts/retries/circuit breakers/fallbacks completed; bulkhead, rate limiting, correlation, tracing and alerts remain. |
| Phase 13 — Data and Transaction Hardening | Pending | **13.1** BigDecimal, **13.2** price snapshots, **13.3** transactions completed; **13.4–13.8** remain. |
| Phase 14 — Testing and Quality Engineering | Pending | Unit, controller, repository, integration, security, service-to-service, Kafka, Testcontainers, WireMock/contract, mutation testing. |
| Phase 15 — Docker | Pending | Dockerfiles, service images, Eureka/container infrastructure, Compose networking, externalized configuration. |
| Phase 16 — Kubernetes | Pending | Deployments, Services, ConfigMaps, Secrets, Ingress, probes, HPA, namespaces. |
| Phase 17 — CI/CD | Pending | Build, tests, security, image build/publication, deployment, release automation. |
| Phase 18 — Observability | Pending | **18A** foundation completed; **18B–18C** expansion remains. |
| Phase 18A — Observability Foundation | Completed | Actuator, health, Micrometer, Prometheus, Grafana baseline. |
| Phase 18B — Resilience Visibility | Pending | Broader resilience/dependency dashboards plus visibility for deferred resilience controls. |
| Phase 18C — Observability Expansion | Pending | Centralized logging, correlation IDs, tracing, dependency dashboards, alerts. |
| Phase 19 — AWS Cloud Deployment | Pending | **19A–19L** implement the AWS foundation, security, networking, EKS, data, integration, edge, observability, CI/CD, cost/reliability, and final deployment. |
| Phase 19A | Pending | Cloud foundations, account/environment structure, governance, regions/AZs, tagging, cost controls. |
| Phase 19B | Pending | IAM, least privilege, workload identity, Secrets Manager, KMS, CloudTrail, WAF, GuardDuty awareness. |
| Phase 19C | Pending | VPC, subnets, routing, IGW/NAT, security groups, network controls, Route 53, private connectivity, multi-AZ. |
| Phase 19D | Pending | Docker images, ECR, EKS, namespaces, workloads, probes, resources, HPA/PDB, rollout, load balancer controller. |
| Phase 19E | Pending | RDS MySQL, private connectivity, Multi-AZ, backups/PITR, migrations, DynamoDB fundamentals, S3. |
| Phase 19F | Pending | SQS, SNS, EventBridge, DLQ, idempotency, retry/backoff, event-driven failure handling, Kafka/MSK mapping. |
| Phase 19G | Pending | Spring Cloud Gateway integration, ALB, API Gateway comparison, Route 53, ACM/TLS, CloudFront awareness, WAF. |
| Phase 19H | Pending | CloudWatch, Container Insights, tracing concepts, structured logs, diagnostics, performance and load testing. |
| Phase 19I | Pending | Source workflow, build/test/security, Docker/ECR, Kubernetes deployment, promotion, rollback, IaC. |
| Phase 19J | Pending | Lambda, Lambda+SQS/EventBridge/API Gateway labs, AWS SDK for Java, CLI workflows, developer tooling. |
| Phase 19K | Pending | Cost allocation, Cost Explorer, Budgets, right-sizing, reliability/DR trade-offs, Well-Architected review. |
| Phase 19L | Pending | Final Route 53/ALB/EKS/API Gateway/RDS/ECR/secrets/security/observability/messaging/CI/CD production validation. |

## Completed phases

| Completed phase | Implementation outcome |
|---|---|
| [Phase 1 — User Management](#phase-1--user-management) | User lifecycle, validation, persistence, password handling, and roles. |
| [Phase 2 — Restaurant Management](#phase-2--restaurant-management) | Restaurant and item management with validation and exception handling. |
| [Phase 5 — API Test Baseline](#phase-5--api-test-baseline) | Baseline Postman functional and negative-path coverage. |
| [Phase 6 — JWT Authentication](#phase-6--jwt-authentication) | JWT login, validation, expiry, roles, and bearer authentication. |
| [Phase 7 — Role-Based Authorization](#phase-7--role-based-authorization) | USER/ADMIN endpoint access controls. |
| [Phase 7A — User Administration Hardening](#phase-7a--user-administration-hardening) | Ownership, role separation, registration hardening, and last-ADMIN protection. |
| [Phase 8 — Security Postman Regression](#phase-8--security-postman-regression) | Repeatable 401/403 security regression coverage. |
| [Phase 9 — Eureka Service Discovery](#phase-9--eureka-service-discovery) | Service registration and discovery. |
| [Phase 10 — OpenFeign + Eureka](#phase-10--openfeign--eureka) | Declarative service calls, discovery, JWT propagation, and error mapping. |
| [Phase 11A — API Gateway](#phase-11a--api-gateway) | Gateway routing, edge JWT validation, and Eureka load balancing. |
| [Phase 18A — Observability Foundation](#phase-18a--observability-foundation) | Actuator, Prometheus, Grafana, and baseline service metrics. |

## Pending Task Matrix

[**Jump to Pending Task Matrix**](#pending-task-matrix)

| Phase | Status | Pending work |
|---|---|---|
| Phase 0 | Pending | Define scope only if a formal Phase 0 is required. |
| Phase 3 | Pending | Remaining data hardening is tracked under Phase 13. |
| Phase 4 | Pending | Event-driven integration and broader quality hardening. |
| Phase 11B | Pending | Kafka infrastructure, events, producers/consumers, topics/groups, retries/error handling, idempotency, integration tests. |
| Phase 12 | Pending | Bulkhead isolation, rate limiting, correlation IDs, distributed tracing, alerts. |
| Phase 13 | Pending | Database indexes/constraints, pagination/sorting, audit fields, ownership checks, concurrency handling. |
| Phase 14 | Pending | Full automated quality track: unit, controller, repository, integration, security, service-to-service, Kafka, Testcontainers, WireMock/contract, mutation testing. |
| Phase 15 | Pending | Dockerfiles, images, Compose, networking, externalized configuration. |
| Phase 16 | Pending | Kubernetes deployments/services/configuration/secrets/ingress/probes/autoscaling/namespaces. |
| Phase 17 | Pending | CI/CD build, tests, security, image publication, deployment, release and rollback. |
| Phase 18B | Pending | Broader resilience/dependency dashboards and deferred-control visibility. |
| Phase 18C | Pending | Centralized logging, correlation IDs, distributed tracing, dashboards, alerts. |
| Phase 19A–19L | Pending | AWS foundation, IAM/security, VPC, ECR/EKS, RDS/S3, messaging, edge delivery, observability/performance, CI/CD/IaC, serverless labs, cost/reliability, final production deployment. |

## Phase implementation details
### Phase 1 — User Management

**Description:** User identity, registration, profile management, persistence, password handling, and role-aware access.
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

**Description:** Restaurant and food-item ownership, CRUD operations, ratings, validation, and persistence.
Status: Complete

Implementation:
- Restaurant CRUD
- Restaurant items
- Bulk item creation
- Restaurant rating
- Validation
- Exception handling

### Phase 3 — Order Management

**Description:** Order creation, retrieval, status transitions, price calculation, and service-to-service validation.
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

**Description:** Delivery-person lifecycle, delivery assignment, status transitions, and availability management.
Status: Core complete

Implementation:
- Delivery person management
- Delivery assignments
- Assignment retrieval
- Assignment listing
- Delivery status transitions
- Availability handling

### Phase 5 — API Test Baseline

**Description:** Postman-based happy-path and negative-path API validation across the business services.
Status: Complete

Implementation:
- Individual Postman collections
- Overall Postman collection
- Happy-path scenarios
- Negative scenarios
- HTTP status assertions
- Response assertions

### Phase 6 — JWT Authentication

**Description:** JWT login, token validation, expiry handling, role claims, and bearer-token authentication.
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

**Description:** Endpoint authorization for USER and ADMIN roles with explicit access boundaries.
Status: Complete

Implementation:
- USER role
- ADMIN role
- Endpoint-level authorization
- ADMIN-only management operations
- USER-accessible operations

### Phase 7A — User Administration Hardening

**Description:** Safe user administration, ownership enforcement, role-management separation, and last-ADMIN protection.
Status: Complete — implementation, direct-service validation, Gateway validation, and Postman regression passed

Security model:
- Public `POST /users` always creates `USER`
- Client-supplied role cannot create an `ADMIN`
- `PATCH /users/{userId}`: USER can update only their own profile; ADMIN can update any user
- `PATCH /users/{userId}/role`: ADMIN only
- At least one ADMIN must remain
- Role changes are separated from normal profile updates
- Ownership denials return HTTP 403

Validation assets:
- `postman/FoodManagement-UserManagement.postman_collection.json`
- `postman/FoodManagement-API-Gateway.postman_collection.json`
- `postman/FoodManagement-Overall.postman_collection.json`

Validation completed successfully:
- Public registration role-injection protection — passed
- USER own-profile update — passed
- USER update of another user -> 403 — passed
- ADMIN update of another user — passed
- USER role promotion attempt -> 403 — passed
- ADMIN promotion of USER — passed
- ADMIN demotion of another ADMIN — passed
- Last-ADMIN protection — passed
- Gateway user-administration regression — passed

### Phase 8 — Security Postman Regression

**Description:** Repeatable 401/403 security regression coverage using USER and ADMIN environments.
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

**Description:** Service registration and discovery so services resolve each other by logical service ID.
Status: Complete

Implementation:
- Eureka Server
- Eureka client registration
- Service names
- Service discovery configuration
- Service registration validation

### Phase 10 — OpenFeign + Eureka

**Description:** Declarative inter-service HTTP calls, service discovery, JWT propagation, and downstream error mapping.
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

### Phase 11 — Synchronous + Edge + Event-Driven Architecture

**Description:** Service-to-service communication strategy, API gateway entry point, and the planned asynchronous event model.
Status: Phase 11 synchronous + edge validation complete — implementation and validation successful; Kafka Phase 11B remains pending

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
- Edge: Client -> API Gateway -> Business Services
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

#### Phase 11A — API Gateway

**Description:** Single application edge for routing, JWT edge validation, Eureka-backed load balancing, and preserved downstream paths.
Status: Complete — Gateway implementation and Postman regression coverage committed; direct-service regression suites preserved

Implemented:
1. Dedicated apigateway Spring Boot module using Spring Cloud Gateway Server WebMVC
2. Eureka registration and discovery-backed lb:// routing
3. User routes: /auth/** and /users/** -> User Management
4. Restaurant route: /restaurants/** -> Restaurant Management
5. Order route: /orders/** -> Order Management
6. Delivery routes: /delivery-person/** and /delivery-assignments/** -> Delivery Management
7. Gateway JWT edge validation for protected routes
8. Public Gateway access for POST /auth/login, POST /users, and OPTIONS
9. Downstream JWT/RBAC remains authoritative for USER / ADMIN authorization
10. No StripPrefix behavior; business-service paths are preserved

Postman regression:
- Added postman/FoodManagement-API-Gateway.postman_collection.json
- Added gatewayBaseUrl=http://localhost:8080 to USER, ADMIN, and generic environments
- Added an additive 00A - API Gateway Regression section to FoodManagement-Overall.postman_collection.json
- Existing direct-service variables remain unchanged:
  - userServiceUrl=http://localhost:8002
  - restaurantServiceUrl=http://localhost:8001
  - orderServiceUrl=http://localhost:8003
  - deliveryServiceUrl=http://localhost:8004
- Existing direct-service collections were not rewritten and remain available for troubleshooting

Gateway regression scenarios:
1. Login through Gateway
2. Protected route without JWT -> 401
3. Protected route with invalid JWT -> 401
4. Restaurant routing through Gateway
5. USER / ADMIN RBAC preservation for User, Order, and Delivery routes
6. Unknown Gateway route -> 404

Validation completed successfully:
1. Order -> User synchronous communication — passed
2. Order -> Restaurant OpenFeign/Eureka communication — passed
3. JWT/Authorization propagation through synchronous calls — passed
4. User existence validation and 404 handling — passed
5. API Gateway Eureka-based routing — passed
6. Gateway protected-route missing JWT -> 401 — passed
7. Gateway invalid JWT -> 401 — passed
8. Gateway USER / ADMIN downstream RBAC preservation — passed
9. Gateway unknown-route handling -> 404 — passed
10. Existing direct-service Postman regression remained intact — passed

Architecture:
Client
  |
  v
API Gateway :8080
  |
  +--> Eureka :8761
  |
  +--> User Management :8002
  +--> Restaurant Management :8001
  +--> Order Management :8003
  +--> Delivery Management :8004

### Phase 11B — Kafka / Event-Driven Architecture

**Description:** Asynchronous domain events between Order and Delivery with retry/error handling and idempotency.
Status: Deferred until Observability Foundation + Fault Tolerance + Failure-Path Testing + Observability Expansion are complete

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

**Description:** Timeouts, retries, circuit breakers, fallbacks, and failure-path controls for downstream dependencies.
Status: Complete — core resilience controls implemented, runtime validated, and failure-path validation completed. Bulkhead isolation, rate limiting, correlation IDs, distributed tracing, and alerts are explicitly deferred as later hardening / observability work.

Purpose:
- Protect synchronous service calls from latency spikes and dependency failures
- Validate retries, circuit breakers, fallbacks, and existing 503/504 behavior before Kafka is introduced
- Keep the current resilience scope measurable and regression-tested

Completed implementation and validation:
1. Observability Foundation:
   - Spring Boot Actuator
   - Micrometer metrics
   - Prometheus-format metrics endpoint
   - Basic Grafana dashboard for request rate, latency, and error rate
2. Fault-tolerance controls:
   - Downstream timeouts
   - Downstream service-unavailable handling
   - Spring Cloud LoadBalancer retry policies for User Management and Restaurant Management
   - Circuit breakers for User Management and Restaurant Management
   - Fallbacks for User Management and Restaurant Management calls
3. Failure-path validation:
   - HTTP 503 downstream service-unavailable behavior — validated
   - HTTP 504 downstream timeout behavior — validated
   - Retry behavior — validated
   - Circuit Breaker CLOSED state — validated
   - Fallback invocation — validated
   - Circuit Breaker OPEN transition — validated
   - OPEN-state rejection without another downstream call — validated
   - OPEN -> HALF_OPEN -> CLOSED recovery — validated
   - HALF_OPEN failure returns to OPEN — validated
   - 404 business errors remain non-breaker failures — validated

Circuit Breaker configuration currently uses:
| Property | Purpose |
| --- | --- |
| `slidingWindowSize` | Number of calls in the sliding window used to calculate failure rate |
| `minimumNumberOfCalls` | Minimum number of calls required before the failure rate is evaluated |
| `failureRateThreshold` | Failure-rate percentage that causes the circuit breaker to open |
| `waitDurationInOpenState` | How long the circuit breaker remains OPEN before transitioning to HALF_OPEN |
| `permittedNumberOfCallsInHalfOpenState` | Number of test calls allowed while HALF_OPEN |
| `automaticTransitionFromOpenToHalfOpenEnabled` | Automatically transitions from OPEN to HALF_OPEN after the wait duration |
| `ignoreExceptions` | Business exceptions such as 404 that must not open the circuit |

### Deferred later work
The following items are intentionally moved out of the current Phase 12 core scope:
- Bulkhead isolation
- Rate limiting
- Correlation IDs if not introduced in a future logging slice
- Distributed tracing
- Alerts

These items remain part of the later resilience/observability roadmap and do not block the current Phase 12 completion.

### Definition of done for the current Phase 12 scope
- Timeout behavior implemented and validated
- Downstream service-unavailable behavior implemented and validated
- Retry policies implemented and validated
- Circuit breakers implemented and validated
- Fallbacks implemented and validated
- Failure-path validation completed for the above controls
- Existing 503/504 response contracts preserved

Kafka remains gated by the later observability and architecture milestones.
### Phase 13 — Data and Transaction Hardening

**Description:** Monetary precision, price snapshots, transaction boundaries, schema integrity, query performance, and concurrency safety.
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

**Description:** Automated unit, controller, repository, integration, security, service-to-service, contract, and mutation testing.
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

**Description:** Container images, local multi-service composition, networking, and externalized configuration.
Plan:
1. Dockerfiles
2. Containerize each service
3. Containerize Eureka
4. Compose local infrastructure
5. Configure service networking
6. Externalize environment configuration

### Phase 16 — Kubernetes

**Description:** Container orchestration with Deployments, Services, ConfigMaps, Secrets, probes, autoscaling, and namespaces.
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

**Description:** Automated build, test, security, image publication, deployment, release, and rollback workflows.
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

**Description:** Application and resilience visibility, metrics, logs, tracing, dashboards, and alerting.
Status: Staged — foundation first, remaining capabilities after resilience and failure testing

This phase is intentionally interleaved with Phase 12.

### Phase 18A — Observability Foundation

**Description:** Actuator, health endpoints, Micrometer metrics, Prometheus scraping, and Grafana baseline visibility.
Plan:
1. Spring Boot Actuator
2. Health endpoints
3. Micrometer metrics
4. Prometheus-format metrics
5. Basic Grafana dashboard
6. Baseline request rate, latency, and error rate
7. Optional early Correlation ID filter

### Phase 18B — Resilience Visibility

**Description:** Visibility into resilience controls, dependency failures, and later bulkhead/rate-limit behavior.
Delivered through Phase 12:
1. Expose Resilience4j metrics automatically
2. Observe timeout, retry, circuit-breaker, bulkhead, and rate-limit behavior
3. Compare resilience behavior with the pre-resilience baseline
4. Execute failure-path testing while monitoring the dashboard

### Phase 18C — Observability Expansion

**Description:** Centralized logging, correlation IDs, distributed tracing, richer dashboards, and alerts.
Status: Deferred — this is later work after the current Phase 12 core resilience scope.

Plan:
1. Centralized logging
2. Correlation IDs if not already introduced
3. Distributed tracing
4. Grafana dashboards for resilience and dependency behavior
5. Alerts

These observability items are intentionally not part of the current Phase 12 completion gate. Kafka remains deferred until the required resilience/observability expansion is completed.

### Phase 19 — AWS Cloud Deployment

**Description:** Production cloud deployment of the application using the AWS services explicitly planned for this project.
Status: Planned — starts only after Phases 12–18C are implemented and validated

The AWS phase is intentionally gated behind resilience, testing, containerization, Kubernetes, CI/CD, and observability. The project will not move to AWS deployment while those local/cloud-neutral foundations are incomplete.

Certification targets:
- AWS Certified Cloud Practitioner (CLF-C02)
- AWS Certified Developer - Associate (DVA-C03)
- DVA-C02 remains relevant only for candidates completing that exam before November 30, 2026; DVA-C03 GA delivery begins December 1, 2026.

Official references:
- https://aws.amazon.com/certification/certified-cloud-practitioner/
- https://aws.amazon.com/certification/certified-developer-associate/
- https://docs.aws.amazon.com/aws-certification/latest/cloud-practitioner-02/cloud-practitioner-02.html
- https://docs.aws.amazon.com/aws-certification/latest/developer-associate-03/

AWS implementation sequence:

#### Phase 19A — Cloud Foundations and Account Governance

**Description:** AWS environment structure, regional planning, governance, tagging, and cost-management foundations.
Hands-on:
1. AWS account structure and environment separation
2. AWS Regions and Availability Zones
3. Shared responsibility model
4. AWS Well-Architected Framework
5. AWS Cloud Adoption Framework awareness
6. AWS CLI and CloudShell
7. Tagging and environment naming conventions
8. AWS Pricing Calculator
9. Cost Explorer
10. AWS Budgets
11. Support plans and AWS Knowledge Center / re:Post awareness

Certification coverage:
- CLF-C02 Cloud Concepts
- CLF-C02 Billing, Pricing, and Support
- DVA-C03 AWS account/application lifecycle fundamentals

#### Phase 19B — IAM and Application Security

**Description:** Least-privilege IAM, workload identity, secrets, encryption, audit logging, and edge security.
Hands-on:
1. IAM users, groups, roles, and policies
2. Principle of least privilege
3. Root-account protection and MFA
4. IAM role-based access for workloads
5. AWS STS / temporary credentials
6. EKS Pod Identity / workload IAM
7. AWS Secrets Manager
8. AWS KMS
9. Encryption at rest and in transit
10. CloudTrail audit logging
11. AWS WAF
12. GuardDuty and security-service awareness
13. Preserve application JWT/RBAC while adding AWS authorization boundaries
14. Compare application authentication with AWS resource authorization

Certification coverage:
- CLF-C02 Security and Compliance
- DVA-C03 Security

#### Phase 19C — VPC and Networking

**Description:** Public/private networking, routing, security groups, load-balancer networking, and multi-AZ design.
Hands-on:
1. VPC
2. CIDR planning
3. Public and private subnets
4. Route tables
5. Internet Gateway
6. NAT Gateway
7. Security Groups
8. Network ACL awareness
9. DNS and Route 53
10. Private database networking
11. Load-balancer networking
12. VPC endpoints / PrivateLink awareness
13. High availability across Availability Zones

Target network:
Client -> Route 53 -> ALB -> EKS -> RDS

Certification coverage:
- CLF-C02 global infrastructure and network services
- DVA-C03 networking and secure application connectivity

#### Phase 19D — Containers, ECR, and EKS

**Description:** Container packaging, image storage/scanning, EKS deployment, configuration, health, scaling, and rollout controls.
Hands-on:
1. Docker image creation
2. Image versioning with Git SHA / semantic tags
3. Amazon ECR repositories
4. Image scanning and lifecycle concepts
5. Amazon EKS cluster
6. Kubernetes namespaces
7. Deployments
8. Services
9. ConfigMaps
10. Secrets integration
11. Liveness/readiness/startup probes
12. Requests/limits
13. Horizontal Pod Autoscaler
14. PodDisruptionBudget
15. Rolling deployment
16. ALB integration through AWS Load Balancer Controller
17. EKS logging and observability

Target runtime:
ECR -> EKS -> Spring Boot microservices

Certification coverage:
- CLF-C02 compute/containers
- DVA-C03 container management, deployment, testing, troubleshooting

#### Phase 19E — Managed Data

**Description:** RDS MySQL, private connectivity, backup/recovery, database migrations, S3, and supporting managed data services.
Hands-on:
1. Amazon RDS for MySQL
2. RDS connectivity from private EKS subnets
3. Multi-AZ concepts
4. Backups and point-in-time recovery
5. Parameter groups / option concepts
6. Database credentials through Secrets Manager
7. Database migrations
8. DynamoDB fundamentals and access-pattern thinking
9. ElastiCache awareness and caching use cases
10. S3 for object storage / exports / deployment artifacts

Production application path:
Spring Boot -> RDS MySQL

Certification labs:
- RDS
- DynamoDB
- ElastiCache
- S3

#### Phase 19F — Application Integration and Event-Driven AWS

**Description:** SQS, SNS, EventBridge, retry/backoff, dead-letter handling, idempotency, and AWS messaging comparisons.
Hands-on:
1. Amazon SQS
2. Amazon SNS
3. Amazon EventBridge
4. Compare queue, pub/sub, and event-bus patterns
5. Dead-letter queues
6. Idempotency
7. Retry and backoff
8. Event-driven failure handling
9. Compare Kafka/Apache Kafka with AWS managed messaging services
10. Later map the project's Kafka design to Amazon MSK where appropriate

Certification coverage:
- DVA-C03 messaging and event-driven development
- CLF-C02 application integration awareness

#### Phase 19G — API, Edge, and Delivery

**Description:** ALB, API Gateway comparison, Route 53, TLS/ACM, WAF, and gateway/discovery architecture comparisons.
Hands-on:
1. Existing Spring Cloud Gateway as the application gateway
2. AWS Application Load Balancer as the infrastructure ingress
3. Amazon API Gateway comparison/lab
4. Route 53 DNS
5. HTTPS/TLS and ACM
6. CloudFront awareness and static/content delivery use cases
7. WAF protection at the edge

Architecture:
Internet -> Route 53 -> ALB -> API Gateway -> Services

The project will explicitly compare:
- ALB vs API Gateway
- Spring Cloud Gateway vs API Gateway
- Kubernetes Service discovery vs Eureka

#### Phase 19H — Observability, Troubleshooting, and Performance

**Description:** CloudWatch, tracing, structured logs, diagnostics, performance tuning, and load-validation practices.
Hands-on:
1. CloudWatch metrics
2. CloudWatch Logs
3. CloudWatch dashboards
4. CloudWatch alarms
5. CloudWatch Container Insights
6. AWS X-Ray / distributed tracing concepts
7. Structured application logging
8. Correlation IDs
9. Health/readiness/liveness monitoring
10. Root-cause analysis from logs, metrics, and traces
11. CPU/memory optimization
12. Connection-pool tuning
13. Autoscaling
14. Caching
15. Performance and load testing

The existing Prometheus + Grafana stack remains the local development baseline. AWS observability is added as the cloud runtime layer.

Certification coverage:
- CLF-C02 cloud operations concepts
- DVA-C03 Troubleshooting and Optimization

#### Phase 19I — CI/CD and Infrastructure as Code

**Description:** Build and deployment automation plus versioned infrastructure and release configuration.
Hands-on:
1. GitHub as the source repository
2. AWS CodeBuild
3. AWS CodePipeline
4. Deployment artifacts
5. Automated unit/integration/security tests
6. Docker build and ECR push
7. Kubernetes deployment
8. Environment promotion
9. Rolling / blue-green / canary deployment concepts
10. Rollback
11. Infrastructure as Code
12. AWS CloudFormation
13. AWS CDK awareness and comparison
14. Versioned configuration
15. Release tagging

Target pipeline:
Git push -> build -> test -> security checks -> Docker build -> ECR -> EKS deployment -> health validation

Certification coverage:
- DVA-C03 Testing and Deployment
- CLF-C02 deployment and management concepts

#### Phase 19J — Serverless and Developer AWS Labs

**Description:** Lambda-based integrations, AWS SDK/CLI workflows, and focused developer-platform labs.
These services will be used for focused learning labs rather than forced into the FoodManagement production architecture.

Labs:
1. AWS Lambda
2. Lambda environment variables and configuration
3. Lambda timeout, memory, concurrency, and retry behavior
4. Lambda + SQS
5. Lambda + EventBridge
6. API Gateway + Lambda
7. AWS SDK usage from Java
8. AWS CLI workflows
9. Amazon Q Developer for development assistance
10. Amazon Bedrock awareness/lab
11. AI-service security and sensitive-data handling awareness

Certification coverage:
- DVA-C03 Development with AWS Services
- DVA-C03 security for AWS-integrated applications
- Current AWS developer certification emphasis on AI-assisted development

#### Phase 19K — AWS Cost and Reliability Engineering

**Description:** Cost controls, right-sizing, reliability trade-offs, disaster recovery, and Well-Architected review.
Hands-on:
1. Cost allocation tags
2. Cost Explorer
3. AWS Budgets
4. Pricing Calculator
5. On-Demand vs Reserved/Savings Plans/Spot concepts
6. Right-sizing
7. EKS/RDS cost drivers
8. Log/metric retention cost awareness
9. Multi-AZ trade-offs
10. Availability vs cost trade-offs
11. AWS Well-Architected review
12. Reliability and disaster-recovery concepts

#### Phase 19L — Final AWS Deployment

**Description:** Final production routing, EKS workloads, managed data, security, observability, CI/CD, and release validation.
Target architecture:

Internet
  |
Route 53
  |
ALB
  |
EKS
  |
  +-- API Gateway
  |
  +-- User Management
  +-- Restaurant Management
  +-- Order Management
  +-- Delivery Management
  |
  +-- Eureka initially; Kubernetes DNS evaluated as a later modernization
  |
RDS MySQL

Supporting AWS services:
- ECR
- Secrets Manager
- KMS
- CloudWatch
- CloudTrail
- WAF
- IAM
- S3
- SQS/SNS/EventBridge
- Route 53
- ACM
- CodeBuild / CodePipeline
- X-Ray / tracing
- Budgets / Cost Explorer

#### AWS phase Definition of Done

The application is considered AWS-ready only when:
- No runtime dependency requires localhost
- No production credential or JWT secret is committed to Git
- Configuration is environment-specific
- Docker images are reproducible and versioned
- Images are stored in ECR
- Services deploy successfully to EKS
- Health probes are configured
- RDS connectivity works from private subnets
- Secrets are managed outside source control
- ALB routing works
- HTTPS is enabled
- IAM follows least privilege
- Application and infrastructure logs/metrics are observable
- CI/CD can build, test, publish, deploy, and roll back
- Cost controls are configured
- Failure scenarios have been tested
- AWS architecture has been reviewed against the Well-Architected Framework

### Pre-AWS gate

AWS deployment is blocked until all of the following are complete and validated:
- Phase 12 — Fault Tolerance
- Phase 13 — Data and Transaction Hardening
- Phase 14 — Testing and Quality Engineering
- Phase 15 — Docker
- Phase 16 — Kubernetes
- Phase 17 — CI/CD
- Phase 18A — Observability Foundation
- Phase 18B — Resilience Visibility
- Phase 18C — Observability Expansion
- Kafka / event-driven design remains deferred until those gates are complete.


## Current project phase status

| Phase | Status | Pending items |
|---|---|---|
| 0 | Not formally defined | Define Phase 0 scope if needed |
| 1–2 | Complete | None |
| 3–4 | Core complete | Hardening items remain |
| 5–9 | Complete | None |
| 10 | Complete & validated | Cleanup of unused legacy artifacts is deferred |
| 11A | Complete & validated | None |
| 11B | Pending / deferred | Kafka implementation after resilience and observability gates |
| 12 | Complete & validated | Core resilience scope complete; Bulkhead, rate limiting, correlation IDs, tracing, and alerts deferred |
| 13–17 | Pending | Data hardening, testing, Docker, Kubernetes, CI/CD |
| 18A | Complete & validated | None |
| 18B–18C | Deferred / staged | Later resilience visibility, correlation, tracing, dashboards, and alerts |
| 19A–19L | Planned / gated | AWS foundations, platform setup, cloud deployment, and final validation after preceding gates |

### Phase 12 validation and closure

The current Phase 12 core resilience scope has been validated successfully.

Validated:
- Restaurant Management unavailable -> HTTP 503
- Restaurant Management timeout -> HTTP 504
- Retry policy behavior -> PASS
- Circuit Breaker normal CLOSED state -> PASS
- Fallback invocation -> PASS
- Circuit Breaker OPEN transition -> PASS
- OPEN circuit rejects calls without invoking the downstream service -> PASS
- OPEN -> HALF_OPEN recovery -> PASS
- HALF_OPEN successful recovery -> CLOSED -> PASS
- HALF_OPEN failure -> OPEN -> PASS
- 404 business errors remain non-breaker failures -> PASS

Phase 12 core status:
- Timeouts -> VALIDATED
- Downstream service-unavailable handling -> VALIDATED
- Retry policies -> VALIDATED
- Circuit breakers -> VALIDATED
- Fallbacks -> VALIDATED

Deferred later work:
- Bulkhead isolation
- Rate limiting
- Correlation IDs if not introduced in a future logging slice
- Distributed tracing
- Alerts

These deferred items are tracked in the later resilience/observability roadmap and do not block the current Phase 12 core completion.
## API testing with sample data

### Canonical test endpoint

All Postman collections use the API Gateway as the default test endpoint:

    http://localhost:8080

Postman variable:

    testBaseUrl=http://localhost:8080

Direct service URLs remain available for troubleshooting:

    userServiceUrl=http://localhost:8002
    restaurantServiceUrl=http://localhost:8001
    orderServiceUrl=http://localhost:8003
    deliveryServiceUrl=http://localhost:8004

### Start the system

    MySQL
    Eureka Server :8761
    User Management :8002
    Restaurant Management :8001
    Order Management :8003
    Delivery Management :8004
    API Gateway :8080

### Login

    POST {{testBaseUrl}}/auth/login

Request:

    {
      "email": "<valid-user-or-admin-email>",
      "password": "<valid-password>"
    }

Response:

    {
      "token": "<JWT>"
    }

The Postman login request stores the JWT in \`TOKEN\`. After a user is promoted to ADMIN, log in again to receive a JWT containing the new role.

### User — create USER

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

Public registration always creates USER. Sending \`"role": "ADMIN"\` in the public request cannot create an ADMIN.

### User — own profile update

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

USER may update only the authenticated user's own profile. ADMIN may update any user.

### User — ADMIN role management

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

Only ADMIN may promote/demote users. The final remaining ADMIN cannot be demoted.

### Restaurant — create restaurant

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

### Restaurant — add item

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

### Order — place order

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

### Order — update status

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

### Delivery — create agent

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

### Delivery — create assignment

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

The IDs above are illustrative. During Postman execution, use the IDs captured in the active environment.

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
    FoodManagement-API-Gateway.postman_collection.json

Environments:

    FoodManagement-USER.postman_environment.json
    FoodManagement-ADMIN.postman_environment.json
    FoodManagement-environment.postman_environment.json

Basic Postman sequence:

    Select USER or ADMIN environment
    -> For Gateway testing, run FoodManagement-API-Gateway / Login - Through Gateway
    -> For existing direct-service testing, run FoodManagement-Login / Login - Current Environment
    -> Use the generated TOKEN automatically
    -> Run Gateway regression or the required direct-service collection
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


## Observability Foundation — Complete

Status: Implemented and locally validated.

### Management ports

| Component | App port | Management port |
|---|---:|---:|
| API Gateway | 8080 | 9100 |
| Restaurant Management | 8001 | 9101 |
| User Management | 8002 | 9102 |
| Order Management | 8003 | 9103 |
| Delivery Management | 8004 | 9104 |

The management ports expose Actuator separately from business traffic. Business APIs retain the existing JWT / USER / ADMIN security model.

### Prometheus + Grafana

The local observability infrastructure is under /observability and contains Docker Compose, Prometheus configuration, Grafana datasource provisioning, dashboard provisioning, and the FoodManagement dashboard.

Current Docker scope:

    Prometheus :9090
    Grafana    :3000

The Spring Boot services remain local JVM processes. Full application containerization is deferred to the later Docker phase.

### Validation

    curl.exe -i http://localhost:<management-port>/actuator/health
    curl.exe -i http://localhost:<management-port>/actuator/prometheus

Expected: HTTP 200.

Validate Docker:

    docker --version
    docker compose version
    docker info
    cd observability
    docker compose config
    docker compose pull
    docker compose up -d
    docker compose ps

Validate Prometheus at http://localhost:9090, then Status -> Targets. Expected targets: API Gateway, User, Restaurant, Order, Delivery, and Prometheus all UP.

Prometheus baseline queries:

    up
    http_server_requests_seconds_count
    http_server_requests_seconds_bucket

Validate Grafana at http://localhost:3000. Confirm the FoodManagement Prometheus datasource and FoodManagement Observability Overview dashboard.

### Docker image workflow

An image is the packaged template. A container is a running instance of an image. docker push uploads an image to a registry; it does not push an image into a container.

The current observability Compose file uses:

    prom/prometheus:latest
    grafana/grafana:latest

Future application image workflow, after Dockerfiles are introduced:

    docker build -t <registry>/<namespace>/foodmanagement-<service>:<version> ./<service>
    docker login <registry>
    docker push <registry>/<namespace>/foodmanagement-<service>:<version>

On another machine:

    docker pull <registry>/<namespace>/foodmanagement-<service>:<version>

Do not use the application-image commands yet for this observability-only Docker setup.

### Observability validation checklist

- All five management health endpoints -> 200
- All five Prometheus endpoints -> 200
- Prometheus targets -> UP
- Grafana datasource -> successful
- Request rate -> populated
- Error rate -> validated with a deliberate 4xx/5xx request
- P95/P99 latency -> populated
- Service health -> populated
- JVM heap -> populated
- CPU -> populated

## Project-level architecture roadmap

See **FOODMANAGEMENT_ROADMAP_ARCHITECTURE.md** for the current runtime architecture, observability architecture, execution gates, and future Docker/Kubernetes/AWS roadmap.
