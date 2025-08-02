# DevConnect 👥

A Networking and Portfolio Platform for Developers – built with clean architecture and a focus on scalability and DDD best practices.

If you want to read in portuguese we have the [README-pt.md](./README-pt.md)

---

## ✨ Overview

**DevConnect** is a fullstack system focused on technical profiles, portfolios, and developer connections.  
Designed with **Java (Spring Boot)** using **hexagonal architecture**, **DDD**, and **domain-oriented values**.

---

## 🧱 Architecture

- ✅ **Isolated domain** (no technical dependencies)
- ✅ **Rich Value Objects** (`Password`, `Address`)
- ✅ Separated layers: `application`, `adapters`, `infrastructure`

---

## 🛠️ Technologies

- **Java 24+**, Spring Boot 3.x
- JPA (in `adapters/persistence`)
- Docker
- PostgreSQL
- Gradle
- JUnit 5
- Jacoco
- diff-coverage

---

## 📂 Project Structure

```
src/
├── application/
│   ├── domain/              # Pure entities and value objects
│   ├── repositories/        # Output ports
│   ├── usecases/            # Use case interfaces
│   ├── services/            # Use case implementations
│   └── shared/              # Generic types like PaginatedResult
├── adapters/
│   ├── inbound/          # REST APIs + consumers entry points
│   ├── outbound/         # Persistence + publishers exit points
│   └── config/              # Technical configs (Spring, Swagger)
```

---

## 🔧 Running Locally

Prerequisites:
- Java 24
- Docker
- PostgreSQL running on port `5432` (running outside Docker)

```bash
./gradlew bootRun
```

---

## 📄 Technical Decisions

All design or architecture decisions are documented inside the **/adrs** folder.

---

## 🤝 Contributions

Contributions are welcome! Feel free to open issues or PRs with improvements, tests, or new features.

---

## 📄 License

MIT