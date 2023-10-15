# Base auth project with jwt

This project uses Spring Boot, Spring Security, Spring JPA, Hibernate, Flyway Migration,
Thymeleaf, H2 Database, JsonWebToken and Map Struct.

It was used SonarLint and SonarQube to verify the code quality.

## Project architecture

I divided the architecture in 4 principal groups:
- commons
  - Utilities classes and annotations
- domain
  - The model layer divided by domain. Each domain has your own service,
  repository, entity, dto, adapter and enums folders.
- infra
  - Layer of configurations, exceptions, data loaders and for access external apis.
- rest
  - Rest layer application.

The layer cannot access layers that is above here, but layers of top level
can access layers of bottom level.

Domain and commons are layers on the same level.

Rest and Infra are layers above all the others, but both are on the same level.

So: (infra <-> rest) -> (domain <-> commons) 
