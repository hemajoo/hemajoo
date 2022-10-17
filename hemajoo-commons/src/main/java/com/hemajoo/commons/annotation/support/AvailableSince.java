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
 * Types and methods annotated with the {@link AvailableSince} annotation are available since the given version of the library, meaning the code using
 * such elements won't or may not be compatible with older versions of the library.
 * <br><br>
 * This information may be used by IDEs and static analysis tools.<br>
 * This annotation can be used instead of '@since' Javadoc tag if it's needed to keep such information in <b>*.class</b> files or if there is a need to generate them automatically.
 * <br>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface AvailableSince
{
    /**
     * Version of the library.
     * @return Version.
     */
    String version() default "";
}
