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
import com.hemajoo.i18n.translation.core.entry.ITranslationEntry;
import com.hemajoo.i18n.translation.core.type.TranslationEntityType;
import com.hemajoo.i18n.translation.core.type.TranslationFileType;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * Represent a translation entity. It can be a <b>source</b> (meaning it's the reference for target(s) entities to be translated) or a <b>target</b> (meaning it's an entity to be translated).
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface ITranslationEntity extends Serializable
{
    /**
     * Return the language type.
     * @return {@link LanguageType}.
     */
    LanguageType getLanguage();

    /**
     * Return the type (file type) of this translation entity.
     * @return {@link TranslationFileType} representing the type (file type) of this translation entity.
     */
    TranslationFileType getFileType();

    /**
     * Return the type of this translation entity.
     * @return {@link TranslationEntityType}.
     */
    TranslationEntityType getType();

    /**
     * Return the object representing this translation entity.
     * @return Object representing this translation entity.
     */
    Object getObject();

    /**
     * Return if this translation entity has been translated ?
     * @return <b>True</b> if this translation entity has been translated, <b>false</b> otherwise.
     */
    boolean isTranslated();

    /**
     * Return if this translation entity has failed being translated ?
     * @return <b>True</b> if this translation entity has failed being translated, <b>false</b> otherwise.
     */
    boolean isFailed();

    /**
     * Return if this translation entity is to be translated ?
     * @return <b>True</b> if this translation entity is to be translated, <b>false</b> otherwise.
     */
    boolean isToTranslate();

    /**
     * Return the total number of entries.
     * @return Total number of entries.
     */
    int count();

    /**
     * Return the number of translated entries.
     * @return Number of translated entries.
     */
    int countTranslated();

    /**
     * Return the number of entries to translate.
     * @return Number of entries to translate.
     */
    int countToTranslate();

    /**
     * Return the number of entries having failed to be translated.
     * @return Number of entries having failed to be translated.
     */
    int countFailed();

    /**
     * Return the list of translation entries.
     * @return Entries.
     */
    List<ITranslationEntry> getEntries();

    /**
     * Return a translation entry.
     * @param key Key.
     * @return {@link ITranslationEntry} if found, <b>null</b> otherwise.
     */
    ITranslationEntry getEntry(final @NonNull String key);

    /**
     * Add a translation entry.
     * @param entry Translation entry.
     */
    void addEntry(final @NonNull ITranslationEntry entry);

    /**
     * Return if a translation entry exist for the given key?
     * @param key Key.
     * @return <b>True</b> if a translation entry exist for the given key, <b>false</b> otherwise.
     */
    boolean existEntry(final @NonNull String key);

    /**
     * Remove a translation entry given its key?
     * @param key Key.
     */
    void removeEntry(final @NonNull String key);

    /**
     * Clear all entries of the translation entity.
     */
    void clear();

    /**
     * Return the entries of this translation entity as a {@link Properties}.
     * @return {@link Properties}.
     */
    Properties getAsProperties();

    long getExecutionTime();

    void addExecutionTime(final long time);
}
