# Movio

Event management platform for Ukrainian movie screenings in Germany.

## About the Project

**Team:** Developed by a team of 6 developers.

**Key Backend Contributions (leonfesjuk):**
- Designed API contracts and technical specifications
- Developed Events & Cinemas API (entity → repository → service → controller)
- Implemented Geonames service (city search via JPQL)
- Configured role-based access and endpoint permissions
- Organized PostgreSQL database connection (migrated from MySQL)
- Unit tests for business logic

## Project Structure

```
movio/
├── be/              # Backend API (Spring Boot)
└── fe/              # Frontend Dashboard (React)
```

## Tech Stack

### Backend
- Java 17 + Spring Boot 3
- PostgreSQL
- JWT Authentication (Access/Refresh tokens)
- Gradle + GitHub Actions CI/CD

### Frontend
- React 18 + TypeScript
- Vite
- Redux Toolkit
- Tailwind CSS

## Features

- **Cinemas Management** — CRUD operations for cinema venues
- **Events Management** — Create and manage events with filtering (by city, cinema, organization)
- **City Search (Geonames)** — Geographic data integration
- **JWT Authentication** — Secure cookie-based auth with role-based access
- **Admin Dashboard** — React-based UI for platform management

## API

Backend runs on `http://localhost:8080`

Key endpoints:
- `/api/v1/auth/*` — Authentication
- `/api/v1/cinemas/*` — Cinema management
- `/api/v1/events/*` — Event management
- `/api/v1/geonames/*` — City search

## User Roles

| Role | Permissions |
|------|-------------|
| ADMIN | Full platform access |
| USER | Manage own entities |

---

For detailed documentation, see:
- [Backend README](be/README.md)
- [Frontend README](fe/README.md)
