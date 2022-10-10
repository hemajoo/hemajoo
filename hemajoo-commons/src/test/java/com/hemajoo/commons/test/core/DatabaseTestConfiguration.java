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

package com.hemajoo.commons.test.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseTestConfiguration
{
    /**
     * Datasource URL.
     */
    @Getter
    @Setter
    @Value("${spring.datasource.url}")
    protected String datasourceUrl;

    /**
     * Datasource user name.
     */
    @Getter
    @Value("${spring.datasource.username}")
    protected String datasourceUsername;

    /**
     * Datasource user name.
     */
    @Getter
    @Value("${spring.datasource.password}")
    protected String datasourcePassword;

    /**
     * Datasource database name.
     */
    @Getter
    @Value("${spring.datasource.database-name}")
    protected String datasourceDbName;
}
