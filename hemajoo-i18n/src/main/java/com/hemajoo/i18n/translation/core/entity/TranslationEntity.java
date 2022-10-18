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
package com.hemajoo.i18n.translation.core.entity;

import com.hemajoo.i18n.core.localization.data.LanguageType;
import com.hemajoo.i18n.translation.core.TranslationException;
import com.hemajoo.i18n.translation.core.entry.ITranslationEntry;
import com.hemajoo.i18n.translation.core.entry.TranslationEntry;
import com.hemajoo.i18n.translation.core.type.TranslationEntityType;
import com.hemajoo.i18n.translation.core.type.TranslationFileType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.File;
import java.util.*;

/**
 * Represent an entity being part of a translation process, it can be a <b>source</b> or a <b>target</b> translation entity containing entries to be translated.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class TranslationEntity implements ITranslationEntity
{
    /**
     * Line feed constant.
     */
    public static final String LINE_FEED = "\n";

    /**
     * Language.
     */
    @Getter
    private LanguageType language;

    /**
     * Translation file type.
     */
    @Getter
    private TranslationFileType fileType;

    /**
     * Translation object.
     */
    @Getter
    private final Object object;

    /**
     * Translation source entity.
     */
    @Getter
    private final ITranslationEntity source;

    /**
     * Translation entity type.
     */
    @Getter
    private TranslationEntityType type;

    /**
     * Does this entry has been translated?
     */
    @Getter
    private boolean translated;

    /**
     * Does this entry has failed being translated?
     */
    @Getter
    private boolean failed;

    /**
     * Is this entry to be translated?
     */
    @Getter
    @Setter
    private boolean toTranslate;

    @Getter
    private long executionTime;

    /**
     * Collection of translation entries.
     */
    private final Map<String, ITranslationEntry> entries = new HashMap<>();

    /**
     * Create a translation entity.
     * @param language Language.
     * @param entityType {@link TranslationEntityType} defining if it's a <b>source</b> or <b>target</b> translation entity.
     * @param object Object representing the source of this translation entity, if one exist.
     * @param source The <b>source</b> translation entity only if this translation entity is a <b>target</b> translation entity.
     * @throws TranslationException Thrown if an error occurred with a translation entity.
     */
    @Builder(setterPrefix = "with")
    public TranslationEntity(LanguageType language, final @NonNull TranslationEntityType entityType, final Object object, final ITranslationEntity source) throws TranslationException
    {
        this.language = language;
        this.object = object;
        this.type = entityType;
        this.source = source;
        this.executionTime = 0L;

        if (entityType == TranslationEntityType.SOURCE)
        {
            toTranslate = false;
            if (source != null)
            {
                throw new TranslationException("❗A translation entity of type: SOURCE cannot have a source set!");
            }
        }

        if (entityType == TranslationEntityType.TARGET && source == null)
        {
            throw new TranslationException("❗A translation entity of type: TARGET must have a source set!");
        }

        if (object != null)
        {
            if (object instanceof ResourceBundle resourceBundle)
            {
                fileType = TranslationFileType.RESOURCE_BUNDLE;
                this.language = LanguageType.from(resourceBundle.getLocale().getLanguage());
                createResourceBundleEntries();
            }
            else if (object instanceof Properties)
            {
                fileType = TranslationFileType.PROPERTIES;
                if (language == null)
                {
                    throw new TranslationException("❗Language must be provided!");
                }
                createPropertiesEntries();
            }
            else if (object instanceof String)
            {
                fileType = TranslationFileType.TEXT;
                if (language == null)
                {
                    throw new TranslationException("❗Language must be provided!");
                }
                createTextEntries();
            }
            else if (object instanceof File)
            {
                fileType = TranslationFileType.FILE_TEXT;
                if (language == null)
                {
                    throw new TranslationException("❗Language must be provided!");
                }
                //createTextFileEntries();
            }
            else
            {
                throw new TranslationException(String.format("❗️File of type: %s is not handled!", object.getClass().getName()));
            }

            if (entityType == TranslationEntityType.TARGET)
            {
                checkObjectTypes(source, object);
            }
        }
        else
        {
            if (type != TranslationEntityType.SOURCE)
            {
                // Create the target entries based on source entries
                if (source.getObject() instanceof ResourceBundle resourceBundle)
                {
                    fileType = TranslationFileType.RESOURCE_BUNDLE;
                    this.language = LanguageType.from(resourceBundle.getLocale().getLanguage());
                    createResourceBundleEntries();
                }
                else if (source.getObject() instanceof Properties)
                {
                    fileType = TranslationFileType.PROPERTIES;
                    if (language == null)
                    {
                        throw new TranslationException("❗Language must be provided!");
                    }
                    createPropertiesEntries();
                }
                else if (source.getObject() instanceof String)
                {
                    fileType = TranslationFileType.TEXT;
                    if (language == null)
                    {
                        throw new TranslationException("❗Language must be provided!");
                    }
                    createTextEntries();
                }
                else if (source.getObject() instanceof File)
                {
                    fileType = TranslationFileType.FILE_TEXT;
                    if (language == null)
                    {
                        throw new TranslationException("❗Language must be provided!");
                    }
                    //createTextFileEntries();
                }
                else
                {
                    throw new TranslationException(String.format("❗️File of type: %s is not handled!", source.getObject().getClass().getName()));
                }
            }

            if (entityType == TranslationEntityType.TARGET)
            {
                if (object != null)
                {
                    checkObjectTypes(source, object);
                }
            }
        }

        if (this.language == null)
        {
            throw new TranslationException("❗Unable to determine language!");
        }
    }

    @Override
    public final void addExecutionTime(final long time)
    {
        if (time > 0)
        {
            this.executionTime += time;
        }
    }

    /**
     * Create the {@link TranslationEntry} entities for an object of type {@link ResourceBundle}.
     */
    private void createResourceBundleEntries()
    {
        if (this.type == TranslationEntityType.SOURCE)
        {
            ResourceBundle bundle = (ResourceBundle) this.object;
            for (String key : bundle.keySet())
            {
                this.entries.put(key, TranslationEntry.builder()
                        .withKey(key)
                        .withLanguage(language)
                        .withValue(bundle.getString(key))
                        .build());
            }
        }
        else if (this.type == TranslationEntityType.TARGET)
        {
            ResourceBundle sourceObject = (ResourceBundle) this.source.getObject();
            ResourceBundle targetObject = (ResourceBundle) this.object;

            for (String key : sourceObject.keySet())
            {
                if (targetObject.containsKey(key))
                {
                    this.entries.put(key, TranslationEntry.builder()
                            .withKey(key)
                            .withLanguage(language)
                            .withValue(targetObject.getString(key))
                            .build());
                }
                else
                {
                    // Create the missing entry
                    this.entries.put(key, TranslationEntry.builder()
                            .withKey(key)
                            .withLanguage(language)
                            .build());
                }
            }
        }
    }

    /**
     * Create the {@link TranslationEntry} entities for an object of type {@link Properties}.
     */
    @SuppressWarnings("java:S2864")
    private void createPropertiesEntries()
    {
        if (this.type == TranslationEntityType.SOURCE)
        {
            Properties properties = (Properties) this.object;
            for (Object key : properties.keySet())
            {
                this.entries.put((String) key, TranslationEntry.builder()
                        .withKey((String) key)
                        .withLanguage(language)
                        .withValue((String) properties.get(key))
                        .build());
            }
        }
        else if (this.type == TranslationEntityType.TARGET)
        {
            Properties sourceObject = (Properties) this.source.getObject();

            if (object != null)
            {
                Properties targetObject = (Properties) this.object;

                for (Object key : sourceObject.keySet())
                {
                    if (targetObject.containsKey(key))
                    {
                        this.entries.put((String) key, TranslationEntry.builder()
                                .withKey((String) key)
                                .withLanguage(language)
                                .withValue((String) targetObject.get(key))
                                .build());
                    }
                    else
                    {
                        // Create the missing entry
                        this.entries.put((String) key, TranslationEntry.builder()
                                .withKey((String) key)
                                .withLanguage(language)
                                .build());
                    }
                }
            }
            else
            {
                // Create missing entries (all)
                for (Object key : sourceObject.keySet())
                {
                    this.entries.put((String) key, TranslationEntry.builder()
                            .withKey((String) key)
                            .withLanguage(language)
                            .build());
                }
            }
        }
    }

    /**
     * Create the {@link TranslationEntry} entities for an object of type {@link String}.
     * <br><br>
     * An entry is created based on the '\n' (line feed) character.
     */
    @SuppressWarnings("java:S2864")
    private void createTextEntries()
    {
        int count = 0;
        String key;

        if (this.type == TranslationEntityType.SOURCE)
        {
            String text = (String) this.object;

            List<String> values = List.of(text.split(LINE_FEED));
            for (String value : values)
            {
                key = Integer.toString(count++);
                this.entries.put(key, TranslationEntry.builder()
                        .withKey(key)
                        .withLanguage(language)
                        .withValue(value)
                        .build());
            }
        }
        else if (this.type == TranslationEntityType.TARGET) // For a text, the target is not supposed to exist!
        {
            for (Object propKey : source.getAsProperties().keySet())
            {
                // Create the missing entries (all)
                this.entries.put((String) propKey, TranslationEntry.builder()
                        .withKey((String) propKey)
                        .withLanguage(language)
                        .build());
            }
        }
    }

    /**
     * Check the source and object types should be the same.
     * @param source Source translation entity.
     * @param object Object representing the target translation entity.
     * @throws TranslationException Thrown in case the source object type and the target object type are not the same.
     */
    private void checkObjectTypes(final @NonNull ITranslationEntity source, final @NonNull Object object) throws TranslationException
    {
        if (source.getFileType() != fileType)
        {
            throw new TranslationException(String.format("❗Incompatible source: %s and target: %s types. Source and target mst have the same type!",
                    source.getObject().getClass().getName(),
                    object.getClass().getName()));
        }
    }

    @Override
    public final Properties getAsProperties()
    {
        Properties properties = new Properties();

        for (String key : entries.keySet())
        {
            properties.put(key, entries.get(key));
        }

        return properties;
    }

    @Override
    public final int count()
    {
        return entries.size();
    }

    @Override
    public final int countTranslated()
    {
        return (int) entries.values().stream().filter(ITranslationEntry::isTranslated).count();
    }

    @Override
    public final int countToTranslate()
    {
        return (int) entries.values().stream().filter(ITranslationEntry::isToTranslate).count();
    }

    @Override
    public final int countFailed()
    {
        return (int) entries.values().stream().filter(ITranslationEntry::isFailed).count();
    }

    @Override
    public final List<ITranslationEntry> getEntries()
    {
        return entries.values().stream().sorted().toList();
    }

    @Override
    public final ITranslationEntry getEntry(final @NonNull String key)
    {
        return entries.get(key);
    }

    @Override
    public final void addEntry(@NonNull ITranslationEntry entry)
    {
        entries.put(entry.getKey(), entry);
    }

    @Override
    public final boolean existEntry(@NonNull String key)
    {
        return entries.get(key) != null;
    }

    @Override
    public final void removeEntry(@NonNull String key)
    {
        entries.remove(key);
    }

    @Override
    public void clear()
    {
        entries.clear();
        type = TranslationEntityType.UNKNOWN;
        toTranslate = false;
        failed = false;
        translated = false;
    }
}
