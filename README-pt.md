# DevConnect 👥

Plataforma de Networking e Portfólio para Desenvolvedores – construída com arquitetura limpa e foco em escalabilidade e boas práticas de DDD.

---

## ✨ Visão Geral

O **DevConnect** é um sistema fullstack com foco em perfis técnicos, portfólios e conexões entre desenvolvedores.  
Projetado com **Java (Spring Boot)** usando **arquitetura hexagonal**, **DDD**, e **valores orientados ao domínio**.

---

## 🧱 Arquitetura

- ✅ **Domínio isolado** (sem dependências técnicas)
- ✅ **Value Objects ricos** (Password, Address)
- ✅ Camadas separadas: application, adapters, infrastructure

---

## 🛠️ Tecnologias

- **Java 24+**, Spring Boot 3.x
- JPA (em adapters/persistence)
- Docker
- PostgresSQL
- Gradle
- JUnit 5

---

## 📂 Estrutura

src/
├── application/
│   ├── domain/              # Entidades e VOs puros
│   ├── repositories/        # Portas de saída
│   ├── usecases/            # Casos de uso (interfaces)
│   ├── services/            # Implementações dos casos de uso
│   └── shared/              # Tipos genéricos como PaginatedResult
├── adapters/
│   ├── controller/          # APIs REST
│   ├── persistence/         # JPA + conversores
│   └── config/              # Configs técnicas (Spring, Swagger)


---

## 🔧 Como rodar localmente

Pré-requisitos:
- Java 24
- Docker
- PostgreSQL rodando na porta 5432 (rodando sem docker)

bash
./gradlew bootRun


---

## 📄 Decisões Técnicas
Todas as decisões relacionadas ao desing ou arquitetura da aplicação estão dentro da pasta **/adrs**.

---

## 🤝 Contribuições

Aberto a contribuições! Sinta-se à vontade para abrir issues ou PRs com melhorias, testes ou novas features.

---

## 📄 Licença

MIT