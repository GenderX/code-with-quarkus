# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Quarkus Java application using:
- Java 21
- Quarkus 3.24.3 
- Maven build system
- REST-based architecture with Jackson JSON serialization
- JUnit 5 and REST Assured for testing

## Essential Commands

### Development
- **Start dev mode**: `./mvnw quarkus:dev` (enables live coding with Dev UI at http://localhost:8080/q/dev/)
- **Build application**: `./mvnw package`
- **Run tests**: `./mvnw test`
- **Run integration tests**: `./mvnw verify` or `./mvnw failsafe:integration-test`

### Production Builds
- **Standard JAR**: `./mvnw package` (produces `target/quarkus-app/quarkus-run.jar`)
- **Uber JAR**: `./mvnw package -Dquarkus.package.jar.type=uber-jar`
- **Native executable**: `./mvnw package -Dnative`
- **Native with container**: `./mvnw package -Dnative -Dquarkus.native.container-build=true`

## Project Structure

- `src/main/java/org/acme/`: Main application code
  - REST resources follow JAX-RS annotations (@Path, @GET, etc.)
- `src/test/java/org/acme/`: Unit tests using @QuarkusTest
- `src/main/resources/application.properties`: Configuration (currently empty)
- `src/main/docker/`: Dockerfile variants for different deployment scenarios

## Testing Framework

- Uses JUnit 5 with Quarkus test extensions
- REST Assured for API testing
- Integration tests use suffix `IT` and run with failsafe plugin
- Tests automatically configure Quarkus test context with @QuarkusTest annotation

## Key Dependencies

- `quarkus-rest`: Core REST functionality
- `quarkus-rest-jackson`: JSON serialization
- `quarkus-arc`: CDI container
- `quarkus-junit5`: Testing support