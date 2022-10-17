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
import com.hemajoo.i18n.core.translation.request.ITranslationRequest;
import com.hemajoo.i18n.core.translation.request.ITranslationRequestEntry;
import com.hemajoo.i18n.core.translation.request.TranslationRequestEntry;
import com.hemajoo.i18n.core.translation.result.ITranslationResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

/**
 * A translation process.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
public class TranslationProcess implements ITranslationProcess
{
    /**
     * Translation request.
     */
    @Getter
    private ITranslationRequest request;

    /**
     * Translation result.
     */
    @Getter
    private ITranslationResult result;

    /**
     * Translation processor.
     */
    @Getter
    private ITranslationProcessor processor;

    /**
     * Does this translation process has been translated?
     */
    @Getter
    private boolean translated = false;

    /**
     * Does this translation process requires a translation by a translation processor?
     */
    private boolean requireProcessing = false;

    /**
     * Document content.
     */
    private final StringBuilder document = new StringBuilder();

    @Getter
    @Setter
    private long elapsed;

    /**
     * Create a translation process.
     * @param processor Translation processor.
     */
    public TranslationProcess(final @NonNull ITranslationProcessor processor)
    {
        this.processor = processor;
    }

    @Override
    public final boolean requireProcessing()
    {
        return requireProcessing;
    }

    @Override
    public final boolean isTranslated()
    {
        return translated;
    }

    @Override
    public void setRequest(final @NonNull ITranslationRequest request)
    {
        this.request = request;

        generateRequestEntries();
        computeRequireProcessing();
    }

    @Override
    public final void setResult(@NonNull ITranslationResult result)
    {
        this.result = result;
        translated = true;
    }

    @Override
    public final String getDocument()
    {
        generateDocument();

        return document.toString();
    }

    @Override
    public void updateEntry(@NonNull ITranslationRequestEntry entry, @NonNull ITranslationResult result)
    {
        if (request != null)
        {
            request.updateEntry(entry, result);
        }
    }

    /**
     * Generates the translation request entries based on the source and target properties.
     */
    private void generateRequestEntries()
    {
        TranslationRequestEntry requestEntry;
        String targetValue;

        for (Map.Entry<String, String> entry : request.getSources().entrySet())
        {
            targetValue = request.getTarget(entry.getKey());
            if (targetValue == null || targetValue.isBlank())
            {
                requestEntry = new TranslationRequestEntry(entry.getKey(), entry.getValue(), true);
                request.getEntries().add(requestEntry);
            }
            else
            {
                requestEntry = new TranslationRequestEntry(entry.getKey(), entry.getValue(), false);
                requestEntry.setTranslation(targetValue);
                request.getEntries().add(requestEntry);
            }
        }
    }

    /**
     * Computes if a translation processing is required or not.
     */
    private void computeRequireProcessing()
    {
        // Do we have at least one entry that require translation?
        Optional<TranslationRequestEntry> element = getRequest().getEntries().stream()
                .filter(TranslationRequestEntry::requireTranslation)
                .findAny();

        requireProcessing = element.isPresent();
    }

    /**
     * Generates the document.
     */
    private void generateDocument()
    {
        for (ITranslationRequestEntry entry : request.getEntries())
        {
            document.append(entry.getKey()).append("=").append(entry.getTranslation()).append("\n");
        }
    }

    // Not used for the moment, not accurate enough!
//    private void prepareDocumentForCompactMode() throws TranslationException
//    {
//        ITranslationRequestEntry entry;
//
//        for (ITranslationResultSentence sentence : getResult().getSentences())
//        {
//            if (sentence.getOriginal() != null)
//            {
//                String[] originals = sentence.getOriginal().replace("[", "").replace("]", "").split(",");
//                String[] translations = sentence.getTranslation().replace("[", "").replace("]", "").split(",");
//                if (originals.length != translations.length)
//                {
//                    // Separator characters translated for other alphabets
//                    translations = sentence.getTranslation().replace("[", "").replace("]", "").split("、");
//                }
//                for (int i = 0; i < originals.length; i++)
//                {
//                    // Find the key of the value in the source
//                    entry = getRequest().findEntryKeyFor(originals[i]);
//                    entry.setTranslation(translations[i].trim());
//                }
//            }
//        }
//
//        generateDocument();
//    }
}
