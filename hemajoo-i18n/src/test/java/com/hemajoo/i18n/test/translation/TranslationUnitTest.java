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
import com.hemajoo.i18n.localization.I18nManager;
import com.hemajoo.i18n.localization.data.LanguageType;
import com.hemajoo.i18n.localization.exception.LocalizationException;
import com.hemajoo.i18n.localization.exception.ResourceException;
import com.hemajoo.i18n.translation.Translation;
import com.hemajoo.i18n.translation.engine.ITranslationEngine;
import com.hemajoo.i18n.translation.engine.TranslationEngine;
import com.hemajoo.i18n.translation.engine.google.GoogleFreeTranslationEngine;
import com.hemajoo.i18n.translation.entity.ITranslationEntity;
import com.hemajoo.i18n.translation.entity.TranslationEntity;
import com.hemajoo.i18n.translation.exception.TranslationException;
import com.hemajoo.i18n.translation.type.TranslationEntityType;
import com.hemajoo.i18n.translation.type.TranslationProviderType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A unit test class for testing the <b>Translation</b> entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Slf4j
class TranslationUnitTest extends AbstractHemajooUnitTest
{
    /**
     * Test resource bundle.
     */
    private static final String RESOURCE_BUNDLE_DAY = "i18n/day";

    /**
     * Test text document as a string.
     */
    private static final String TEXT = "Advantages of Test Documentation\n" +
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

    /**
     * Testing purpose constant for a word in english.
     */
    private static final String TEST_ENGLISH_WORD = "Philosophy";

    /**
     * Testing purpose constant for a sentence in english.
     */
    private static final String TEST_ENGLISH_SENTENCE = "A spoken language is a language produced by articulate sounds, as opposed to a written language. An oral language or vocal language is a language produced with the vocal tract, as opposed to a sign language, which is produced with the hands and face.";

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Translation entity must have a translation entity type set")
    void testTranslationEntityMustHaveTranslationEntityType() throws LocalizationException, ResourceException, TranslationException
    {
        I18nManager.getInstance().load(RESOURCE_BUNDLE_DAY);

        assertThatThrownBy(() ->
                TranslationEntity.builder()
                        .withObject(I18nManager.getInstance().getBundle(RESOURCE_BUNDLE_DAY, LanguageType.ENGLISH)) // TODO If not exist, should try to load the bundle!
                        .build())
                .isInstanceOf(NullPointerException.class);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Target translation entity must have a source entity set")
    void testTranslationEntityMustHaveSourceSet() throws LocalizationException
    {
        I18nManager.getInstance().load(RESOURCE_BUNDLE_DAY);

        assertThatThrownBy(() ->
                TranslationEntity.builder()
                        .withObject(I18nManager.getInstance().getBundle(RESOURCE_BUNDLE_DAY, LanguageType.ENGLISH))
                        .withEntityType(TranslationEntityType.TARGET)
                        .build())
                .isInstanceOf(TranslationException.class);
    }

    @SuppressWarnings("java:S5778")
    @Test
    @DisplayName("Translate some resource bundle using the Google free translator")
    void testCreateResourceBundleTranslationEntity() throws LocalizationException, ResourceException, TranslationException
    {
        I18nManager.getInstance().load(RESOURCE_BUNDLE_DAY);

        ITranslationEntity source = TranslationEntity.builder()
                .withEntityType(TranslationEntityType.SOURCE)
                .withObject(I18nManager.getInstance().getBundle(RESOURCE_BUNDLE_DAY, LanguageType.ENGLISH))
                .build();

        ITranslationEntity german = TranslationEntity.builder()
                .withSource(source)
                .withObject(I18nManager.getInstance().getBundle(RESOURCE_BUNDLE_DAY, LanguageType.GERMAN))
                .withEntityType(TranslationEntityType.TARGET)
                .build();

        ITranslationEntity italian = TranslationEntity.builder()
                .withSource(source)
                .withObject(I18nManager.getInstance().getBundle(RESOURCE_BUNDLE_DAY, LanguageType.ITALIAN))
                .withEntityType(TranslationEntityType.TARGET)
                .build();

        ITranslationEntity french = TranslationEntity.builder()
                .withSource(source)
                .withObject(I18nManager.getInstance().getBundle(RESOURCE_BUNDLE_DAY, LanguageType.FRENCH))
                .withEntityType(TranslationEntityType.TARGET)
                .build();

        Translation translation = Translation.builder()
                .withSource(source)
                .withTarget(german)
                .build();
        translation.addTargetEntity(italian);
        translation.addTargetEntity(french);

        ITranslationEngine translator = new GoogleFreeTranslationEngine(translation);
        translator.translate();

        assertThat(translation.getExecutionTime()).isPositive();
    }

    @Test
    @DisplayName("Translate a properties using the Google free translator")
    void testCreatePropertiesTranslationEntity() throws TranslationException
    {
        Properties properties = new Properties();
        properties.put("animal.dog", "Dog");
        properties.put("animal.cat", "Cat");
        properties.put("animal.fish", "Fish");
        properties.put("animal.tiger", "Tiger");
        properties.put("animal.crocodile", "Crocodile");
        properties.put("animal.bird", "Bird");
        properties.put("animal.eagle", "Eagle");
        properties.put("animal.elephant", "Elephant");
        properties.put("animal.shark", "Shark");

        ITranslationEntity source = TranslationEntity.builder()
                .withEntityType(TranslationEntityType.SOURCE)
                .withLanguage(LanguageType.ENGLISH)
                .withObject(properties)
                .build();

        ITranslationEntity target = TranslationEntity.builder()
                .withEntityType(TranslationEntityType.TARGET)
                .withLanguage(LanguageType.SPANISH)
                .withSource(source)
                .build();

        Translation translation = Translation.builder()
                .withSource(source)
                .withTarget(target)
                .build();

        ITranslationEngine engine = new TranslationEngine(TranslationProviderType.GOOGLE_FREE, translation);
        engine.translate();

        assertThat(translation.getExecutionTime()).isPositive();
    }

    @Test
    @DisplayName("Translate a properties file entities based on a text string using the Google free translator")
    void testCreateTextTranslationEntity() throws TranslationException
    {
        ITranslationEntity source = TranslationEntity.builder()
                .withEntityType(TranslationEntityType.SOURCE)
                .withLanguage(LanguageType.ENGLISH)
                .withObject(TEXT)
                .build();

        ITranslationEntity target = TranslationEntity.builder()
                .withEntityType(TranslationEntityType.TARGET)
                .withLanguage(LanguageType.FRENCH)
                .withSource(source)
                .build();

        Translation translation = Translation.builder()
                .withSource(source)
                .withTarget(target)
                .build();

        ITranslationEngine translator = new GoogleFreeTranslationEngine(translation); // or new TranslationEngine(TranslationProviderType.AZURE, translation);
        translator.translate();

        Properties result = target.getAsProperties();

        assertThat(translation.getExecutionTime()).isPositive();
    }

    @Test
    @DisplayName("Translate directly a text using the Google free translator")
    void testTranslateTextUsingGoogleFreeTranslator() throws TranslationException
    {
        ITranslationEngine translationEngine = new GoogleFreeTranslationEngine();
        String translation = translationEngine.translateDirect(LanguageType.ENGLISH, LanguageType.POLISH, TEXT); // Will not preserve lines feed!

        assertThat(translation).isNotNull();
    }
}
