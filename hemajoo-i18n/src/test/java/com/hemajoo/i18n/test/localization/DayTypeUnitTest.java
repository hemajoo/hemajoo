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
package com.hemajoo.i18n.test.localization;

import com.hemajoo.commons.core.DatabaseTestConfiguration;
import com.hemajoo.i18n.localization.data.DayType;
import com.hemajoo.i18n.localization.exception.LocalizationException;
import lombok.NonNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A class for unit testing the {@link DayType} localized enumeration.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SpringBootTest(classes = { DatabaseTestConfiguration.class })
class DayTypeUnitTest
{
    @ParameterizedTest
    @CsvSource(value = {
            "MONDAY;fr;Lundi",
            "MONDAY;en;Monday",
            "MONDAY;de;Montag",
            "MONDAY;es;Lunes",
            "MONDAY;it;Lunedì",
    }, delimiter = ';')
    void testLocalizeDayTypeName(final @NonNull String enumValue, final @NonNull String isoAlpha2, final @NonNull String localization) throws LocalizationException
    {
        DayType day = DayType.valueOf(enumValue);

        assertThat(day.getName(Locale.forLanguageTag(isoAlpha2))).isEqualTo(localization);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "FRIDAY;fr;Le vendredi est le cinquième jour de la semaine si l'on considère que la semaine commence le lundi et le sixième si l'on considère que la semaine commence le dimanche. Le mot « vendredi » est issu du latin Veneris dies, signifiant « jour de Vénus », le nom de Vénus au génitif en latin étant Veneris.",
            "FRIDAY;en;Friday is the day of the week between Thursday and Saturday. In countries adopting the \"Monday-first\" convention it is the fifth day of the week. In countries that adopt the \"Sunday-first\" convention, it is the sixth day of the week.",
    }, delimiter = ';')
    void testLocalizeDayTypeDescription(final @NonNull String enumValue, final @NonNull String isoAlpha2, final @NonNull String localization) throws LocalizationException
    {
        DayType day = DayType.valueOf(enumValue);

        assertThat(day.getDescription(Locale.forLanguageTag(isoAlpha2))).isEqualTo(localization);
    }
}
