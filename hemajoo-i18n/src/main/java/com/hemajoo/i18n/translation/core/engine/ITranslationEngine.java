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
package com.hemajoo.i18n.translation.core.engine;

import com.hemajoo.i18n.core.localization.data.LanguageType;
import com.hemajoo.i18n.translation.core.ITranslation;
import com.hemajoo.i18n.translation.core.TranslationException;
import lombok.NonNull;
import org.apache.http.HttpResponse;

public interface ITranslationEngine
{
    /**
     * Set a translation object.
     * @param translation Translation object.
     */
    void setTranslation(final @NonNull ITranslation translation);

    /**
     * Extract the translated text from the response.
     * @param response Response.
     * @return Translated text.
     * @throws TranslationException Thrown in case an error occurred while trying to extract the response.
     */
    String extractTranslation(final @NonNull HttpResponse response) throws TranslationException;

    /**
     * Translate.
     * @throws TranslationException Thrown in case an error occurred while trying to translate some text.
     */
    void translate() throws TranslationException;

    String translateDirect(@NonNull LanguageType source, @NonNull LanguageType target, @NonNull String text) throws TranslationException;
}
