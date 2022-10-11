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
package com.hemajoo.i18n.data;

import com.hemajoo.i18n.core.annotation.I18n;
import com.hemajoo.i18n.core.localization.I18nManager;
import com.hemajoo.i18n.core.localization.LocalizationException;
import com.hemajoo.i18n.core.localization.LocalizeEnum;
import lombok.NonNull;

import java.util.Locale;

/**
 * A localized enumeration representing the <b>days</b> of the year.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * @see I18n
 */
@I18n(bundle = "i18n/day", key = "day.${this}.name")
public enum DayType implements LocalizeEnum
{
    /**
     * Monday.
     */
    MONDAY,

    /**
     * Tuesday.
     */
    TUESDAY,

    /**
     * Wednesday.
     */
    WEDNESDAY,

    /**
     * Thursday.
     */
    THURSDAY,

    /**
     * Friday.
     */
    FRIDAY,

    /**
     * Saturday.
     */
    SATURDAY,

    /**
     * Sunday.
     */
    SUNDAY;

    /**
     * Resource bundle file.
     */
    private static final transient String RESOURCE_BUNDLE_FILE = "i18n/day";

    /**
     * Return the localized name of the day.
     * @return Day name.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @Override
    @I18n(bundle = DayType.RESOURCE_BUNDLE_FILE, key = "day.${this}.name")
    public String getName() throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Return the localized name of the day.
     * @param locale Locale.
     * @return Day name.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @Override
    @I18n(bundle = DayType.RESOURCE_BUNDLE_FILE, key = "day.${this}.name")
    public String getName(final @NonNull Locale locale) throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, locale);
    }

    /**
     * Return the localized description of the day.
     * @return Description.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @I18n(bundle = DayType.RESOURCE_BUNDLE_FILE, key = "day.${this}.description")
    public String getDescription() throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, I18nManager.getInstance().getLocale());
    }

    /**
     * Return the localized description of the day.
     * @param locale Locale.
     * @return Description.
     * @throws LocalizationException Thrown to indicate an error occurred while trying to localize a resource.
     */
    @I18n(bundle = DayType.RESOURCE_BUNDLE_FILE, key = "day.${this}.description")
    public String getDescription(final @NonNull Locale locale) throws LocalizationException
    {
        return I18nManager.getInstance().localize(this, locale);
    }
}
