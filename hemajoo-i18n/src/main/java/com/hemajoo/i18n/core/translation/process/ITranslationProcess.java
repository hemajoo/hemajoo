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
package com.hemajoo.i18n.core.translation.process;

import com.hemajoo.i18n.core.translation.ITranslationProcessor;
import com.hemajoo.i18n.core.translation.TranslationException;
import com.hemajoo.i18n.core.translation.request.ITranslationRequest;
import com.hemajoo.i18n.core.translation.request.ITranslationRequestEntry;
import com.hemajoo.i18n.core.translation.result.ITranslationResult;
import lombok.NonNull;

/**
 * Interface defining the behavior of a translation process (composed of a request, request entries, a processor
 * and their associated results).
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * @see ITranslationRequest
 * @see ITranslationRequestEntry
 * @see ITranslationProcessor
 * @see ITranslationResult
 */
public interface ITranslationProcess
{
    /**
     * Return the translation request associated to this translation process.
     * @return {@link ITranslationRequest}.
     */
    ITranslationRequest getRequest();

    /**
     * Set the translation request associated to this translation process.
     * @param request {@link ITranslationRequest}.
     */
    void setRequest(final @NonNull ITranslationRequest request);

    /**
     * Return the translation result associated to this translation process.
     * <br>
     * A translation process has a translation result only if the request is in compact mode,
     * see {@link ITranslationRequest#isCompactMode()}, otherwise each translation request entry has its own
     * translation result.
     * @return {@link ITranslationResult}.
     */
    ITranslationResult getResult();

    /**
     * Set the translation result associated to this translation process.
     * <br>
     * A translation process has a translation result only if the request is in compact mode,
     * see {@link ITranslationRequest#isCompactMode()}, otherwise each translation request entry has its own
     * translation result.
     * @param result {@link ITranslationResult}.
     */
    void setResult(final @NonNull ITranslationResult result);

    /**
     * Return the translation processor associated to this translation process.
     * @return {@link ITranslationProcessor}.
     */
    ITranslationProcessor getProcessor();

    /**
     * Return if the translation process has been successfully processed by a translation processor?
     * @return True if it has been successfully processed, false otherwise.
     */
    boolean isTranslated();

    /**
     * Return if this translation process requires to be processed by a translation processor?
     * <br>
     * Under certain circumstances, a translation request does not require a translation.
     * @return True if it requires to be processed, false otherwise.
     */
    boolean requireProcessing();

    /**
     * Return the content of the document of the underlying property file as text.
     * @return Document content.
     * @throws TranslationException Thrown in case an error occurred while generating the document content.
     */
    String getDocument() throws TranslationException;

    void setElapsed(final long elapsed);

    void updateEntry(final @NonNull ITranslationRequestEntry entry, final @NonNull ITranslationResult result);

    long getElapsed();
}
