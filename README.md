# E-Commerce Backend System

A production-style backend application built using Java and Spring Boot,
designed to manage e-commerce operations using RESTful APIs and a layered architecture.

## Features
- RESTful APIs for product and inventory management
- CRUD operations using Spring Data JPA and Hibernate
- MySQL integration for persistent storage
- Layered architecture (Controller–Service–Repository)
- Exception handling and input validation
- API testing using Postman

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## Project Structure
- Controller layer for API endpoints
- Service layer for business logic
- Repository layer for database operations
- Entity layer for domain modeling

## How to Run
1. Clone the repository
2. Configure MySQL credentials in `application.yml`
3. Run the application:
   ```bash
   mvn spring-boot:run
