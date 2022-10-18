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
import lombok.NonNull;

import java.io.Serializable;

/**
 * Represent a translation entry of a {@link TranslationEntity}.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface ITranslationEntry extends Serializable, Comparable<ITranslationEntry>
{
    /**
     * Return the entry key.
     * @return Key.
     */
    String getKey();

    /**
     * Return the entry value.
     * @return Value.
     */
    String getValue();

    /**
     * Set the entry value.
     * @param value Value.
     */
    void setValue(final @NonNull String value);

    /**
     * Return the entry language.
     * @return Language.
     */
    LanguageType getLanguage();

    /**
     * Return if this translation entry has been translated ?
     * @return <b>True</b> if this translation entry has been translated, <b>false</b> otherwise.
     */
    boolean isTranslated();

    /**
     * Set if this translation entry is translated.
     * @param translated <b>True</b> if this translation entry is translated, <b>false</b> otherwise.
     */
    void setTranslated(final boolean translated);

    /**
     * Return if this translation entry is to be translated?
     * @return <b>True</b> if this translation entry is to be translated, <b>false</b> otherwise.
     */
    boolean isToTranslate();

    /**
     * Set if this translation entry is to be translated.
     * @param translate <b>True</b> if this translation entry is to be translated, <b>false</b> otherwise.
     */
    void setToTranslate(final boolean translate);

    /**
     * Return if this translation entry has failed being translated ?
     * @return <b>True</b> if this translation entry has failed being translated, <b>false</b> otherwise.
     */
    boolean isFailed();

    /**
     * Set if this translation entry has failed.
     * @param failed <b>True</b> if this translation entry has failed being translated, <b>false</b> otherwise.
     */
    void setFailed(final boolean failed);

    /**
     * Set the failing reason.
     * @param reason Failing reason.
     */
    void setFailingReason(final @NonNull String reason);

    /**
     * Return the failing reason explaining why the translation has failed.
     * @return Failing reason.
     */
    String getFailingReason();

    /**
     * Set the execution time (expressed in milliseconds).
     * @param executionTime Execution time.
     */
    void setExecutionTime(final long executionTime);

    /**
     * Return the time used to translate this translation entry (expressed in milliseconds).
     * @return Execution time.
     */
    long getExecutionTime();

    /**
     * Set the translation confidence.
     * @param confidence Confidence.
     */
    void setConfidence(final float confidence);

    /**
     * Return the translation confidence.
     * @return Confidence.
     */
    float getConfidence();

    /**
     * Set the translation provider type.
     * @param provider Translation provider type.
     */
    void setProviderType(final TranslationProviderType provider);

    /**
     * Return the translation provider type.
     * @return {@link TranslationProviderType} representing the translation provider (engine) having realized the translation.
     */
    TranslationProviderType getProviderType();
}
