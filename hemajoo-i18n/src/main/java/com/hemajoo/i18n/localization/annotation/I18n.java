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
package com.hemajoo.i18n.localization.annotation;

import java.lang.annotation.*;

/**
 * Annotation used to specify a type's fields, method, field or local variable as a <b>localize</b> resource.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE } )
public @interface I18n
{
    /**
     * Resource bundle path and name containing the localization.
     * @return Resource bundle path and name.
     */
    String bundle() default "";

    /**
     * Resource bundle key used to resolve a localization or translation (ex.: <b>com.hemajoo.cherry.base.data.model.continent.${continentType}.name</b>).
     * @return Resource bundle key.
     */
    String key() default "";
}
