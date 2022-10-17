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
package com.hemajoo.i18n.test.translation;

import com.hemajoo.commons.core.AbstractHemajooUnitTest;
import com.hemajoo.i18n.core.MemoryResourceBundle;
import com.hemajoo.i18n.core.exception.ResourceException;
import com.hemajoo.i18n.core.localization.I18nManager;
import com.hemajoo.i18n.core.localization.LocalizationException;
import com.hemajoo.i18n.core.localization.data.LanguageException;
import com.hemajoo.i18n.core.localization.data.LanguageType;
import com.hemajoo.i18n.core.translation.TranslationException;
import com.hemajoo.i18n.core.translation.engine.google.GoogleFreeTranslator;
import com.hemajoo.i18n.core.translation.request.TranslationRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A unit test class for testing the <b>Translation</b> entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Slf4j
class TranslationUnitTest extends AbstractHemajooUnitTest
{
    /**
     * Testing purpose constant for a word in english.
     */
    private static final String TEST_ENGLISH_WORD = "Philosophy";

    /**
     * Testing purpose constant for a sentence in english.
     */
    private static final String TEST_ENGLISH_SENTENCE = "A spoken language is a language produced by articulate sounds, as opposed to a written language. An oral language or vocal language is a language produced with the vocal tract, as opposed to a sign language, which is produced with the hands and face.";

    @Test
    @DisplayName("Translate a quote using the Google free translator")
    void testTranslateQuoteUsingGoogleFreeTranslator() throws TranslationException
    {
        String translated;
        String original;

        // Translate using the Translation static service
        original = FAKER.backToTheFuture().quote();
        translated = GoogleFreeTranslator.translate(LanguageType.ENGLISH, LanguageType.FRENCH, original);
        assertThat(translated).isNotNull();
    }

    @Test
    @DisplayName("Translate a a resource bundle using the Google free translator")
    void testTranslateResourceBundleUsingGoogleFreeTranslator() throws TranslationException, LocalizationException, ResourceException, LanguageException
    {
        GoogleFreeTranslator translator;

        I18nManager.getInstance().load("i18n/day");
        ResourceBundle sourceBundle = I18nManager.getInstance().getBundle("i18n/day", LanguageType.ENGLISH);
        MemoryResourceBundle targetBundle = MemoryResourceBundle.copyAndClearValues(sourceBundle, LanguageType.CZECH);

        // Create a translation request
        TranslationRequest request = new TranslationRequest(
                new MemoryResourceBundle(sourceBundle, LanguageType.from(sourceBundle.getLocale())),
                targetBundle);

        // Create a translator
        translator = GoogleFreeTranslator.builder()
                .withRequest(request)
                .build();

        // Translate the content of the translation request
        translator.translate();
        assertThat(translator.getTranslationProcess().getElapsed()).isNotZero();
        translator.close();

        LOGGER.info(String.format(
                "Resource bundle: '%s_%s' successfully translated to: '%s' in: '%s' ms",
                sourceBundle.getBaseBundleName(),
                sourceBundle.getLocale(),
                targetBundle.getLanguage(),
                translator.getTranslationProcess().getElapsed()));
    }

    @Test
    @DisplayName("Translate a text document using the Google free translator")
    void testTranslateTextDocumentUsingGoogleFreeTranslator() throws TranslationException
    {
        String text = "Advantages of Test Documentation\n" +
                "The main reason behind creating test documentation is to either reduce or remove any uncertainties about the testing activities. Helps you to remove ambiguity which often arises when it comes to the allocation of tasks\n" +
                "Documentation not only offers a systematic approach to software testing, but it also acts as training material to freshers in the software testing process\n" +
                "It is also a good marketing & sales strategy to showcase Test Documentation to exhibit a mature testing process\n" +
                "Test documentation helps you to offer a quality product to the client within specific time limits\n" +
                "In Software Engineering, Test Documentation also helps to configure or set-up the program through the configuration document and operator manuals\n" +
                "Test documentation helps you to improve transparency with the client\n" +
                "Disadvantages of Test Documentation\n" +
                "The cost of the documentation may surpass its value as it is very time-consuming\n" +
                "Many times, it is written by people who can’t write well or who don’t know the material\n" +
                "Keeping track of changes requested by the client and updating corresponding documents is tiring.\n" +
                "Poor documentation directly reflects the quality of the product as a misunderstanding between the client and the organization can occur\n" +
                "Summary\n" +
                "\n" +
                "Test documentation is documentation of artifacts created before or during the testing of software.\n" +
                "The degree of test formality depends on 1) the type of application under test 2) standards followed by your organization 3) the maturity of the development process.\n" +
                "Important types of Test Documents are Test policy, Test strategy, Test plan, Test case etc.\n" +
                "QA team needs to be involved in the initial phase of the project so that Test Documentation is created in parallel\n" +
                "The main reason behind creating test documentation is to either reduce or remove any uncertainties about the testing activities.\n" +
                "The cost of the documentation may surpass its value as it is very time-consuming\n";

        // Translate a request using the Google free translator
        GoogleFreeTranslator translator = GoogleFreeTranslator.builder()
                .withRequest(new TranslationRequest(LanguageType.ENGLISH, LanguageType.POLISH, text))
                .build();

        // Translate the content of the translation request
        translator.translate();

        String translation = (String) translator.getTranslationProcess().getRequest().getTranslationResult();
        assertThat(translation).isNotNull();
    }

    @Test
    @DisplayName("Translate a text using the Google free translator")
    void testTranslateTextUsingGoogleFreeTranslator() throws TranslationException
    {
        String text = "Advantages of Test Documentation\n" +
                "The main reason behind creating test documentation is to either reduce or remove any uncertainties about the testing activities. Helps you to remove ambiguity which often arises when it comes to the allocation of tasks\n" +
                "Documentation not only offers a systematic approach to software testing, but it also acts as training material to freshers in the software testing process\n" +
                "It is also a good marketing & sales strategy to showcase Test Documentation to exhibit a mature testing process\n" +
                "Test documentation helps you to offer a quality product to the client within specific time limits\n" +
                "In Software Engineering, Test Documentation also helps to configure or set-up the program through the configuration document and operator manuals\n" +
                "Test documentation helps you to improve transparency with the client\n" +
                "Disadvantages of Test Documentation\n" +
                "The cost of the documentation may surpass its value as it is very time-consuming\n" +
                "Many times, it is written by people who can’t write well or who don’t know the material\n" +
                "Keeping track of changes requested by the client and updating corresponding documents is tiring.\n" +
                "Poor documentation directly reflects the quality of the product as a misunderstanding between the client and the organization can occur\n" +
                "Summary\n" +
                "\n" +
                "Test documentation is documentation of artifacts created before or during the testing of software.\n" +
                "The degree of test formality depends on 1) the type of application under test 2) standards followed by your organization 3) the maturity of the development process.\n" +
                "Important types of Test Documents are Test policy, Test strategy, Test plan, Test case etc.\n" +
                "QA team needs to be involved in the initial phase of the project so that Test Documentation is created in parallel\n" +
                "The main reason behind creating test documentation is to either reduce or remove any uncertainties about the testing activities.\n" +
                "The cost of the documentation may surpass its value as it is very time-consuming\n";

        // Create an instant translator
        String translation = GoogleFreeTranslator.translate(LanguageType.ENGLISH, LanguageType.POLISH, text); // Will not preserve lines feed!
        assertThat(translation).isNotNull();
    }
}
