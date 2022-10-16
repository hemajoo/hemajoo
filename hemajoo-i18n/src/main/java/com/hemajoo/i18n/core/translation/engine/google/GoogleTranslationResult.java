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

import com.hemajoo.i18n.core.translation.TranslationException;
import com.hemajoo.i18n.core.translation.result.ITranslationResult;
import com.hemajoo.i18n.core.translation.result.ITranslationResultSentence;
import lombok.*;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A <b>Google</b> translation result.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
public final class GoogleTranslationResult implements ITranslationResult
{
    /**
     * Result translation of sentences.
     */
    @Getter
    @Setter
    private List<ITranslationResultSentence> sentences = new ArrayList<>();

    /**
     * Source of the translation.
     */
    @Getter
    @Setter
    private String source;

    /**
     * Confidence factor of the translation.
     */
    @Getter
    @Setter
    private double confidence;

    /**
     * Creates a new Google translation result.
     */
    public GoogleTranslationResult(final @NonNull HttpResponse response) throws TranslationException
    {
        StatusLine statusLine = response.getStatusLine();

        if (statusLine.getStatusCode() == HttpStatus.SC_OK)
        {
            sentences.add(new GoogleTranslationResultSentence(getResponseString(response), "", 0));
        }
    }

    /**
     * Return the translation result.
     * @return Translation result.
     */
    public final String getTranslation()
    {
        String result = sentences.stream()
                .map(ITranslationResultSentence::getTranslation)
                .collect( Collectors.joining(""));

        return result.replace("null", "");
    }

    @Override
    public TranslationProviderType getProviderType()
    {
        return TranslationProviderType.GOOGLE_FREE_TRANSLATE_API;
    }

    /**
     * Extracts the response string from the received HTTP response.
     * @param response HTTP response.
     * @return Response string.
     * @throws TranslationException Thrown to indicate an error occurred while trying to extract the response string.
     */
    private String getResponseString(HttpResponse response) throws TranslationException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try
        {
            response.getEntity().writeTo(out);
            String responseString = out.toString();
            out.close();
            return responseString.replace("[\"", "").replace("\"]", "");
        }
        catch (IOException e)
        {
            throw new TranslationException(e);
        }
    }
}
