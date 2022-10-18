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
package com.hemajoo.i18n.localization.exception;

import com.hemajoo.commons.exception.HemajooException;

/**
 * Exception thrown to indicate an error occurred with a resource.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class ResourceException extends HemajooException
{
    /**
     * Thrown to indicate that an error occurred with a resource.
     *
     * @param exception Parent exception.
     */
    public ResourceException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Thrown to indicate that an error occurred with a resource.
     *
     * @param message Message describing the error being the cause of the raised exception.
     */
    public ResourceException(final String message)
    {
        super(message);
    }

    /**
     * Thrown to indicate that an error occurred with a resource.
     *
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent exception.
     */
    public ResourceException(final String message, final Exception exception)
    {
        super(message, exception);
    }
}

