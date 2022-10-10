# Hemajoo Library

## Description

The `Hemajoo Library` project aims to provide low-level entities and features via several modules:

- `hemajoo-commons`
- `hemajoo-utilities`
- `hemajoo-i18n`


## Status

| Metric     | Status                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|:-----------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `CI/CD`    | [![CI/CD](https://github.com/hemajoo/hemajoo/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/hemajoo/hemajoo/actions/workflows/build.yml)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| `Quality`  | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent) |
| `Issues`   | [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=bugs)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent) [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent) [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent) [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent)                                               |
| `Metrics`  | [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent) [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent)                                                                                                                                                                                                                                                                                                                                                                                                                                |
| `Coverage` | [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.hemajoo%3Ahemajoo-parent&metric=coverage)](https://sonarcloud.io/summary/new_code?id=com.hemajoo%3Ahemajoo-parent)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |

## Hemajoo Commons

The **Hemajoo Commons** module provides low-level entities such as :

- `DbContainer` class providing an easy way to get a database docker container for one of the following database types:

  - `POSTGRES`
  - `MYSQL`
  - `IBM_DB2`
  - `MICROSOFT_SQL_SERVER`
  - `ORACLE`
  - `NEO4J`
  - `MARIADB`
  - `MONGODB`
  - `DYNALITE`
  - `CASSANDRA`
  - `COUCHBASE`
  - `CLICKHOUSE`
  - `INFLUXDB`
  - `ORIENTDB`
  - `PRESTO`
  - `TRINO`
  - `TIDB`

When using a DbContainer, the client application is intended to provide an `application.properties` containing the following properties:

- `spring.datasource.username`
- `spring.datasource.password`
- `spring.datasource.database-name

Note that for some DBMS, having an initial database name setup is not available, same for an initial user.

## Hemajoo Utilities


## Hemajoo Internationalization (i18n)



## Infrastructure

### See: [Hemajoo Document microservice - Infrastructure](./doc/infrastructure.md)


## DevOps

### See: [Hemajoo Document microservice - DevOps](./doc/devops.md)


## REST endpoints

### See: [Hemajoo Document microservice - REST](./doc/rest.md)


## Release History

### See: [Hemajoo Document microservice - Release History](./doc/release_history.md)


## Documentation

### See: [Hemajoo Document microservice - Documentation](./doc/documentation.md)


## Processes

### See: [Hemajoo Document microservice - Processes](./doc/processes.md)


## Notes

### See: [Hemajoo Document microservice - Notes](./doc/notes.md)


## Links

### See: [Hemajoo Document microservice - Links](./doc/links.md)

