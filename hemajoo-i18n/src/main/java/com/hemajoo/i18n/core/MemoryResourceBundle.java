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
package com.hemajoo.i18n.core;

import com.hemajoo.i18n.localization.data.LanguageType;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

public class MemoryResourceBundle
{
    @Getter
    private final LanguageType language;

    @Getter
    private final String baseBundleName;

    @Getter
    private final Properties properties;

    public MemoryResourceBundle(final @NonNull ResourceBundle bundle, final LanguageType targetLanguage)
    {
        this.language = targetLanguage;
        this.baseBundleName = bundle.getBaseBundleName();
        this.properties = new Properties();

        for (String key : bundle.keySet())
        {
            if (LanguageType.from(bundle.getLocale()) == targetLanguage)
            {
                properties.put(key, bundle.getString(key));
            }
            else
            {
                properties.put(key, "");
            }
        }
    }

    public MemoryResourceBundle(final @NonNull Properties properties, final LanguageType language, final @NonNull String baseBundleName)
    {
        this.properties = properties;
        this.language = language;
        this.baseBundleName = baseBundleName + "_" + language.getLocale().getLanguage();
    }

    /**
     * Create a {@link MemoryResourceBundle} object copied from a {@link MemoryResourceBundle} object with emptied values (keys are preserved).
     * @param source Resource bundle object.
     * @param language Target language
     * @return Newly created {@link MemoryResourceBundle} object.
     */
    public static MemoryResourceBundle copyEmpty(final @NonNull MemoryResourceBundle source, final LanguageType language)
    {
        MemoryResourceBundle copy = new MemoryResourceBundle(copyProperties(source.getProperties()), language, source.getBaseBundleName());
        clearValues(copy);

        return copy;
    }

    /**
     * Create a {@link MemoryResourceBundle} object copied from a {@link ResourceBundle} object with emptied values (keys are preserved).
     * @param source Properties object.
     * @param language Target language
     * @return Newly created {@link MemoryResourceBundle} object.
     */
    public static MemoryResourceBundle copyEmpty(final @NonNull ResourceBundle source, final LanguageType language)
    {
        MemoryResourceBundle temporary = new MemoryResourceBundle(source, language); //BUG Do a copy of the resource bundle properties!
        MemoryResourceBundle copy = new MemoryResourceBundle(temporary.getProperties(), language, source.getBaseBundleName());
        clearValues(copy);

        return copy;
    }

    /**
     * Create a {@link MemoryResourceBundle} object copied from a based {@link Properties} object with emptied values (keys are preserved).
     * @param source Properties object.
     * @param language Target language
     * @param name Base name (properties or resource bundle).
     * @return Newly created {@link MemoryResourceBundle} object.
     */
    public static MemoryResourceBundle copyEmpty(final @NonNull Properties source, final LanguageType language, final @NonNull String name)
    {
        MemoryResourceBundle copy = new MemoryResourceBundle(copyProperties(source), language, name);
        clearValues(copy);

        return copy;
    }

    private static Properties copyProperties(final @NonNull Properties properties)
    {
        Properties copy = new Properties(properties.size());
        for (Object key : properties.keySet())
        {
            copy.put(key, properties.getProperty((String) key));
        }

        return copy;
    }

    /**
     * Clear the values of the given {@link MemoryResourceBundle}
     * @param bundle Memory resource bundle.
     */
    private static void clearValues(final @NonNull MemoryResourceBundle bundle)
    {
        for (String key : bundle.getKeys())
        {
            bundle.getProperties().put(key, "");
        }
    }

    /**
     * Return the value.
     * @param key Key.
     * @return Key value.
     */
    public String getValue(final @NonNull String key)
    {
        return properties.getProperty(key);
    }

    /**
     * Update a value.
     * @param key Key.
     * @param value Value to update.
     */
    public void updateValue(final @NonNull String key, final String value)
    {
        properties.put(key, value);
    }

    /**
     * Return a list of the keys.
     * @return List of keys.
     */
    public List<String> getKeys()
    {
        List<Object> keyList = Collections.list(properties.keys());
        List<String> keys = new ArrayList<>();
        for (Object o : keyList)
        {
            keys.add((String) o);
        }

        return keys;
    }
}
