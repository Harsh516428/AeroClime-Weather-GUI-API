# AeroClime - Weather Forecasting & Climate Analysis (Starter)

This repository contains a GitHub-ready starter project for the AeroClime Weather Forecasting & Climate Analysis System (Java Spring Boot).

## Contents
- Spring Boot backend that fetches weather from OpenWeatherMap
- JDBC-based DAO to store observations in PostgreSQL
- Scheduled ingestion using ScheduledExecutorService
- Simple statistical endpoints
- Schema and run instructions

## Quick Start

1. Install Java 17+, Maven, and PostgreSQL.
2. Create a Postgres database:
   ```bash
   createdb aeroclime_db
   psql -d aeroclime_db -f schema.sql
   ```
3. Edit `src/main/resources/application.properties`:
   - Set `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`
   - Set `openweathermap.api.key=YOUR_KEY`
4. Build and run:
   ```bash
   mvn clean package
   mvn spring-boot:run
   ```
   or:
   ```bash
   java -jar target/aeroclime-weather-0.1.0.jar
   ```

## Endpoints
- `GET /api/health` — health check
- `POST /api/weather/{city}/fetch` — fetch current weather for city and save to DB
- `GET  /api/weather/{city}/stats?lastN=10` — compute simple stats from last N records

## Notes for Reviewers
This project demonstrates:
- OOP (interfaces, polymorphism, custom exception)
- Collections & generics utilities
- Multithreading (scheduled ingestion)
- JDBC connectivity and DAO classes
- Schema and simple queries

Add contributors, screenshots or further docs as needed.
