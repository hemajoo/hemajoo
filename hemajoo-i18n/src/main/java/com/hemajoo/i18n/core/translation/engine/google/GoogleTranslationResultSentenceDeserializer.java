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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.hemajoo.i18n.core.translation.result.ITranslationResultSentence;

import java.lang.reflect.Type;

/**
 * A <b>Google</b> translation result sentence deserializer to use when JSON encounters a {@link ITranslationResultSentence} interface.
 * It then indicates which concrete implementation to use to process the de-serialization.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class GoogleTranslationResultSentenceDeserializer implements JsonDeserializer<ITranslationResultSentence>
{
    @Override
    public ITranslationResultSentence deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    {
        return context.deserialize(json, GoogleTranslationResultSentence.class);
    }
}
