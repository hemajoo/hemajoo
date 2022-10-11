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
package com.hemajoo.utility.test.random;

import com.hemajoo.utility.random.GeneratorException;
import com.hemajoo.utility.random.RandomEnumGenerator;
import com.hemajoo.utility.test.internal.AnimalType;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Log4j2
class RandomEnumGeneratorUnitTest
{
    private final RandomEnumGenerator animalGenerator = RandomEnumGenerator.of(AnimalType.class);

    @RepeatedTest(value = 20)
    @DisplayName("Generate a pseudo-random enumerated value")
    final void testGenerateRandomEnumeratedValue() throws GeneratorException
    {
        AnimalType animal = (AnimalType) animalGenerator.generate();

        LOGGER.debug(String.format("Random enumerated value: '%s' from enumeration: '%s'", animal, AnimalType.class.getSimpleName()));

        assertThat(animal).isNotNull();
    }

    @RepeatedTest(value = 20)
    @DisplayName("Generate a pseudo-random enumerated value with some exclusion")
    final void testGenerateRandomEnumeratedValueWithExclusion() throws GeneratorException
    {
        AnimalType animal = (AnimalType) animalGenerator.exclude(AnimalType.BIRD).exclude(AnimalType.FISH).generate();

        assertThat(animal).isNotNull().isNotEqualTo(AnimalType.BIRD).isNotEqualTo(AnimalType.FISH);
    }

    @Test
    @DisplayName("Cannot generate a pseudo-random enumerated value when all values are excluded")
    final void testCannotGenerateRandomEnumeratedValueWhenAllValuesExcluded()
    {
        assertThatThrownBy(() -> animalGenerator
                .exclude(AnimalType.BIRD)
                .exclude(AnimalType.FISH)
                .exclude(AnimalType.CAT)
                .exclude(AnimalType.DOG)
                .exclude(AnimalType.CROCODILE)
                .exclude(AnimalType.EAGLE)
                .exclude(AnimalType.ELEPHANT)
                .exclude(AnimalType.TIGER)
                .exclude(AnimalType.SHARK)
                .generate())
                .isInstanceOf(GeneratorException.class);
    }
}
