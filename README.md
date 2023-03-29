# 🚀 Microservices Project

This project is a set of microservices built using Spring Boot. Each microservice is designed to handle a specific business capability, such as user management or order processing. The project was built as a learning exercise to gain experience with modern microservices architecture and cloud-native technologies.

## 🛠️ Technologies Used

The following technologies were used in the project:

- 🍃 Spring Boot: A popular Java framework for building microservices.
- 🐳 Docker: A containerization platform used for packaging and deploying the microservices.
- 🐳 Docker Compose: A tool for defining and running multi-container Docker applications.
- 🐘 PostgreSQL: An open-source relational database used for storing the microservices data.
- 📚 Swagger: A tool for generating API documentation for the microservices.
- 🌟 Netflix Eureka: A service registry used for service discovery.
- 📈 Spring Cloud Sleuth: A distributed tracing solution for Spring Cloud applications.
- 🔐 Spring Security OAuth 2.0 Resource Server
- 🔥 Spring WebFlux: A reactive web framework for building non-blocking applications.
- ☁️ Spring Cloud Config: A centralized configuration server for distributed systems.
- 🚪 Spring Cloud Gateway: A reactive gateway for routing and filtering requests.
- 🔌 resilience4j Circuit Breaker: A library for handling and recovering from failures in distributed systems.
- 🧱 resilience4j Bulkhead: A library for limiting concurrent access to external systems.
- 📦 Redis: An in-memory data structure store used as a database, cache, and message broker.

## 🚀 Getting Started

To get started with the project, you'll need to have the following prerequisites installed on your system:

- ☕ Java 19 or later
- 🐳 Docker
- 🐳 Docker Compose

Once you have the prerequisites installed, you can build and run the microservices using the following commands:

```bash
docker compose up
```

This command will start up the microservices in Docker containers.

After the microservices are up and running, you can access them using the following URLs:

- 🔐 Auth service: http://localhost:7778/api/auth
- 🤵 Admin service: http://localhost:7778/api/admin
- 🧑‍🦱 Customer service: http://localhost:7778/api/customer
- 👨‍💼 Attendant service: http://localhost:7778/api/attendant
- 👩‍⚕️ Veterinarian service: http://localhost:7778/api/veterinarian
- 🏥 Visit service: http://localhost:7778/api/visit

## 🎓 Learning Goals

The main learning goals of the project were:

- 🏗️ Understanding the principles and benefits of microservices architecture.
- 💻 Learning how to design and implement microservices using Spring Boot.
- 🐳 Gaining experience with containerization and Docker.
- 🐳 Learning how to use Docker Compose to manage multi-container applications.
- 🐘 Understanding how to use PostgreSQL as a database for the microservices.
- 📦 Learning how to use Redis for cache
- 🔥 Learning how to build reactive applications with Spring WebFlux.
- 🌟 Understanding how to use Netflix Eureka for service discovery.
- 📈 Learning how to use Spring Cloud Sleuth for distributed tracing.
- ☁️ Learning how to use Spring Cloud Config to manage configuration in distributed systems.
- 🚪 Learning how to use Spring Cloud Gateway for routing and filtering requests.
- 🔐 Understanding how to use Spring Security OAuth 2.0 for securing microservices.
- 🔌 Learning how to use resilience4j Circuit Breaker for handling failures in distributed systems.
- 🧱 Learning how to use resilience4j Bulkhead for limiting concurrent access to external systems.
