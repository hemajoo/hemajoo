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

import com.hemajoo.i18n.core.localization.data.LanguageType;
import com.hemajoo.i18n.core.translation.ITranslator;
import com.hemajoo.i18n.core.translation.process.ITranslationProcess;
import com.hemajoo.i18n.core.translation.process.TranslationProcess;
import com.hemajoo.i18n.core.translation.request.ITranslationRequest;
import com.hemajoo.i18n.core.translation.request.ITranslationRequestEntry;
import com.hemajoo.i18n.core.translation.result.ITranslationResult;
import com.hemajoo.i18n.translation.core.TranslationException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

/**
 * A <b>Google free translator</b> used to translate resources from a language to another one making use of the free Google translation APIs.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Slf4j
@NoArgsConstructor
public final class GoogleFreeTranslator implements ITranslator
{
    /**
     * Google free translation API.
     */
    private static final String GOOGLE_TRANSLATE_API = "https://translate.googleapis.com/translate_a/t?client=dict-chrome-ex&sl=";

    /**
     * Translation result.
     */
    @Getter
    @Setter
    private ITranslationResult translationResult;

    /**
     * Translation process.
     */
    @Getter
    @Setter
    private ITranslationProcess translationProcess;

    /**
     * Http client.
     */
    private CloseableHttpClient httpClient = null;


    /**
     * Create a <b>Google free translator</b> given a translation request.
     * @param request Translation request.
     */
    @Builder(setterPrefix = "with")
    public GoogleFreeTranslator(final @NonNull ITranslationRequest request)
    {
        translationProcess = new TranslationProcess();
        translationProcess.setRequest(request);

        httpClient = HttpClientBuilder.create().build();
    }

    /**
     * Translate some text.
     * @param sourceLanguage Source language.
     * @param targetLanguage Target language.
     * @param text Text to translate.
     * @return Translated text.
     * @throws TranslationException Thrown to indicate an error occurred while trying to translate some text.
     */
    public static String translate(final @NonNull LanguageType sourceLanguage, final @NonNull LanguageType targetLanguage, final @NonNull String text) throws TranslationException
    {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build())
        {
            String url = buildUrl(text, sourceLanguage.getLocale().getLanguage(), targetLanguage.getLocale().getLanguage());
            HttpGet http = new HttpGet(url);
            http.setHeader( "Accept", "application/json" );
            HttpResponse response = httpClient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK)
            {
                return getResponseString(response);
            }
            else
            {
                throw new TranslationException(response.getStatusLine().getReasonPhrase());
            }
        }
        catch (Exception e)
        {
            throw new TranslationException(e);
        }
    }

    @Override
    public void translate() throws TranslationException
    {
        Instant start = Instant.now();
        Instant finish;

        if (translationProcess != null)
        {
            for (ITranslationRequestEntry requestEntry : translationProcess.getRequest().getEntries())
            {
                translateEach(httpClient, requestEntry);
            }

            finish = Instant.now();
            translationProcess.setElapsed(Duration.between(start, finish).toMillis());
        }
    }

    @Override
    public void close() throws TranslationException
    {
        if (httpClient != null)
        {
            try
            {
                translationProcess = null;
                translationResult = null;
                httpClient.close();
                httpClient = null;
            }
            catch (IOException e)
            {
                throw new TranslationException(e);
            }
        }
    }

    /**
     * Translate a translation request entry.
     * @param entry Translation request entry.
     * @throws TranslationException Thrown to indicate an error occurred while trying to translate a request entry.
     */
    private void translateEach(final @NonNull HttpClient httpClient, final @NonNull ITranslationRequestEntry entry) throws TranslationException
    {
        String url;
        HttpGet http;
        HttpResponse response;
        StatusLine statusLine;

        try
        {
            if (entry.requireTranslation())
            {
                url = buildUrl(
                        entry.getSource(),
                        translationProcess.getRequest().getSourceLanguage().getLocale().getLanguage(),
                        translationProcess.getRequest().getTargetLanguage().getLocale().getLanguage());

                Instant start = Instant.now();

                http = new HttpGet(url);
                http.setHeader( "Accept", "application/json" );
                response = httpClient.execute(new HttpGet(url));
                statusLine = response.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK)
                {
                    translationProcess.updateEntry(entry, new GoogleTranslationResult(response));
                    LOGGER.trace(String.format("🌏Translation from %s (%s) to %s (%s) took %s ms",
                            translationProcess.getRequest().getSourceLanguage(),
                            translationProcess.getRequest().getSourceLanguage().getLocale().getLanguage(),
                            translationProcess.getRequest().getTargetLanguage(),
                            translationProcess.getRequest().getTargetLanguage().getLocale().getLanguage(),
                            Duration.between(start, Instant.now()).toMillis()));
                }
                else
                {
                    throw new TranslationException(response.getStatusLine().getReasonPhrase());
                }
            }
        }
        catch (IOException e)
        {
            throw new TranslationException(e);
        }

    }

    /**
     * Build the url to be used for the translation.
     * @param text Text to be translated.
     * @param sourceLanguage Source language.
     * @param targetLanguage Target language.
     * @return Translation URL.
     */
    private static String buildUrl(String text, String sourceLanguage, String targetLanguage)
    {
        String textEncoded = URLEncoder.encode(text, StandardCharsets.UTF_8);
        return GOOGLE_TRANSLATE_API + sourceLanguage + "&tl=" + targetLanguage + "&dt=t&q=" + textEncoded;
    }

    /**
     * Extract the response string from the received <b>http</b> response.
     * @param response Http response.
     * @return Response string.
     * @throws IOException Thrown to indicate an error occurred while trying to extracts the response string.
     */
    private static String getResponseString(HttpResponse response) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);
        String responseString = out.toString();
        out.close();

        return responseString.replace("[\"", "").replace("\"]", "");
    }
}
