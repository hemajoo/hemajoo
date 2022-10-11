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

import com.hemajoo.i18n.core.annotation.I18n;
import com.hemajoo.i18n.core.localization.Localize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@I18n(bundle = "i18n/test")
public final class QuoteOfTheDay implements Localize
{
    @Getter
    private final String quoteNumber;

    @Setter
    @Getter
    @I18n(key = "com.hemajoo.i18n.quote.${quoteNumber}.name")
    private String quoteName;

    @Setter
    @Getter
    @I18n(key = "com.hemajoo.i18n.quote.${quoteNumber}.text")
    private String quoteDescription;

    @Builder(setterPrefix = "with")
    public QuoteOfTheDay(final int number)
    {
        this.quoteNumber = String.valueOf(number); // Only 1 or 2 at this time!
    }
}
