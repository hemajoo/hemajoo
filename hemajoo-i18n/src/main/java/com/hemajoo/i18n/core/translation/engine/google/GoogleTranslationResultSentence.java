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
package com.hemajoo.i18n.core.translation.engine.google;

import com.hemajoo.i18n.core.translation.result.ITranslationResultSentence;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A translation result sentence for the free <b>Google</b> translation service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
public final class GoogleTranslationResultSentence implements ITranslationResultSentence
{
    /**
     * Translated text.
     */
    @Getter
    @Setter
    private String translation;

    /**
     * Text to translate.
     */
    @Getter
    @Setter
    private String original;

    /**
     * Backend.
     */
    @Getter
    @Setter
    private int backend;
}
