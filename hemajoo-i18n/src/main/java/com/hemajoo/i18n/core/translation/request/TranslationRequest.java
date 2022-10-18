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
package com.hemajoo.i18n.core.translation.request;

import com.hemajoo.i18n.core.MemoryResourceBundle;
import com.hemajoo.i18n.core.localization.data.LanguageType;
import com.hemajoo.i18n.core.translation.result.ITranslationResult;
import com.hemajoo.i18n.translation.core.TranslationException;
import com.hemajoo.i18n.translation.core.type.TranslationFileType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A <b>translation request</b> is used to be able to submit multiple entries to be translated at once.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
public class TranslationRequest implements ITranslationRequest
{
    private final List<ITranslationRequestEntry> entries = new ArrayList<>();

    /**
     * Source property file entries.
     */
    @Getter
    private final Map<String, String> sources = new HashMap<>();

    /**
     * Target property file entries.
     */
    @Getter
    private final Map<String, String> targets = new HashMap<>();

    /**
     * Compact mode.
     */
    @Getter
    private boolean compactMode = false;

    /**
     * Source language.
     */
    @Getter
    @Setter
    private LanguageType sourceLanguage;

    /**
     * Target language.
     */
    @Getter
    @Setter
    private LanguageType targetLanguage;

    @Getter
    private TranslationFileType documentType;

    public TranslationRequest(final @NonNull MemoryResourceBundle sourceResourceBundle, final MemoryResourceBundle targetResourceBundle)
    {
        documentType = TranslationFileType.RESOURCE_BUNDLE;
        setSourceLanguage(sourceResourceBundle.getLanguage());
        setTargetLanguage(targetResourceBundle.getLanguage());
        setSourceResourceBundle(sourceResourceBundle);
        setTargetResourceBundle(targetResourceBundle);
    }

    public TranslationRequest(final @NonNull LanguageType sourceLanguage, final @NonNull LanguageType targetLanguage, final @NonNull String textDocument)
    {
        documentType = TranslationFileType.TEXT;
        setSourceLanguage(sourceLanguage);
        setTargetLanguage(targetLanguage);
        setSourceProperties(textDocument);
        setTargetProperties(null); // Should map with source
    }

    public final List<ITranslationRequestEntry> getEntries()
    {
        return entries;
    }

    @Override
    public final String getQuery()
    {
        compactMode = true;

        StringBuilder query = new StringBuilder("[");

        for (ITranslationRequestEntry entry : entries)
        {
            if (entry.requireTranslation())
            {
                query.append(entry.getSource()).append(",");
            }
        }

        // Remove the last "," character.
        query.replace(query.length() -1, query.length(), "");
        query.append("]");

        return query.toString();
    }

    @Override
    public final String getQuery(final @NonNull String key) throws TranslationException
    {
        compactMode = false;

        ITranslationRequestEntry entry = getEntry(key);

        if (!entry.requireTranslation())
        {
            throw new TranslationException(String.format("Entry: '%s' is not supposed to be translated!", key));
        }

        return entry.getSource();
    }

    @Override
    public final void setSourceProperties(final @NonNull String content)
    {
        String[] entryParts;
        int index = 0;

        String[] parts = content.split("\n");
        if (parts.length > 1)
        {
            for (String s : parts)
            {
                entryParts = s.split("=");
                if (entryParts.length == 1) // Not a resource bundle, just a text file
                {
                    if (documentType == TranslationFileType.PROPERTIES || documentType == TranslationFileType.RESOURCE_BUNDLE)
                    {
                        sources.put(entryParts[0], "");
                    }
                    else
                    {
                        sources.put(Integer.toString(index++), entryParts[0]);
                    }
                }
                else if (entryParts.length > 1) // A resource bundle or a properties file
                {
                    sources.put(entryParts[0], entryParts[1]);
                }
            }
        }
    }

    @Override
    public final int getCount()
    {
        return (int) entries.stream()
                .filter(ITranslationRequestEntry::requireTranslation)
                .count();
    }

    @Override
    public final void setTargetProperties(final String content)
    {
        String[] entryParts;

        if (content == null) // A text file to convert
        {
            for (String key : sources.keySet())
            {
                targets.put(key, null);
            }
        }
        else
        {
            String[] parts = content.split("\n");
            if (parts.length > 1)
            {
                for (String s : parts)
                {
                    entryParts = s.split("=");
                    if (entryParts.length > 0)
                    {
                        targets.put(entryParts[0], entryParts.length == 2 ? entryParts[1] : "");
                    }
                }
            }
        }
    }

    @Override
    public Object getTranslationResult() throws TranslationException
    {
        switch (documentType)
        {
            case RESOURCE_BUNDLE:
                break;

            case PROPERTIES:
                break;

            case TEXT:
                StringBuilder builder = new StringBuilder();
                List<Integer> keys = targets.keySet().stream().map(Integer::valueOf).sorted().toList();
                for (Integer index : keys)
                {
                    builder.append(targets.get(index.toString())).append("\n");
                }
                return builder.toString();

            case FILE_TEXT:
                break;
        }

        throw new TranslationException("Unknown source document type!");
    }

    @Override
    public final void updateEntry(final @NonNull ITranslationRequestEntry entry, final @NonNull ITranslationResult result)
    {
        targets.put(entry.getKey(), result.getSentences().get(0).getTranslation());
    }

    @Override
    public final ITranslationRequestEntry findEntryKeyFor(@NonNull String value) throws TranslationException
    {
        for (ITranslationRequestEntry entry : entries)
        {
            if (entry.getSource().equals(value))
            {
                return entry;
            }
        }

        throw new TranslationException(String.format("No translation request entry found for source value: '%s'", value));
    }

    @Override
    public final ITranslationRequestEntry getEntry(final @NonNull String key) throws TranslationException
    {
        for (ITranslationRequestEntry entry : entries)
        {
            if (entry.getKey().equals(key))
            {
                return entry;
            }
        }

        throw new TranslationException(String.format("No entry with key: '%s' found in translation request!", key));
    }

    @Override
    public final String getTarget(String key)
    {
        return targets.get(key);
    }

    @Override
    public final void setCompactMode(final boolean mode)
    {
        this.compactMode = mode;
    }

    public void setSourceResourceBundle(final @NonNull MemoryResourceBundle resourceBundle)
    {
        setSourceProperties(convertResourceBundleToString(resourceBundle));
    }

    public void setTargetResourceBundle(final @NonNull MemoryResourceBundle resourceBundle)
    {
        setTargetProperties(convertResourceBundleToString(resourceBundle));
    }

    /**
     * Convert a resource bundle to a string.
     * @param resourceBundle Resource bundle.
     * @return String.
     */
    private String convertResourceBundleToString(final @NonNull MemoryResourceBundle resourceBundle)
    {
        StringBuilder builder = new StringBuilder();

        List<String> keys = resourceBundle.getKeys();
        for (String key : keys)
        {
            builder.append(key).append("=").append(resourceBundle.getValue(key)).append("\n");
        }

        return builder.toString();
    }
}
