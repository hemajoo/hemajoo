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
import com.hemajoo.i18n.translation.entry.ITranslationEntry;
import com.hemajoo.i18n.translation.exception.TranslationException;
import com.hemajoo.i18n.translation.type.TranslationProviderType;
import com.hemajoo.utility.string.StringExpander;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@NoArgsConstructor
public abstract class AbstractTranslationEngine implements ITranslationEngine
{
    private static final String HEADER_ACCEPT_TITLE = "Accept";
    private static final String HEADER_ACCEPT_VALUE = "application/json";
    private static final String VARIABLE_LANGUAGE_SOURCE = "sourceLanguage";
    private static final String VARIABLE_LANGUAGE_TARGET = "targetLanguage";

    private static final String VARIABLE_ENCODE_TEXT = "encodedText";

    /**
     * Translation provider type.
     */
    @Getter
    protected TranslationProviderType providerType;

    /**
     * Translation object.
     */
    @Getter
    protected ITranslation translation;

    /**
     * Translation provider url.
     */
    protected String providerUrl;

    /**
     * Http client.
     */
    protected CloseableHttpClient client = null;

    /**
     * Total execution time (expressed in milliseconds).
     */
    protected long totalExecutionTime = 0L;

    /**
     * Create an abstract translation engine.
     * @param providerType Translation provider type.
     * @param providerUrl Translation url API (specific to the provider).
     * @param translation Translation object.
     */
    public AbstractTranslationEngine(final @NonNull TranslationProviderType providerType, final @NonNull String providerUrl, final ITranslation translation)
    {
        this.providerType = providerType;
        this.translation = translation;
        this.providerUrl = providerUrl;

        client = HttpClientBuilder.create().build();
    }

    @Override
    public final void setTranslation(@NonNull ITranslation translation)
    {
        this.translation = translation;
    }

    @Override
    public String translateDirect(@NonNull LanguageType source, @NonNull LanguageType target, @NonNull String text) throws TranslationException
    {
        String url;
        HttpGet http;
        HttpResponse response;
        StatusLine result;
        Instant start = Instant.now();

        try
        {
            url = buildApiUrl(source, target, text);

            http = new HttpGet(url);
            http.setHeader( HEADER_ACCEPT_TITLE, HEADER_ACCEPT_VALUE);
            response = client.execute(new HttpGet(url));
            result = response.getStatusLine();

            if (result.getStatusCode() == HttpStatus.SC_OK)
            {
                LOGGER.trace(String.format("🌏 Direct translation from (%s) to (%s) took %d ms.", source.getLocale().toLanguageTag(),  target.getLocale().toLanguageTag(), Duration.between(start, Instant.now()).toMillis()));
                return processEntryResponse(response);
            }
            else
            {
                throw new TranslationException(response.getStatusLine().getReasonPhrase());
            }
        }
        catch (IOException e)
        {
            throw new TranslationException(e);
        }
    }

    @Override
    public void translate() throws TranslationException
    {
        totalExecutionTime = 0L;

        if (translation != null && translation.needTranslation())
        {
            for (ITranslationEntity entity : translation.getTargetEntities())
            {
                translate(entity);
            }
        }
    }

    @Override
    public void translate(final @NonNull ITranslationEntity entity) throws TranslationException
    {
        if (translation != null && translation.needTranslation())
        {
            for (ITranslationEntity element : translation.getTargetEntities())
            {
                if (element.getLanguage() == entity.getLanguage())
                {
                    LOGGER.debug(String.format(
                            "🌏 Entity of type: %s with %d entries submitted for translation from: %s to: %s using %s translation engine",
                            entity.getFileType(),
                            entity.getEntries().size(),
                            translation.getSourceEntity().getLanguage(),
                            entity.getLanguage(),
                            providerType));

                    processEntity(entity);

                    translation.addExecutionTime(entity.getExecutionTime());
                    LOGGER.debug(String.format("🌏 %s entries translated to language (%s) in %s ms", entity.getEntries().size(), entity.getLanguage().getLocale().toLanguageTag(), entity.getExecutionTime()));
                }

                LOGGER.debug(String.format("🌏 A total of: %s entries translated in %s ms", translation.countEntities(), translation.getExecutionTime()));
            }
        }
    }

    /**
     * Process an entity for translation.
     * @param target Translation entity.
     */
    private void processEntity(final @NonNull ITranslationEntity target) throws TranslationException
    {
        for (ITranslationEntry entry : target.getEntries())
        {
            processEntry(target, entry);
        }
    }

    /**
     * Process an entry for translation.
     * @param entity Translation entity.
     * @param entry Translation entry.
     */
    private void processEntry(final @NonNull ITranslationEntity entity, final @NonNull ITranslationEntry entry) throws TranslationException
    {
        String url;
        HttpGet http;
        HttpResponse response;
        StatusLine result;
        Instant start = Instant.now();

        try
        {
            url = buildApiUrl(translation.getSourceEntity().getLanguage(), entity.getLanguage(), translation.getSourceEntity().getEntry(entry.getKey()).getValue());
            http = new HttpGet(url);
            http.setHeader( HEADER_ACCEPT_TITLE, HEADER_ACCEPT_VALUE);
            response = client.execute(new HttpGet(url));
            result = response.getStatusLine();

            if (result.getStatusCode() == HttpStatus.SC_OK)
            {
                // Update the entry
                entry.setValue(processEntryResponse(response));
                entry.setTranslated(true);
                entry.setConfidence(1.0f);
                entry.setProviderType(providerType);
                entry.setExecutionTime(Duration.between(start, Instant.now()).toMillis());
                totalExecutionTime += entry.getExecutionTime();
                entity.addExecutionTime(entry.getExecutionTime());
                LOGGER.trace(String.format("🌏 Key: '%-30s' translated to language (%s) in %5d ms. Result is: %s", entry.getKey(), entry.getLanguage().getLocale().toLanguageTag(), entry.getExecutionTime(), entry.getValue()));
            }
            else
            {
                throw new TranslationException(response.getStatusLine().getReasonPhrase());
            }
        }
        catch (IOException e)
        {
            throw new TranslationException(e);
        }
    }

    @Override
    public String processEntryResponse(final @NonNull HttpResponse response) throws TranslationException
    {
        return getRawText(response).replace("[\"", "").replace("\"]", "");
    }

    /**
     * Return the raw text from the response.
     * @param response Response.
     * @return Raw text containing the translation.
     * @throws TranslationException
     */
    protected String getRawText(final @NonNull HttpResponse response) throws TranslationException
    {
        String responseString;

        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            responseString = out.toString();
            out.close();
        }
        catch (IOException e)
        {
            throw new TranslationException(e);
        }

        return responseString;
    }

    /**
     * Build the final url.
     * @param sourceLanguage Source language.
     * @param targetLanguage Target language.
     * @param text Text to translate.
     * @return Url.
     */
    private String buildApiUrl(final @NonNull LanguageType sourceLanguage, final @NonNull LanguageType targetLanguage, final @NonNull String text)
    {
        String url = StringExpander.expandByName(providerUrl, VARIABLE_LANGUAGE_SOURCE, sourceLanguage.getLocale().getLanguage());
        url = StringExpander.expandByName(url, VARIABLE_LANGUAGE_TARGET, targetLanguage.getLocale().getLanguage());
        url = StringExpander.expandByName(url, VARIABLE_ENCODE_TEXT, encodeText(text));

        return url;
    }

    /**
     * Encode a text.
     * @param text Text.
     * @return Encoded text.
     */
    private String encodeText(final String text)
    {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}
