/*
 * (C) Copyright Hemajoo Systems Inc. 2021-2022 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Systems Inc.
 * and its suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */

package com.hemajoo.commons.test.core.container.db;

import com.hemajoo.commons.exception.NotYetImplementedException;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.testcontainers.containers.*;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;

/**
 * Provide database <b>docker container</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * @see DatabaseContainerType
 */
@Log4j2
public class DatabaseContainer
{
    /**
     * Database container type.
     */
    @Getter
    private final DatabaseContainerType type;

    /**
     * User name.
     */
    @Getter
    private final String username;

    /**
     * User password.
     */
    @Getter
    private final String password;

    /**
     * Database name.
     */
    @Getter
    private final String databaseName;

    /**
     * Database docker container.
     */
    @Getter
    @Container
    protected GenericContainer<?> container = null;

    /**
     * Create a new database container.
     * @param type Database container type.
     * @param username User name.
     * @param password User password.
     * @param databaseName The name of the database to create when starting the container.
     */
    @Builder(setterPrefix = "with")
    public DatabaseContainer(final DatabaseContainerType type, final @NonNull String username, final @NonNull String password, final @NonNull String databaseName)
    {
        this.type = type;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;

        switch (type)
        {
            case POSTGRES:
                createPostgresContainer();
                break;

            case IBM_DB2:
                createDb2Container();
                break;

            case ORACLE:
                createOracleContainer();
                break;

            case MONGODB:
                createMongoDBContainer();
                break;

            case ORIENTDB:
                createOrientDBContainer();
                break;

            case MYSQL:
                createMySQLContainer();
                break;

            case MICROSOFT_SQL_SERVER:
                createMicrosoftSQLServerContainer();
                break;

            case MARIADB:
                createMariaDBContainer();
                break;

            case COUCHBASE:
                createCouchbaseContainer();
                break;

            case CASSANDRA:
                createCassandraContainer();
                break;

            case NEO4J:
                createNeo4jContainer();
                break;

            default:
                throw new NotYetImplementedException(type.name());
        }
    }

    /**
     * Create and start a <b>Postgres</b> database container.
     */
    private void createPostgresContainer()
    {
        container = new PostgreSQLContainer(type.getDockerImageName())
                .withUsername(username)
                .withPassword(password)
                .withDatabaseName(databaseName)
                .withReuse(true);
        start();
    }

    /**
     * Create and start a <b>IBM DB2</b> database container.
     */
    private void createDb2Container()
    {
        container = new Db2Container(type.getDockerImageName())
                .withUsername(username)
                .withPassword(password)
                .withDatabaseName(databaseName)
                .withReuse(true)
                .acceptLicense();
        start();
    }

    /**
     * Create and start a <b>MongoDB</b> database container.
     */
    private void createMongoDBContainer()
    {
        container = new MongoDBContainer(type.getDockerImageName());
        start();
    }

    /**
     * Create and start a <b>MariaDB</b> database container.
     */
    private void createMariaDBContainer()
    {
        container = new MariaDBContainer(type.getDockerImageName())
                .withDatabaseName(databaseName)
                .withUsername(username)
                .withPassword(password);
        start();
    }

    /**
     * Create and start an <b>OrientDB</b> database container.
     */
    private void createOrientDBContainer()
    {
        container = new CustomOrientDBContainer(type.getDockerImageName())
                //.withServerPassword(password)
                //.withEnv("ORIENTDB_ROOT_PASSWORD", password)
                .withDatabaseName(databaseName);
        start();
    }

    /**
     * Create and start a <b>MySQL</b> database container.
     */
    private void createMySQLContainer()
    {
        container = new MySQLContainer(type.getDockerImageName())
            .withDatabaseName(databaseName)
            .withUsername(username)
            .withPassword(password);
        start();
    }

    /**
     * Create and start a <b>Oracle</b> database container.
     */
    private void createOracleContainer()
    {
        container = new OracleContainer(type.getDockerImageName())
                .withEnv("ORACLE_PASSWORD", password)
                .withEnv("ORACLE_DATABASE", databaseName)
                .withEnv("APP_USER", username)
                .withEnv("APP_USER_PASSWORD", password);
        start();
    }

    /**
     * Create and start a <b>Microsoft SQL Server</b> database container.
     */
    private void createMicrosoftSQLServerContainer()
    {
        container = new MSSQLServerContainer(type.getDockerImageName())
                .acceptLicense()
                .withPassword(password);
        start();
    }

    /**
     * Create and start a <b>Cassandra</b> database container.
     */
    private void createCassandraContainer()
    {
        container = new CassandraContainer<>(type.getDockerImageName());
        start();
    }

    /**
     * Create and start a <b>Couchbase</b> database container.
     */
    private void createCouchbaseContainer()
    {
        container = new CouchbaseContainer(type.getDockerImageName())
                .withCredentials(username, password)
                .withBucket(new BucketDefinition("mybucket"))
                .withStartupTimeout(Duration.ofSeconds(90))
                .waitingFor(Wait.forHealthcheck());
        start();
    }

    /**
     * Create and start a <b>Neo4j</b> database container.
     */
    private void createNeo4jContainer()
    {
        container = new Neo4jContainer<>(type.getDockerImageName())
                .withoutAuthentication();
        start();
    }

    /**
     * Start the container.
     */
    public final void start()
    {
        LOGGER.info(String.format("\uD83D\uDC33 Starting database container type: '%s' at: '%s'", type, container.getHost()));
        container.start();
        LOGGER.info(String.format("\uD83D\uDC33 Database container of type: '%s' started on: '%s%s%s' for database name: '%s'", type, container.getHost(), ":", container.getFirstMappedPort(), databaseName));
    }

    /**
     * Stop the container.
     */
    public final void stop()
    {
        LOGGER.info(String.format("\uD83D\uDC33 Stopping database container type: '%s' at: '%s%s%s'", type, container.getHost(), ":", container.getFirstMappedPort()));
        container.stop();
        LOGGER.info(String.format("\uD83D\uDC33 Database container of type: '%s'", type));
    }
}
