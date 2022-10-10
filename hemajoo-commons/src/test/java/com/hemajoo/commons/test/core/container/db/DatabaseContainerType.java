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

import lombok.Getter;
import lombok.NonNull;
import org.testcontainers.utility.DockerImageName;

/**
 * Enumerate the several possible <b>database container</b> type values.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum DatabaseContainerType
{
    /**
     * <b>PostgreSQL</b> also known as <b>Postgres</b>, is a free and open-source relational database management system (RDBMS) emphasizing extensibility and SQL compliance. It was originally
     * named <b>POSTGRES</b>, referring to its origins as a successor to the Ingres database developed at the University of California, Berkeley. In 1996, the project was renamed to PostgreSQL to
     * reflect its support for SQL. After a review in 2007, the development team decided to keep the name PostgreSQL and the alias Postgres.
     * <br><br>
     * <b>PostgreSQL</b> is a powerful, open source object-relational database system with over 30 years of active development that has earned it a strong reputation for reliability, feature robustness,
     * and performance.
     * <br><br>
     * See: <a href="https://www.postgresql.org">PostgreSQL</a>
     */
    POSTGRES("postgres:latest"),

    /**
     * <b>MySQL</b> is an open-source relational database management system (RDBMS). Its name is a combination of "My", the name of co-founder Michael Widenius's daughter My, and "SQL", the
     * abbreviation for Structured Query Language. A relational database organizes data into one or more data tables in which data may be related to each other; these relations help structure the
     * data.<br><br>
     * SQL is a language programmers use to create, modify and extract data from the relational database, as well as control user access to the database. In addition to relational databases
     * and SQL, an RDBMS like MySQL works with an operating system to implement a relational database in a computer's storage system, manages users, allows for network access and facilitates
     * testing database integrity and creation of backups.
     * <br><br>
     * See: <a href="https://www.mysql.com">MySQL</a>
     */
    MYSQL("mysql:latest"),

    /**
     * <b>Db2</b> is a family of data management products, including database servers, developed by <b>IBM</b>. They initially supported the relational model, but were extended to support
     * object–relational features and non-relational structures like JSON and XML. The brand name was originally styled as DB/2, then DB2 until 2017 and finally changed to its present form.
     * <br><br>
     * Designed by the world’s leading database experts, IBM Db2 empowers developers, DBAs, and enterprise architects to run low-latency transactions and real-time analytics equipped for the
     * most demanding workloads. From microservices to AI workloads, Db2 is the tested, resilient, and hybrid database providing the extreme availability, built-in refined security, effortless
     * scalability, and intelligent automation for systems that run the world.
     * <br><br>
     * See: <a href="https://www.ibm.com/db2">IBM Db2</a>
     */
    IBM_DB2("ibmcom/db2:11.5.0.0a"),

    /**
     * <b>MariaDB</b> Server is one of the most popular open source relational databases. It’s made by the original developers of <b>MySQL</b> and guaranteed to stay open source. It is part of
     * most cloud offerings and the default in most Linux distributions.<br><br>
     * It is built upon the values of performance, stability, and openness, and MariaDB Foundation ensures contributions will be accepted on technical merit. Recent new functionality includes
     * advanced clustering with Galera Cluster 4, compatibility features with Oracle Database and Temporal Data Tables, allowing one to query the data as it stood at any point in the past.
     * <br><br>
     * See: <a href="https://mariadb.org">MariaDB foundation</a>
     */
    MARIADB("mariadb:latest"),

    /**
     * <b>MongoDB</b> is a source-available cross-platform document-oriented database program. Classified as a NoSQL database program, MongoDB uses JSON-like documents with optional schemas.
     * MongoDB is developed by MongoDB Inc. and licensed under the Server Side Public License (SSPL) which is deemed non-free by several distributions.
     * <br><br>
     * <b>Build faster, Build smarter</b>.<br>
     * Get your ideas to market faster with a developer data platform built on the leading modern database. Support transactional, search, analytics, and mobile use cases while using a common
     * query interface and the data model developers love.
     * <br><br>
     * See: <a href="https://www.mongodb.com">MongoDB</a>
     */
    MONGODB("mongo:latest"),

    /**
     * <b>Microsoft SQL Server</b> is a relational database management system developed by Microsoft. As a database server, it is a software product with the primary function of storing and
     * retrieving data as requested by other software applications—which may run either on the same computer or on another computer across a network (including the Internet).
     * <br><br>
     * Microsoft markets at least a dozen different editions of Microsoft SQL Server, aimed at different audiences and for workloads ranging from small single-machine applications to large
     * Internet-facing applications with many concurrent users.
     * <br><br>
     * See: <a href="https://www.microsoft.com/en-us/sql-server/sql-server-2019">Microsoft SQL Server</a>
     */
    MICROSOFT_SQL_SERVER("mcr.microsoft.com/mssql/server"),

    /**
     * <b>Neo4j</b> is a graph database management system developed by Neo4j, Inc. Described by its developers as an ACID-compliant transactional database with native graph storage and processing,
     * Neo4j is available in a non-open-source "community edition" licensed with a modification of the GNU General Public License, with online backup and high availability extensions licensed under
     * a closed-source commercial license. Neo also licenses Neo4j with these extensions under closed-source commercial terms.
     * <br><br>
     * Neo4j is implemented in Java and accessible from software written in other languages using the Cypher query language through a transactional HTTP endpoint, or through the binary "Bolt"
     * protocol. The "4j" in Neo4j is a reference to its being built in Java, however is now largely viewed as an anachronism.
     * <br><br>
     * See: <a href="https://neo4j.com">Neo4j</a>
     */
    NEO4J("neo4j:latest"),

    /**
     * <b>Dynalite</b> is an implementation of Amazon's DynamoDB built on LevelDB for fast in-memory or persistent usage.<br>
     * This project aims to match the live DynamoDB instances as closely as possible (and is tested against them in various regions), including all limits and error messages.
     * <br><br>
     * See: <a href="https://github.com/mhart/dynalite">Dynalite</a>
     */
    DYNALITE("dimaqq/dynalite:latest"),

    /**
     * <b>Cassandra</b> is a free and open-source, distributed, wide-column store, NoSQL database management system designed to handle large amounts of data across many commodity servers,
     * providing high availability with no single point of failure.
     * <br><br>
     * <b>Cassandra</b> offers support for clusters spanning multiple datacenters, with asynchronous masterless replication allowing low latency operations for all clients. Cassandra was designed
     * to implement a combination of Amazon's Dynamo distributed storage and replication techniques combined with Google's Bigtable data and storage engine model.
     * <br><br>
     * See: <a href="https://cassandra.apache.org/_/index.html">Apache Cassandra</a>
     */
    CASSANDRA("cassandra:latest"),

    /**
     * <b>Couchbase Server</b>, originally known as <b>Membase</b>, is an open-source, distributed (shared-nothing architecture) multi-model NoSQL document-oriented database software package
     * optimized for interactive applications. These applications may serve many concurrent users by creating, storing, retrieving, aggregating, manipulating and presenting data. In support of
     * these kinds of application needs, Couchbase Server is designed to provide easy-to-scale key-value or JSON document access with low latency and high sustained throughput. It is designed to be
     * clustered from a single machine to very large-scale deployments spanning many machines.
     * <br><br>
     * Couchbase Server provided client protocol compatibility with memcached, but added disk persistence, data replication, live cluster reconfiguration, rebalancing and multitenancy with
     * data partitioning.
     * <br><br>
     * See: <a href="https://www.couchbase.com">Couchbase</a>
     */
    COUCHBASE("couchbase/server:latest"),

    /**
     * <b>ClickHouse</b> is an open-source column-oriented DBMS (columnar database management system) for online analytical processing (OLAP) that allows users to generate analytical reports
     * using SQL queries in real-time.
     * <br><br>
     * See: <a href="https://clickhouse.com">ClickHouse</a>
     */
    CLICKHOUSE("bitnami/clickhouse:latest"),

    /**
     * <b>InfluxDB</b> is an open-source time series database (TSDB) developed by the company InfluxData. It is written in the Go programming language for storage and retrieval of time series
     * data in fields such as operations monitoring, application metrics, Internet of Things sensor data, and real-time analytics. It also has support for processing data from Graphite.
     * <br><br>
     * See: <a href="https://www.influxdata.com">InfluxData</a>
     */
    INFLUXDB("influxdb:latest"),

    /**
     * <b>Oracle</b> Database (commonly referred to as Oracle DBMS, Oracle Autonomous Database, or simply as Oracle) is a multi-model database management system produced and marketed by
     * Oracle Corporation.<br>
     * It is a database commonly used for running online transaction processing (OLTP), data warehousing (DW) and mixed (OLTP & DW) database workloads. Oracle Database is available by several
     * service providers on-prem, on-cloud, or as hybrid cloud installation. It may be run on third party servers as well as on Oracle hardware (Exadata on-prem, on Oracle Cloud or at Cloud
     * at Customer).
     * <br><br>
     * See: <a href="https://www.oracle.com/database/">Oracle Database</a>
     */
    ORACLE("gvenzl/oracle-xe:18.4.0-slim"),

    /**
     * <b>OrientDB</b> is an open source NoSQL database management system written in Java. It is a Multi-model database, supporting graph, document, key/value, and object models, but the
     * relationships are managed as in graph databases with direct connections between records. It supports schema-less, schema-full and schema-mixed modes. It has a strong security profiling
     * system based on users and roles and supports querying with Gremlin along with SQL extended for graph traversal. OrientDB uses several indexing mechanisms based on B-tree and extensible
     * hashing, the last one is known as "hash index", there are plans to implement LSM-tree and Fractal tree index based indexes.
     * <br><br>
     * Each record has Surrogate key which indicates position of record inside of Array list, links between records are stored either as single value of record's position stored inside of
     * referrer or as B-tree of record positions (so-called record IDs or RIDs) which allows fast traversal (with O(1) complexity) of one-to-many relationships and fast addition/removal of new links.
     * <br><br>
     * <b>OrientDB</b> is the fifth most popular graph database according to the DB-Engines graph database ranking, as of December 2021.
     * <br><br>
     * The development of OrientDB still relies on an open source community led by OrientDB LTD company created by its original author Luca Garulli. The project uses GitHub to manage the sources,
     * contributors and versioning, Google Group and Stack Overflow to provide free support to the worldwide users. OrientDB also offers a free Udemy course for those hoping to learn the basics
     * and get started with OrientDB.
     * <br><br>
     * See: <a href="https://orientdb.org">OrientDB</a>
     */
    ORIENTDB("orientdb:latest"),

    /**
     * <b>Presto</b> (including PrestoDB, and PrestoSQL which was re-branded to Trino) is a distributed query engine for big data using the SQL query language. Its architecture allows users to
     * query data sources such as Hadoop, Cassandra, Kafka, AWS S3, Alluxio, MySQL, MongoDB and Teradata, and allows use of multiple data sources within a query. Presto is community-driven
     * open-source software released under the Apache License.
     * <br><br>
     * See: <a href="https://prestodb.io">Presto</a>
     */
    PRESTO(""),

    /**
     * <b>Trino</b> is an open-source distributed SQL query engine designed to query large data sets distributed over one or more heterogeneous data sources. Trino can query datalakes that contain
     * open column-oriented data file formats like ORC or Parquet residing on different storage systems like HDFS, AWS S3, Google Cloud Storage, or Azure Blob Storage using the Hive and Iceberg
     * table formats.<br>
     * <b>Trino</b> also has the ability to run federated queries that query tables in different data sources such as MySQL, PostgreSQL, Cassandra, Kafka, MongoDB and Elasticsearch. Trino is
     * released under the Apache License.
     * <br><br>
     * See: <a href="https://trino.io">Trino</a>
     */
    TRINO("trinodb/trino:latest"),

    /**
     * <b>TiDB</b> is an open-source NewSQL database that supports Hybrid Transactional and Analytical Processing (HTAP) workloads. It is MySQL compatible and can provide horizontal scalability,
     * strong consistency, and high availability. It is developed and supported primarily by PingCAP, Inc. and licensed under Apache 2.0. TiDB drew its initial design inspiration from Google's
     * Spanner and F1 papers.
     * <br><br>
     * See: <a href="https://www.pingcap.com">PingCap</a>
     */
    TIDB("pingcap/tidb:latest");

    /**
     * Docker image name.
     */
    @Getter
    private final DockerImageName dockerImageName;

    /**
     * Create a database container type.
     * @param imageName Image name.
     */
    DatabaseContainerType(final @NonNull String imageName)
    {
        this.dockerImageName = DockerImageName.parse(imageName);
    }
}
