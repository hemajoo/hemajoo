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

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.OrientDBContainer;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class CustomOrientDBContainer extends GenericContainer<CustomOrientDBContainer>
{
    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("orientdb");

    private static final String DEFAULT_TAG = "latest";

    private static final String DEFAULT_USERNAME = "admin";

    private static final String DEFAULT_PASSWORD = "admin";

    private static final String DEFAULT_SERVER_PASSWORD = "root";

    private static final String DEFAULT_DATABASE_NAME = "testcontainers";

    private static final int DEFAULT_BINARY_PORT = 2424;

    private static final int DEFAULT_HTTP_PORT = 2480;

    private String databaseName;

    private String serverPassword;

    private Optional<String> scriptPath = Optional.empty();

    private OrientDB orientDB;

    private ODatabaseSession session;

    /**
     * @deprecated use {@link OrientDBContainer(DockerImageName)} instead
     */
    @Deprecated
    public CustomOrientDBContainer() {
        this(DEFAULT_IMAGE_NAME.withTag(DEFAULT_TAG));
    }

    public CustomOrientDBContainer(@NonNull String dockerImageName) {
        this(DockerImageName.parse(dockerImageName));
    }

    public CustomOrientDBContainer(final DockerImageName dockerImageName) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DEFAULT_IMAGE_NAME);

        serverPassword = DEFAULT_SERVER_PASSWORD;
        databaseName = DEFAULT_DATABASE_NAME;

        //waitStrategy = new LogMessageWaitStrategy().withRegEx(".*Gremlin started correctly.*");

        addExposedPorts(DEFAULT_BINARY_PORT, DEFAULT_HTTP_PORT);
    }

    @Override
    protected void configure() {
        addEnv("ORIENTDB_ROOT_PASSWORD", serverPassword);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getTestQueryString() {
        return "SELECT FROM V";
    }

    public CustomOrientDBContainer withDatabaseName(final String databaseName) {
        this.databaseName = databaseName;
        return self();
    }

    public CustomOrientDBContainer withServerPassword(final String serverPassword) {
        this.serverPassword = serverPassword;
        return self();
    }

    public CustomOrientDBContainer withScriptPath(String scriptPath) {
        this.scriptPath = Optional.of(scriptPath);
        return self();
    }

    @Override
    protected void containerIsStarted(InspectContainerResponse containerInfo) {
        orientDB = new OrientDB(getServerUrl(), "root", serverPassword, OrientDBConfig.defaultConfig());
    }

    public OrientDB getOrientDB() {
        return orientDB;
    }

    public String getServerUrl() {
        return "remote:" + getHost() + ":" + getMappedPort(2424);
    }

    public String getDbUrl() {
        return getServerUrl() + "/" + databaseName;
    }

    public ODatabaseSession getSession() {
        return getSession(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public synchronized ODatabaseSession getSession(String username, String password) {
        orientDB.createIfNotExists(databaseName, ODatabaseType.PLOCAL);

        if (session == null) {
            session = orientDB.open(databaseName, username, password);

            scriptPath.ifPresent(path -> loadScript(path, session));
        }
        return session;
    }

    private void loadScript(String path, ODatabaseSession session) {
        try {
            URL resource = getClass().getClassLoader().getResource(path);

            if (resource == null) {
                LOGGER.warn("Could not load classpath init script: {}", scriptPath);
                throw new RuntimeException(
                        "Could not load classpath init script: " + scriptPath + ". Resource not found."
                );
            }

            String script = IOUtils.toString(resource, StandardCharsets.UTF_8);

            session.execute("sql", script);
        } catch (IOException e) {
            LOGGER.warn("Could not load classpath init script: {}", scriptPath);
            throw new RuntimeException("Could not load classpath init script: " + scriptPath, e);
        } catch (UnsupportedOperationException e) {
            LOGGER.error("Error while executing init script: {}", scriptPath, e);
            throw new RuntimeException("Error while executing init script: " + scriptPath, e);
        }
    }
}

