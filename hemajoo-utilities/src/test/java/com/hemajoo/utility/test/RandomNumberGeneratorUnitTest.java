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
package com.hemajoo.utility.test;

import com.hemajoo.utility.random.RandomNumberGenerator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Log4j2
class RandomNumberGeneratorUnitTest
{
    @Test
    @DisplayName("Generate a pseudo-random integer number")
    final void testGenerateRandomInteger()
    {
        assertThat(RandomNumberGenerator.nextInt(Integer.MAX_VALUE)).isPositive();
    }

    @RepeatedTest(value = 100)
    @DisplayName("Generate a pseudo-random integer number given a min and max")
    final void testGenerateRandomIntegerBetween()
    {
        final int MIN = 100;
        final int MAX = 200;

        int value = RandomNumberGenerator.nextInt(MIN, MAX);

        LOGGER.debug(String.format("Random number generated: '%s', min bound: '%s', max bound: '%s'", value, MIN, MAX));

        assertThat(value)
                .isGreaterThanOrEqualTo(MIN)
                .isLessThanOrEqualTo(MAX);
    }

    @Test
    @DisplayName("Cannot generate a pseudo-random integer number if min bound is less than max bound")
    final void testCannotGenerateRandomIntegerBetween()
    {
        assertThatThrownBy(() -> RandomNumberGenerator.nextInt(200, 100))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Generate a pseudo-random long number")
    final void testGenerateRandomLong()
    {
        assertThat(RandomNumberGenerator.nextLong(Long.MAX_VALUE)).isPositive();
    }

    @RepeatedTest(value = 100)
    @DisplayName("Generate a pseudo-random long number given a min and max")
    final void testGenerateRandomLongBetween()
    {
        final long MIN = 100;
        final long MAX = 200;

        long value = RandomNumberGenerator.nextLong(MIN, MAX);

        LOGGER.debug(String.format("Random number generated: '%s', min bound: '%s', max bound: '%s'", value, MIN, MAX));

        assertThat(value)
                .isGreaterThanOrEqualTo(MIN)
                .isLessThanOrEqualTo(MAX);
    }

    @Test
    @DisplayName("Cannot generate a pseudo-random long number if min bound is less than max bound")
    final void testCannotGenerateRandomLongBetween()
    {
        assertThatThrownBy(() -> RandomNumberGenerator.nextLong(200l, 100l))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Generate a pseudo-random double number")
    final void testGenerateRandomDouble()
    {
        assertThat(RandomNumberGenerator.nextDouble(Double.MAX_VALUE)).isPositive();
    }

    @RepeatedTest(value = 100)
    @DisplayName("Generate a pseudo-random double number given a min and max")
    final void testGenerateRandomDoubleBetween()
    {
        final double MIN = 1.0d;
        final double MAX = 2.0d;

        double value = RandomNumberGenerator.nextDouble(MIN, MAX);

        LOGGER.debug(String.format("Random number generated: '%s', min bound: '%s', max bound: '%s'", value, MIN, MAX));

        assertThat(value)
                .isGreaterThanOrEqualTo(MIN)
                .isLessThanOrEqualTo(MAX);
    }

    @Test
    @DisplayName("Cannot generate a pseudo-random double number if min bound is less than max bound")
    final void testCannotGenerateRandomDoubleBetween()
    {
        assertThatThrownBy(() -> RandomNumberGenerator.nextDouble(3.0d, 2.0d))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Generate a pseudo-random float number")
    final void testGenerateRandomFloat()
    {
        assertThat(RandomNumberGenerator.nextFloat(Float.MAX_VALUE)).isPositive();
    }

    @RepeatedTest(value = 100)
    @DisplayName("Generate a pseudo-random float number given a min and max")
    final void testGenerateRandomFloatBetween()
    {
        final float MIN = 5.0f;
        final float MAX = 10.0f;

        float value = RandomNumberGenerator.nextFloat(MIN, MAX);

        LOGGER.debug(String.format("Random float generated: '%s', min bound: '%s', max bound: '%s'", value, MIN, MAX));

        assertThat(value)
                .isGreaterThanOrEqualTo(MIN)
                .isLessThanOrEqualTo(MAX);
    }

    @Test
    @DisplayName("Cannot generate a pseudo-random float number if min bound is less than max bound")
    final void testCannotGenerateRandomFloatBetween()
    {
        assertThatThrownBy(() -> RandomNumberGenerator.nextFloat(2.0f, 1.0f))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @RepeatedTest(value = 100)
    @DisplayName("Generate a pseudo-random boolean value")
    final void testGenerateRandomBoolean()
    {
        assertThat(RandomNumberGenerator.nextBoolean()).isIn(Boolean.TRUE, Boolean.FALSE);
    }
}
