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
package com.hemajoo.i18n.core.translation.engine.google;

import com.hemajoo.i18n.core.MemoryResourceBundle;
import com.hemajoo.i18n.core.localization.data.LanguageType;
import com.hemajoo.i18n.core.translation.request.TranslationRequest;
import lombok.Builder;
import lombok.NonNull;

import java.util.ResourceBundle;

/**
 * A <b>Google</b> translation request.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class GoogleTranslationRequest
{
    private TranslationRequest translationRequest;

    @Builder(setterPrefix = "with")
    public GoogleTranslationRequest(final @NonNull ResourceBundle sourceResourceBundle, final @NonNull ResourceBundle targetResourceBundle)
    {
        translationRequest = new TranslationRequest();
        translationRequest.setSourceLanguage(LanguageType.from(sourceResourceBundle.getLocale()));
        translationRequest.setTargetLanguage(LanguageType.from(targetResourceBundle.getLocale()));
        translationRequest.setSourceResourceBundle(new MemoryResourceBundle(sourceResourceBundle, LanguageType.from(sourceResourceBundle.getLocale())));
        translationRequest.setTargetResourceBundle(new MemoryResourceBundle(targetResourceBundle, LanguageType.from(targetResourceBundle.getLocale())));
    }
}
