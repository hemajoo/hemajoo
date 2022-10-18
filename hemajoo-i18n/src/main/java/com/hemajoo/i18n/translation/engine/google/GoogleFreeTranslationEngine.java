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
package com.hemajoo.i18n.translation.engine.google;

import com.hemajoo.i18n.localization.data.LanguageType;
import com.hemajoo.i18n.translation.ITranslation;
import com.hemajoo.i18n.translation.engine.AbstractTranslationEngine;
import com.hemajoo.i18n.translation.exception.TranslationException;
import com.hemajoo.i18n.translation.type.TranslationProviderType;
import lombok.NonNull;
import org.apache.http.HttpResponse;

public final class GoogleFreeTranslationEngine extends AbstractTranslationEngine
{
    /**
     * Google's free translation API.
     * <br><br>
     * Contains variable that will be expanded/substituted when API is invoked.
     */
    private static final String GOOGLE_TRANSLATE_API = "https://translate.googleapis.com/translate_a/t?client=dict-chrome-ex&sl=${sourceLanguage}&tl=${targetLanguage}&dt=t&q=${encodedText}";

    public GoogleFreeTranslationEngine()
    {
        super(TranslationProviderType.GOOGLE_FREE, GOOGLE_TRANSLATE_API, null);
    }

    public GoogleFreeTranslationEngine(final @NonNull ITranslation translation)
    {
        super(TranslationProviderType.GOOGLE_FREE, GOOGLE_TRANSLATE_API, translation);
    }

    @Override
    public void translate() throws TranslationException
    {
        super.translate();
    }

    @Override
    public String extractTranslation(final @NonNull HttpResponse response) throws TranslationException
    {
        return getRawText(response).replace("[\"", "").replace("\"]", "");
    }

    @Override
    public String translateDirect(@NonNull LanguageType source, @NonNull LanguageType target, @NonNull String text) throws TranslationException
    {
        return super.translateDirect(source, target, text);
    }
}
