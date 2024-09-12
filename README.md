# Library Web API 

This is a simple Library REST API microservice project built with Spring Boot and PostgresSQL as the database. There are 6 main microservices:

- Library Main Service - service that contains main api for bookDto CRUD operations
- Library Auth Service - service for authorization and authentication
- Library Reservation Service - service for bookDto reservation
- Library API Gateway Server - a gateway server which processes requests to microservices
- Library Config Server - server that contains configs for microservices
- Library Discovery Server - server that registers all existing services

Also this project contains 2 docker images:

- PostgreSQL Database
- pgAdmin

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Web
- Liquibase
- PostgresSQL Driver
- Lombok
- JWT
- Spring Doc
- Spring Cloud
- Spring Cloud Eureka Discovery Server
- Spring Cloud Config
- Spring Cloud API Gateway

## Requirements

In order to run this microservice project, Docker and Docker Compose must be installed.

## Running the Application

1. Clone this repository:
   ```git clone https://github.com/AlexisIndustries/modsen-bookDto-service```

2. Install Docker on your machine
3. Run:
   ```docker-compose up -d```
4. Wait 1-3 minutes to load every service properly.
5. Go to http://localhost:8080/swagger-ui.html to view all available routes
6. Go to security/token endpoint and get your JWT token
7. Use generated JWT to make requests