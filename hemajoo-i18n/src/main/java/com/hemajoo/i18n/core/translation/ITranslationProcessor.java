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

import com.hemajoo.i18n.core.translation.process.ITranslationProcess;
import com.hemajoo.i18n.core.translation.request.ITranslationRequestEntry;
import com.hemajoo.i18n.core.translation.result.ITranslationResultSentence;
import com.hemajoo.i18n.translation.core.TranslationException;
import lombok.NonNull;

/**
 * Provide the behavior of a <b>translation processor</b>.
 * <br>
 * Specialized translation processors are available and will make use of specific translation APIs such as:<br>
 * <ul>
 * <li><em>Google Free Translation API</em></li>
 * <li><em>Google Translation API</em></li>
 * <li><em>Azure Translation API</em></li>
 * <li><em>IBM Translation API</em></li>
 * <li><em>etc.</em></li>
 * </ul>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * @see ITranslationResultSentence
 */
public interface ITranslationProcessor
{
    /**
     * Translates a translation process (with its underlying request) using this translation processor.
     * @param process Translation process.
     * @param entry Translation request entry to process.
     * @throws TranslationException Thrown in case an error occurred during translation.
     */
    void translate(final @NonNull ITranslationProcess process, final @NonNull ITranslationRequestEntry entry) throws TranslationException;
}
