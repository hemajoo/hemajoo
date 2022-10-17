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
package com.hemajoo.commons.annotation.support;

import java.lang.annotation.*;

/**
 * Types and/or methods annotated with the {@link ScheduledForRemoval} annotation are subject to a removal in a future version.<br><br>
 * It is a stronger variant of the {@link Deprecated} annotation.
 * <br><br>
 * Since many tools aren't aware of this annotation it should be used as an addition to the {@link Deprecated} annotation or the {@code @deprecated} Javadoc tag.
 * <br>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface ScheduledForRemoval
{
    /**
     * Version of the library.
     * @return Version.
     */
    String version() default "";
}
