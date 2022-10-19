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
import com.hemajoo.i18n.translation.engine.azure.AzureTranslationEngine;
import com.hemajoo.i18n.translation.engine.google.GoogleFreeTranslationEngine;
import com.hemajoo.i18n.translation.engine.google.GoogleTranslationEngine;
import com.hemajoo.i18n.translation.engine.ibm.IbmTranslationEngine;
import com.hemajoo.i18n.translation.entity.ITranslationEntity;
import com.hemajoo.i18n.translation.exception.TranslationException;
import com.hemajoo.i18n.translation.type.TranslationProviderType;
import lombok.Getter;
import lombok.NonNull;
import org.apache.http.HttpResponse;

public final class TranslationEngine implements ITranslationEngine
{
    @Getter
    private final ITranslationEngine engine;

    public TranslationEngine(final @NonNull TranslationProviderType provider, final @NonNull ITranslation translation) throws TranslationException
    {
        switch (provider)
        {
            case GOOGLE_FREE:
                engine = new GoogleFreeTranslationEngine(translation);
                break;

            case GOOGLE:
                engine = new GoogleTranslationEngine(translation);
                break;

            case IBM:
                engine = new IbmTranslationEngine(translation);
                break;

            case AZURE:
                engine = new AzureTranslationEngine(translation);
                break;

            default:
                throw new TranslationException(String.format("Unsupported translation provider: %s!", provider));
        }
    }

    @Override
    public void setTranslation(final @NonNull ITranslation translation)
    {
        engine.setTranslation(translation);
    }

    @Override
    public String processEntryResponse(@NonNull HttpResponse response) throws TranslationException
    {
        return engine.processEntryResponse(response);
    }

    @Override
    public void translate() throws TranslationException
    {
        engine.translate();
    }

    @Override
    public void translate(@NonNull ITranslationEntity entity) throws TranslationException
    {
        engine.translate(entity);
    }

    @Override
    public String translateDirect(@NonNull LanguageType source, @NonNull LanguageType target, @NonNull String text) throws TranslationException
    {
        return null;
    }
}
