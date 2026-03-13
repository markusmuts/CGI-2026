# CGI 2026 ASSIGNMENT

Simple full-stack app with:

- Backend: Spring Boot (Java 21, Gradle)
- Frontend: React + Vite


## Prerequisites

- Java 21
- Node.js 20+ and npm

## Project Structure

- `backend/` Spring Boot API
- `frontend/` React app

## First Build After Pulling The Project

If you just cloned or pulled the latest changes, run a one-time build for both parts before starting the app.

### 1. Build backend

```bash
cd backend
./gradlew clean build
```

### 2. Install frontend dependencies and build

```bash
cd frontend
npm install
npm run build
```

After this, follow the Start The App section below.

## Start The App

Open two terminals.

### 1. Start backend

From the project root:

```bash
cd backend
./gradlew bootRun
```

Backend runs on `http://localhost:8080`.


### 2. Start frontend

From the project root:

```bash
cd frontend
npm install
npm run dev
```

Vite will print the local URL (`http://localhost:5173`).
