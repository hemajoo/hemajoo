## Hemajoo Commons module

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
- `spring.datasource.database-name`

Note that for some DBMS, having an initial database name setup is not available, same for an initial user.
