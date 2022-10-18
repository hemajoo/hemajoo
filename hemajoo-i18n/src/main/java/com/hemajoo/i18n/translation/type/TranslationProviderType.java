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
package com.hemajoo.i18n.translation.type;

/**
 * Enumeration exposing the possible <b>translation providers</b> type.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum TranslationProviderType
{
    /**
     * <b>Unknown</b> translation provider.
     */
    UNKNOWN,

    /**
     * <b>Microsoft Azure</b>> translation provider.
     */
    AZURE,

    /**
     * <b>IBM</b>> translation provider.
     */
    IBM,

    /**
     * <b>Google</b>> free translation provider.
     */
    GOOGLE_FREE,

    /**
     * <b>Google</b>> translation provider.
     */
    GOOGLE,

    /**
     * <b>Other</b>> (unspecified) translation provider.
     */
    OTHER;
}
