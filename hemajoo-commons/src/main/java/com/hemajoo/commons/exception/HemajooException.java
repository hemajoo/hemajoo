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
package com.hemajoo.commons.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * Exception thrown to indicate an error occurred with a <b>Hemajoo</b> entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class HemajooException extends Exception implements Serializable
{
    /**
     * Default serialization identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Thrown to indicate that an error occurred with a <b>Hemajoo</b> entity.
     * @param exception Parent exception.
     */
    public HemajooException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Thrown to indicate that an error occurred with a <b>Hemajoo</b> entity.
     * @param message Message describing the error being the cause of the raised exception.
     */
    public HemajooException(final String message)
    {
        super("❗️ " + message);
    }

    /**
     * Thrown to indicate that an error occurred with a <b>Hemajoo</b> entity.
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent exception.
     */
    public HemajooException(final String message, final Exception exception)
    {
        super("❗️ " + message, exception);
    }
}
