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
package com.hemajoo.i18n.core.translation.request;

import com.hemajoo.i18n.core.translation.result.ITranslationResult;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Provides the basic behavior of a generic translation request entry.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * see {@link ITranslationRequest}
 */
public interface ITranslationRequestEntry
{
    /**
     * Retrieve the key for this translation request entry.
     * @return The entry key.
     */
    String getKey();

    /**
     * Retrieve the source text for this translation request entry.
     * @return The source text.
     */
    String getSource();

    /**
     * Retrieve the translated (target) text for this translation request entry.
     * @return The translated (target) text.
     */
    String getTranslation();

    /**
     * Set the translation text for this translation request entry.
     * @param translation Translation text to set.
     */
    void setTranslation(final @NonNull String translation);

    /**
     * Set the translation result for this translation request entry.
     * @param result Translation result to set.
     */
    void setResult(final @NonNull ITranslationResult result);

    /**
     * Retrieve the translation result for this translation request entry.
     * @return Translation result.
     */
    ITranslationResult getResult();

    /**
     * Return if this translation request entry requires a translation.
     * @return True if this translation request entry requires a translation, false otherwise.
     */
    boolean requireTranslation();

    /**
     * Return the time stamp of the translation.
     * @return Translation time stamp.
     */
    LocalDateTime getTranslationTimeStamp();
}
