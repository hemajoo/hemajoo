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
package com.hemajoo.i18n.translation.engine;

import com.hemajoo.i18n.localization.data.LanguageType;
import com.hemajoo.i18n.translation.ITranslation;
import com.hemajoo.i18n.translation.entity.ITranslationEntity;
import com.hemajoo.i18n.translation.exception.TranslationException;
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
     * Process the raw response after an entry has been translated.
     * @param response Response.
     * @return Translated text.
     * @throws TranslationException Thrown in case an error occurred while trying to extract the response.
     */
    String processEntryResponse(final @NonNull HttpResponse response) throws TranslationException;

    /**
     * Translate the {@link ITranslation} contained instance.
     * @throws TranslationException Thrown in case an error occurred while trying to translate some text.
     */
    void translate() throws TranslationException;

    /**
     * Translate a given entity.
     * @param entity Entity to translate.
     * @throws TranslationException Thrown in case an error occurred while trying to translate an entity.
     */
    void translate(final @NonNull ITranslationEntity entity) throws TranslationException;

    /**
     * Directly translate some text.
     * @param source Source language.
     * @param target Target language.
     * @param text Text.
     * @return Translated text.
     * @throws TranslationException Thrown in case an error occurred while trying to translate some text.
     */
    String translateDirect(@NonNull LanguageType source, @NonNull LanguageType target, @NonNull String text) throws TranslationException;
}
