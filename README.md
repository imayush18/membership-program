# Membership Program

Backend system for managing membership plans, tiers, subscriptions, and configurable member benefits.

## Tech Stack

| | |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4 |
| Persistence | Spring Data JPA + MySQL |
| Build | Maven |
| API Docs | Springdoc OpenAPI (Swagger) |

---

## Features

- **Membership Plans** — Monthly, Quarterly, Yearly with configurable pricing
- **Membership Tiers** — Silver, Gold, Platinum with configurable benefits per tier
- **Subscription Management** — Subscribe, upgrade, downgrade, cancel
- **Tier Evaluation Engine** — Strategy pattern to evaluate eligible tier based on order count, order value, or user cohort
- **Expiry Tracking** — Inline expiry check on every API call
- **Concurrency Safety** — Optimistic locking via `@Version` on subscriptions
- **Auto Seeding** — Plans, tiers, benefits and tier criteria seeded on first startup

---

## Project Structure

```
src/main/java/membership_program/
├── config/
│   └── DataSeeder.java           # Seeds plans, tiers, benefits on startup
├── controller/
│   ├── MembershipPlanController  # GET plans, POST evaluate-tier
│   └── SubscriptionController    # Subscribe, upgrade, downgrade, cancel
├── dto/                          # Request / Response objects
├── entity/                       # JPA entities
├── enums/                        # PlanType, TierLevel, SubscriptionStatus
├── exception/                    # GlobalExceptionHandler, ResourceNotFoundException
├── repository/                   # Spring Data JPA repositories
├── service/
│   ├── MembershipService         # Core subscription business logic
│   └── TierEvaluationService     # Runs all strategies, returns highest eligible tier
└── strategy/
    ├── TierEvaluationStrategy    # Interface
    ├── OrderCountStrategy        # Eligible if orderCount >= minOrderCount
    ├── OrderValueStrategy        # Eligible if totalOrderValue >= minOrderValue
    └── CohortStrategy            # Eligible if user cohort matches configured cohort
```

---

## API Documentation

Once the application is running, Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/plans` | Get all membership plans with tiers and benefits |
| POST | `/api/plans/evaluate-tier` | Get recommended tier based on user activity |
| POST | `/api/subscriptions` | Subscribe to a plan and tier |
| GET | `/api/subscriptions/{userId}` | Get current subscription and expiry |
| PUT | `/api/subscriptions/{userId}/upgrade` | Upgrade to next tier |
| PUT | `/api/subscriptions/{userId}/downgrade` | Downgrade to previous tier |
| PUT | `/api/subscriptions/{userId}/cancel` | Cancel subscription |

---

## Sample Requests & Responses

### GET `/api/plans`
```json
[
  {
    "id": 1,
    "name": "Monthly Plan",
    "planType": "MONTHLY",
    "durationDays": 30,
    "price": 99.00,
    "tiers": [
      {
        "id": 1,
        "name": "Silver",
        "tierLevel": "SILVER",
        "benefits": [
          { "benefitType": "FREE_DELIVERY", "benefitValue": "true" }
        ]
      },
      {
        "id": 2,
        "name": "Gold",
        "tierLevel": "GOLD",
        "benefits": [
          { "benefitType": "FREE_DELIVERY", "benefitValue": "true" },
          { "benefitType": "DISCOUNT", "benefitValue": "10%" },
          { "benefitType": "EARLY_ACCESS", "benefitValue": "true" }
        ]
      }
    ]
  }
]
```

### POST `/api/plans/evaluate-tier`
**Request**
```json
{
  "userId": 1,
  "orderCount": 12,
  "totalOrderValue": 6000.00,
  "cohort": "PREMIUM_USER"
}
```
**Response**
```json
{
  "userId": 1,
  "recommendedTier": "GOLD"
}
```

### POST `/api/subscriptions`
**Request**
```json
{
  "userId": 1,
  "planId": 1,
  "tierId": 2
}
```
**Response**
```json
{
  "subscriptionId": 1,
  "userId": 1,
  "planName": "Monthly Plan",
  "tierName": "Gold",
  "tierLevel": "GOLD",
  "startDate": "2025-01-01",
  "endDate": "2025-01-31",
  "cancelledAt": null,
  "status": "ACTIVE"
}
```

### PUT `/api/subscriptions/{userId}/upgrade`
**Response**
```json
{
  "subscriptionId": 1,
  "userId": 1,
  "planName": "Monthly Plan",
  "tierName": "Platinum",
  "tierLevel": "PLATINUM",
  "startDate": "2025-01-01",
  "endDate": "2025-01-31",
  "status": "ACTIVE"
}
```

---

## Design Decisions

- **Strategy Pattern** for tier evaluation — adding a new criteria (e.g. referral count) requires only a new `@Component` implementing `TierEvaluationStrategy`, zero changes to existing code
- **Configurable thresholds** — tier criteria (`minOrderCount`, `minOrderValue`, `cohort`) are stored in DB via `TierCriteriaEntity`, not hardcoded
- **Optimistic Locking** — `@Version` on `SubscriptionEntity` prevents concurrent upgrades/cancellations from overwriting each other
- **Inline expiry check** — instead of a scheduled job, subscriptions are lazily expired at the time of any API call, keeping the app stateless and reliable across restarts

---

## Setup

### Prerequisites
- Java 17
- MySQL

### Steps

1. Create the database
```sql
CREATE DATABASE membership_db;
```

2. Update credentials in `src/main/resources/application.yml`
```yaml
datasource:
  username: your_username
  password: your_password
```

3. Run the application
```bash
./mvnw spring-boot:run
```

The app will auto-seed plans, tiers, and benefits on first startup.
