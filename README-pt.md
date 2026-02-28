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
- ✅ Camadas separadas: application, adapters 

---

## 🛠️ Tecnologias

- **Java 25+**, Spring Boot 3.x
- JPA (em adapters/persistence)
- Docker
- PostgresSQL
- Gradle
- JUnit 5
- Jacoco
- diff-coverage

---

## 📂 Estrutura
```
src/
├── application/
│   ├── domain/              # Entidades e VOs puros
│   ├── repositories/        # Portas de saída
│   ├── usecases/            # Casos de uso (interfaces)
│   ├── services/            # Implementações dos casos de uso
│   └── shared/              # Tipos genéricos como PaginatedResult
├── adapters/
│   ├── inbound/       # Entradas como API rest ou consumers
│   ├── outbound/ # Saidas como camada de persistencia ou publishers
│   └── config/              # Configs técnicas (Spring, Swagger)
```

---

## 🔧 Rodando Projeto Localmente
Para rodar localmente o projeto se tem duas opções:
- Sem Docker
- Com Docker
### Sem Docker
Se voce quiser rodar esse projeto sem docker, precisa ter instalado:
- Java 25
- PostgreSQL running on port `5432`
- Kafka
 E executar esse comando na raiz do projeto:

```bash
./gradlew bootRun
```
### Com Docker
Agora para rodar com docker :
Se não estiver rodando a infra por favor fazer run dela antes:
```bash
 docker compose -f docker-compose-infra.yml up -d 
```

```bash
docker compose run -d
```
---

## 📄 Decisões Técnicas
Todas as decisões relacionadas ao desing ou arquitetura da aplicação estão dentro da pasta **/adrs**.

---

## 🤝 Contribuições

Aberto a contribuições! Sinta-se à vontade para abrir issues ou PRs com melhorias, testes ou novas features.

---

## 📄 Licença

MIT