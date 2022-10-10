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
package com.hemajoo.commons.test.database;

import com.hemajoo.commons.test.core.AbstractHemajooUnitTest;
import com.hemajoo.commons.test.core.DatabaseTestConfiguration;
import com.hemajoo.commons.test.core.container.db.CustomOrientDBContainer;
import com.hemajoo.commons.test.core.container.db.DatabaseContainerType;
import com.hemajoo.commons.test.core.container.db.DbContainer;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@Testcontainers // Not to be used to keep container alive after the tests!
@SpringBootTest(classes = { DatabaseTestConfiguration.class })
@Log4j2
class DatabaseContainerUnitTest extends AbstractHemajooUnitTest
{
    /**
     * Database configuration.
     */
    @Autowired
    protected DatabaseTestConfiguration dbConfiguration;

    /**
     * Database container.
     */
    protected DbContainer dbContainer;

    @BeforeAll
    public void beforeAll()
    {
        // Empty
    }

    @AfterAll
    public void afterAll()
    {
        // Empty
    }

    @Test
    @DisplayName("Create an IBM Db2 database container")
    void testCreateIbmDb2DatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.IBM_DB2)
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.IBM_DB2.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create a Microsoft SQL Server database container")
    void testCreateMicrosoftSQLServerDatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.MICROSOFT_SQL_SERVER)
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.MICROSOFT_SQL_SERVER.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create a Postgres database container")
    void testCreatePostgresDatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.POSTGRES)
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.POSTGRES.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create an Oracle database container")
    void testCreateOracleDatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.ORACLE)
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.ORACLE.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create a MariaDB database container")
    void testCreateMariaDBDatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.MARIADB)
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.MARIADB.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create an OrientDB database container")
    void testCreateOrientDBDatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.ORIENTDB)
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.ORIENTDB.getDockerImageName().asCanonicalNameString());

        ODatabaseSession session = ((CustomOrientDBContainer) dbContainer.getContainer()).getSession("root", "root");
        session.command("CREATE CLASS Person EXTENDS V");
        session.command("INSERT INTO Person set name='john'");
        session.command("INSERT INTO Person set name='jane'");
        OResultSet result = session.query("SELECT FROM Person");

        assertThat(session.query("SELECT FROM Person").stream()).hasSize(2);

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create an Apache Cassandra database container")
    void testCreateCassandraDBDatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.CASSANDRA)
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.CASSANDRA.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create a Couchbase database container")
    void testCreateCouchbaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.COUCHBASE)
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.COUCHBASE.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create a MySQL Server database container")
    void testCreateMySQLDatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.MYSQL)
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.MYSQL.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create a MongoDB database container")
    void testMongoDBDatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.MONGODB)
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.MONGODB.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }

    @Test
    @DisplayName("Create a Neo4j database container")
    void testNeo4jDatabaseContainer()
    {
        dbContainer = DbContainer.builder()
                .withType(DatabaseContainerType.NEO4J)
                .withUsername(dbConfiguration.getDatasourceUsername())
                .withPassword(dbConfiguration.getDatasourcePassword())
                .withDatabaseName(dbConfiguration.getDatasourceDbName())
                .build();

        assertThat(dbContainer.getContainer().isRunning()).isTrue();
        assertThat(dbContainer.getContainer().getDockerImageName()).isEqualTo(DatabaseContainerType.NEO4J.getDockerImageName().asCanonicalNameString());

        dbContainer.stop();
    }
}
