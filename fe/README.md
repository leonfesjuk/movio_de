# Moonstone Frontend

A React-based admin dashboard for managing cinemas and events.

## Tech Stack

- **React 18** + **TypeScript**
- **Vite** for fast development
- **Redux Toolkit** for state management
- **Tailwind CSS** for styling
- **Axios** for API requests (with interceptors)

## Quick Start

```bash
npm install
npm run dev
```

## Authentication

Cookie-based authentication with automatic token refresh:
- Access Token stored in HttpOnly cookies
- Axios interceptors handle 401 errors and token refresh
- Example: `src/features/auth/authSlice.ts`

## Project Structure

```
src/
├── components/          # Reusable UI components
│   └── ui/              # Base UI kit (buttons, modals, etc.)
├── features/            # Feature-based modules
│   ├── auth/            # Authentication
│   │   ├── slice.ts     # Redux slice + API calls
│   │   ├── services/    # Auth API endpoints
│   │   └── types.ts     # Feature-specific types
│   ├── cinemas/         # Cinema management
│   ├── events/          # Event management
│   └── ...
├── lib/
│   └── axiosInstance.ts # Axios config with interceptors
├── pages/               # Route-level components
└── types/               # Global shared types
```

### Feature Structure

Each feature follows the **FSD (Feature-Sliced Design)** pattern:

| Folder | Purpose |
|--------|---------|
| `slice.ts` | Redux state + business logic |
| `services/` | API calls specific to this feature |
| `types.ts` | Local types/interfaces |

## Error Handling

Centralized error handling via Axios interceptors:
- Server errors trigger notifications
- Example implementation in `src/features/auth/authSlice.ts`

## API Integration

Base URL configured in `src/lib/axiosInstance.ts`:
- Cookie credentials enabled
- Request/response interceptors
- Automatic retry on 401

## Environment

Variables defined in `.env`:
```
VITE_API_URL=http://localhost:8080/api/v1
```
