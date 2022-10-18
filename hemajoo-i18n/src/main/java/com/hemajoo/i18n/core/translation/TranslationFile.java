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
package com.hemajoo.i18n.core.translation;

import com.hemajoo.commons.annotation.support.NotYetImplemented;
import com.hemajoo.commons.exception.NotYetImplementedException;
import com.hemajoo.i18n.core.localization.data.LanguageType;
import com.hemajoo.i18n.translation.core.ITranslation;
import com.hemajoo.i18n.translation.core.type.TranslationFileType;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;
import java.util.*;

/**
 * Represent a <b>file</b> and its content to be translated.
 * <br><br>
 * It keeps track of the source of the translation as well as its translated versions (language specific).
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class TranslationFile implements Serializable
{
    /**
     * Source file for this translation.
     */
    private ITranslation source;

    /**
     * Collection of translations.
     */
    private Map<LanguageType, ITranslation> translations = new EnumMap<>(LanguageType.class);




    @Getter
    private final LanguageType language;

    @Getter
    private final String baseBundleName;

    @Getter
    private final Properties properties;

    @Getter
    private TranslationFileType fileType;

    /**
     * Create a translation file based on a source {@link ResourceBundle}.
     * <br><br>
     * The source resource bundle is supposed to contain all the entries (keys and values) for the source of the translation. The translation file created will represent the target resource
     * bundle and will be populated with translations according to the given target language.
     * <br><br>
     * The target resource bundle is not supposed to exist initially or if so it means there is a clear intention to override it.
     * @param sourceBundle Resource bundle.
     * @param targetLanguage Target language.
     */
    public TranslationFile(final @NonNull ResourceBundle sourceBundle, final LanguageType targetLanguage)
    {
        this.fileType = TranslationFileType.RESOURCE_BUNDLE;

        this.language = targetLanguage;
        this.baseBundleName = sourceBundle.getBaseBundleName();

        // Make a copy of the properties of the original resource bundle
        this.properties = copyProperties(sourceBundle);
    }

    /**
     * Create a translation file based on a source {@link ResourceBundle} and an already existing target {@link ResourceBundle} having some missing entries or entries not containing values.
     * <br><br>
     * The source resource bundle will be used to create the missing entries in the target resource bundle if there are some. All values in the target resource bundle will then be candidate
     * for a translation later on.
     * @param sourceBundle Source resource bundle.
     * @param targetBundle Target resource bundle.
     */
    public TranslationFile(final @NonNull ResourceBundle sourceBundle, final @NonNull ResourceBundle targetBundle)
    {
        this.fileType = TranslationFileType.RESOURCE_BUNDLE;

        this.language = LanguageType.from(targetBundle.getLocale().toLanguageTag());
        this.baseBundleName = sourceBundle.getBaseBundleName();

        // Make a copy of the properties
        this.properties = copyProperties(sourceBundle);

//        for (String key : bundle.keySet())
//        {
//            if (LanguageType.from(bundle.getLocale()) == language)
//            {
//                properties.put(key, bundle.getString(key));
//            }
//            else
//            {
//                properties.put(key, "");
//            }
//        }
    }

    public TranslationFile(final @NonNull Properties properties, final LanguageType language, final @NonNull String baseBundleName)
    {
        this.properties = properties;
        this.language = language;
        this.baseBundleName = baseBundleName + "_" + language.getLocale().getLanguage();
    }

    /**
     * Create a {@link TranslationFile} object copied from a {@link TranslationFile} object with emptied values (keys are preserved).
     * @param source Resource bundle object.
     * @param language Target language
     * @return Newly created {@link TranslationFile} object.
     */
    public static TranslationFile copyEmpty(final @NonNull TranslationFile source, final LanguageType language)
    {
        TranslationFile copy = new TranslationFile(copyProperties(source.getProperties()), language, source.getBaseBundleName());
        clearValues(copy);

        return copy;
    }

    /**
     * Create a {@link TranslationFile} object copied from a {@link ResourceBundle} object with emptied values (keys are preserved).
     * @param source Properties object.
     * @param language Target language
     * @return Newly created {@link TranslationFile} object.
     */
    public static TranslationFile copyEmpty(final @NonNull ResourceBundle source, final LanguageType language)
    {
        TranslationFile temporary = new TranslationFile(source, language); //BUG Do a copy of the resource bundle properties!
        TranslationFile copy = new TranslationFile(temporary.getProperties(), language, source.getBaseBundleName());
        clearValues(copy);

        return copy;
    }

    /**
     * Create a {@link TranslationFile} object copied from a based {@link Properties} object with emptied values (keys are preserved).
     * @param source Properties object.
     * @param language Target language
     * @param name Base name (properties or resource bundle).
     * @return Newly created {@link TranslationFile} object.
     */
    public static TranslationFile copyEmpty(final @NonNull Properties source, final LanguageType language, final @NonNull String name)
    {
        TranslationFile copy = new TranslationFile(copyProperties(source), language, name);
        clearValues(copy);

        return copy;
    }

    /**
     * Make a copy of a {@link Properties} object.
     * @param properties Properties.
     * @return {@link Properties}.
     */
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
     * Make a copy of a {@link Properties} from a given {@link ResourceBundle} object.
     * @param bundle Resource bundle.
     * @return {@link Properties}.
     */
    private static Properties copyProperties(final @NonNull ResourceBundle bundle)
    {
        Properties copy = new Properties(bundle.keySet().size());
        for (String key : bundle.keySet())
        {
            copy.put(key, bundle.getString(key));
        }

        return copy;
    }

    /**
     * Make a copy of a {@link Properties} from a given {@link ResourceBundle} object.
     * @param text Text as a string ('\n' will be used to split values).
     * @return {@link Properties}.
     */
    @NotYetImplemented
    private static Properties getProperties(final @NonNull String text)
    {
        throw new NotYetImplementedException();
    }

    /**
     * Make a copy of a {@link Properties} from a given {@link ResourceBundle} object.
     * @param text Text as a string.
     * @param separator Separator character(s).
     * @return {@link Properties}.
     */
    @NotYetImplemented
    private static Properties getProperties(final @NonNull String text, final @NonNull String separator)
    {
        throw new NotYetImplementedException();
    }

    /**
     * Clear the values of the given {@link TranslationFile}
     * @param bundle Memory resource bundle.
     */
    private static void clearValues(final @NonNull TranslationFile bundle)
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
