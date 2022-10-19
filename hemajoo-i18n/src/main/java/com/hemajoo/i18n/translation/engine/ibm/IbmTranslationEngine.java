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
package com.hemajoo.i18n.translation.engine.ibm;

import com.hemajoo.commons.exception.NotYetImplementedException;
import com.hemajoo.i18n.localization.data.LanguageType;
import com.hemajoo.i18n.translation.ITranslation;
import com.hemajoo.i18n.translation.engine.AbstractTranslationEngine;
import com.hemajoo.i18n.translation.entity.ITranslationEntity;
import com.hemajoo.i18n.translation.exception.TranslationException;
import com.hemajoo.i18n.translation.type.TranslationProviderType;
import lombok.NonNull;
import org.apache.http.HttpResponse;

public final class IbmTranslationEngine extends AbstractTranslationEngine
{
    /**
     * IBM's translation API.
     * <br><br>
     * Contain variables to be expanded/substituted when API is invoked.
     */
    private static final String IBM_TRANSLATE_API = "???";

    public IbmTranslationEngine()
    {
        super(TranslationProviderType.IBM, IBM_TRANSLATE_API, null);
    }

    public IbmTranslationEngine(ITranslation translation)
    {
        super(TranslationProviderType.IBM, IBM_TRANSLATE_API, translation);
    }

    @Override
    public void translate() throws TranslationException
    {
        throw new NotYetImplementedException();
    }

    @Override
    public void translate(final @NonNull ITranslationEntity entity) throws TranslationException
    {
        super.translate(entity);
    }

    @Override
    public String processEntryResponse(final @NonNull HttpResponse response) throws TranslationException
    {
        return getRawText(response).replace("[\"", "").replace("\"]", ""); // Google Free translation API format!
    }

    @Override
    public String translateDirect(@NonNull LanguageType source, @NonNull LanguageType target, @NonNull String text) throws TranslationException
    {
        return null;
    }
}
