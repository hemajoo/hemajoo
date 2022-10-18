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

import com.hemajoo.i18n.translation.core.TranslationException;

/**
 * Interface defining the behavior of a translator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface ITranslator
{
    /**
     * Translate a <b>translation process</b> that has been previously injected. If none has been set, invoking this service has no effect!
     * @throws TranslationException Thrown to indicate an error occurred while trying to translate the request contained in a translation process.
     */
    void translate() throws TranslationException;

    /**
     * Close a <b>Google free translator</b>, freeing all its resources.
     * @throws TranslationException Thrown to indicate an error occurred while trying to close the translator.
     */
    void close() throws TranslationException;
}
