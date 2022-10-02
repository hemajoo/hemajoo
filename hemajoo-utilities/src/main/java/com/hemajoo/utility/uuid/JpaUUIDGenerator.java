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
package com.hemajoo.utility.uuid;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * <b>UUID</b> (Universally Unique Identifier, also called Globally Unique Identifier or GUID) generator for database entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class JpaUUIDGenerator implements IdentifierGenerator
{
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSession, Object object) throws HibernateException
    {
        return UUID.randomUUID();
    }

    /**
     * Return a UUID identifier.
     * @return UUID identifier.
     */
    public static UUID getUuid()
    {
        return UUID.randomUUID();
    }
}
