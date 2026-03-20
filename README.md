# Pushups — Gym Management System (Backend)

A full-stack gym management application built with **Spring Boot / Kotlin** and **Nuxt.js**. Members can log in via Google, browse weekly class schedules, and book sessions. Admins manage members, bookings, and class rosters through a role-based dashboard.

**Live demo:** [pushupsfrontend.up.railway.app](https://pushupsfrontend.up.railway.app)
**Frontend repo:** [github.com/IliasIosifidis/PushUpsFrontend](https://github.com/IliasIosifidis/PushUpsFrontend)

---

## Screenshots

| Admin View | Member View | Member Stats |
|:---:|:---:|:---:|
| ![Admin dashboard](docs/admin-view.jpeg) | ![Member dashboard](docs/member-view.jpeg) | ![Attendance chart](docs/stats-modal.jpeg) |

> Admin view shows full member management (add, edit, deactivate) and booking controls. Member view is read-only with self-booking. The stats modal displays monthly attendance via a bar chart.

---

## Tech Stack

### Backend
- **Kotlin** + **Spring Boot 3**
- **Spring Security** with Google OAuth2 (OIDC)
- **Spring Data JPA** / Hibernate
- **MySQL** (Railway-hosted)
- **DataFaker** for realistic seed data

### Frontend
- **Nuxt 3** (Vue 3, file-based routing, auto-imports)
- **Pinia** for state management
- **Tailwind CSS v3**
- **Axios** for API calls
- **Chart.js** + vue-chartjs for attendance charts

### Infrastructure
- **Railway** for backend, frontend, and MySQL hosting
- Environment variable injection for all secrets

---

## Architecture

```
┌─────────────┐       OAuth2        ┌──────────────────┐        JPA         ┌─────────┐
│   Nuxt.js   │ ──── sessions ────▶ │   Spring Boot    │ ◀───────────────▶  │  MySQL  │
│  (Frontend) │ ◀─── JSON API ───── │   (Backend)      │                    │   (DB)  │
└─────────────┘                     └──────────────────┘                    └─────────┘
                                           │
                                    Google OAuth2
                                     (login flow)
```

The frontend communicates with the backend via REST. Authentication uses Spring Security's OAuth2 login flow with session cookies — the frontend sends `withCredentials: true` on every request.

---

## Domain Model

**Member** — gym members with name, email, phone, active status, and role (MEMBER or ADMIN).

**GymClass** — recurring weekly classes (Aerobic, Groups, Calisthenics, CrossFit, Yoga) with day-of-week, start/end time, and max capacity of 20.

**Booking** — links a member to a class on a specific date. A unique constraint on (member, class, date) prevents double-booking.

Soft-delete is used for members (`active` boolean) so booking history is preserved for analytics.

---

## API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/auth/me` | Authenticated | Returns current user info and role |
| PUT | `/api/auth/demo/{id}/toggle-role` | Authenticated | Toggles a member between ADMIN and MEMBER (demo only) |

### Members
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/member` | Admin | List all members (paginated) |
| GET | `/api/member/active` | Admin | List active members (paginated) |
| GET | `/api/member/search?name=` | Admin | Search by first or last name |
| POST | `/api/member` | Admin | Create a new member |
| PUT | `/api/member/{id}` | Admin | Update a member |
| PATCH | `/api/member/{id}` | Admin | Toggle active/inactive |
| DELETE | `/api/member/{id}` | Admin | Delete a member |

### Bookings
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/booking` | Authenticated | List all bookings (paginated) |
| GET | `/api/booking/weekly?date=` | Authenticated | Weekly calendar: classes grouped by day with enrolled members |
| GET | `/api/booking/member/{id}` | Authenticated | A member's bookings (paginated) |
| GET | `/api/booking/memberStats/{id}` | Authenticated | List of all booking dates for a member (used for the attendance chart) |
| POST | `/api/booking` | Authenticated | Book a member into a class |
| DELETE | `/api/booking/{id}` | Admin | Remove a booking |

### Classes
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/class` | Public | List all classes |
| GET | `/api/class/daily?dayOfWeek=` | Public | Classes for a specific day |

---

## Security

Role-based access using Spring Security:

- **Public** — class listings
- **Authenticated** — weekly calendar, bookings, own profile
- **Admin only** — member CRUD, booking deletion

On first Google login, a new `Member` record is automatically created with the `MEMBER` role. Admins are promoted manually via the database or the demo toggle endpoint.

The frontend reads `/api/auth/me` to determine the user's role and conditionally renders admin-only features (Add Member button, Edit/Delete controls, member management panel).

---

## Weekly Calendar Endpoint

The `/api/booking/weekly?date=` endpoint is the core of the booking view. Given any date, it:

1. Calculates Monday–Friday of that week
2. For each day, queries all scheduled `GymClass` records by day-of-week
3. Joins with bookings for that date range to attach enrolled members
4. Returns a structured response: **week → day → class → members**

This ensures classes with zero bookings still appear in the calendar — important for future dates.

---

## Data Seeding

The `DataInitializer` generates realistic demo data on first run:

- **200 active members** with fake names, emails, and phone numbers (via DataFaker)
- **25 classes per week** (5 class types × 5 weekdays), each with a 20-person capacity
- **~1 year of booking history** with randomized attendance (8–20 members per class)

The seeder includes a failsafe to skip if data already exists, preventing duplicate records on restart.

---

## Running Locally

### Prerequisites
- JDK 17+
- MySQL running locally
- A Google OAuth2 client ID and secret ([Google Cloud Console](https://console.cloud.google.com/))

### Setup

1. Clone the repo:
```bash
git clone https://github.com/IliasIosifidis/PushUpsBackend.git
cd PushUpsBackend
```

2. Create `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pushups
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=create
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=openid,profile,email
```

3. Run:
```bash
./gradlew bootRun
```

4. After seeding completes, switch `ddl-auto` to `update` to preserve data across restarts.

---

## Deployment (Railway)

The backend is deployed on Railway with environment variables for all secrets:

- `MYSQLHOST`, `MYSQLPORT`, `MYSQLDATABASE`, `MYSQLUSER`, `MYSQLPASSWORD` — injected by Railway's MySQL plugin
- `GOOGLE_CLIENT_SECRET` — set manually in Railway's Variables tab

The `application.properties` in the repo uses `${VARIABLE}` syntax to reference these at runtime. No secrets are committed to version control.

---

## Project Structure

```
src/main/kotlin/org/pushups/gymgoers/
├── config/
│   ├── CorsConfig.kt                  # CORS setup for frontend origins
│   ├── SecurityConfig.kt              # Spring Security + OAuth2 rules
│   └── OAuth2LoginSuccessHandler.kt   # Auto-creates member on first Google login
├── controller/
│   ├── AuthController.kt              # /api/auth — login status, role toggle
│   ├── MemberController.kt            # /api/member — CRUD + search
│   ├── BookingController.kt           # /api/booking — weekly calendar, stats
│   └── GymClassController.kt          # /api/class — class listings
├── dto/
│   ├── MemberDto.kt, BookingDto.kt, GymClassDto.kt
│   ├── WeeklyBookingsDto.kt           # Nested weekly calendar response
│   ├── Mappers.kt                     # Entity → DTO extension functions
│   └── AddMemberRequest.kt, UpdateMemberRequest.kt, BookingRequest.kt
├── exception/
│   ├── GlobalExceptionHandler.kt      # @RestControllerAdvice
│   ├── ResourceNotFoundException.kt
│   └── ClassFullException.kt
├── model/
│   ├── Member.kt, Booking.kt, GymClass.kt
├── repository/
│   ├── MemberRepository.kt, BookingRepository.kt, GymClassRepository.kt
├── service/
│   ├── MemberService.kt + MemberServiceImpl.kt
│   ├── BookingService.kt + BookingServiceImpl.kt
│   ├── GymClassService.kt + GymClassServiceImpl.kt
│   └── CustomOidcUserService.kt
└── init/
    └── DataInitializer.kt             # Seed data on first run
```

---

## Key Design Decisions

- **Soft delete over hard delete** — members are deactivated (not removed) to preserve booking history for future analytics.
- **Backend-grouped weekly response** — the `/weekly` endpoint pre-groups bookings by day and class so the frontend can render the calendar grid directly without extra processing.
- **Session-based auth over JWT** — simpler for a server-rendered frontend; Spring Security manages the session cookie, and axios sends `withCredentials: true`.
- **GymClass as the base query for weekly view** — querying classes by day-of-week (not bookings) ensures days with zero bookings still show the full class schedule.
- **Demo role toggle** — a dedicated endpoint lets portfolio reviewers switch between admin and member views without manual database changes.
