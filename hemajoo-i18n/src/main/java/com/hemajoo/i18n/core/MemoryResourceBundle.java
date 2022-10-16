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

import com.hemajoo.i18n.core.localization.data.LanguageType;
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
        this.baseBundleName = baseBundleName;
    }

    public String getKey(final @NonNull String key)
    {
        return (String) properties.get(key);
    }

    public String getValue(final @NonNull String key)
    {
        return properties.getProperty(key);
    }

    public void updateValue(final @NonNull String key, final String value)
    {
        properties.put(key, value);
    }

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
