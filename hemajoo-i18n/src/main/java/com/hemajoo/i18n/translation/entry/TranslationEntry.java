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
package com.hemajoo.i18n.translation.entry;

import com.hemajoo.i18n.localization.data.LanguageType;
import com.hemajoo.i18n.translation.entity.TranslationEntity;
import com.hemajoo.i18n.translation.type.TranslationProviderType;
import lombok.*;

/**
 * Represent an entry being part of a {@link TranslationEntity}.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@EqualsAndHashCode
public class TranslationEntry implements ITranslationEntry
{
    /**
     * Entry language.
     */
    @Getter
    private final LanguageType language;

    /**
     * Entry key.
     */
    @Getter
    private final String key;

    /**
     * Entry value.
     */
    @Getter
    @Setter
    private String value;

    /**
     * Is translated?
     */
    @Getter
    @Setter
    private boolean translated;

    /**
     * Is to be translated?
     */
    @Getter
    @Setter
    private boolean toTranslate;

    /**
     * Failed being translated?
     */
    @Getter
    @Setter
    private boolean failed;

    /**
     * Failing reason.
     */
    @Getter
    @Setter
    private String failingReason;

    /**
     * Execution time.
     */
    @Getter
    @Setter
    private long executionTime;

    /**
     * Confidence.
     */
    @Getter
    @Setter
    private float confidence;

    /**
     * Translation provider type.
     */
    @Getter
    @Setter
    private TranslationProviderType providerType;

    /**
     * Create a translation entry.
     * @param language Language.
     * @param key Key.
     * @param value Value.
     */
    @Builder(setterPrefix = "with")
    public TranslationEntry(final @NonNull LanguageType language, final @NonNull String key, final String value)
    {
        this.language = language;
        this.key = key;
        this.value = value;

        this.toTranslate = value == null || value.isEmpty();
    }

    @Override
    public int compareTo(ITranslationEntry o)
    {
        try
        {
            return Integer.valueOf(key).compareTo(Integer.valueOf(o.getKey()));
        }
        catch (NumberFormatException e)
        {
            return key.compareTo(o.getKey());
        }
    }
}
