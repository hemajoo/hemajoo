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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomNumberGeneratorUnitTest
{
    @Test
    @DisplayName("Generate a pseudo-random integer number")
    final void testCreateRandomInteger()
    {
        assertThat(RandomNumberGenerator.nextInt(Integer.MAX_VALUE)).isPositive();
    }

    @RepeatedTest(value = 100)
    @DisplayName("Generate a pseudo-random integer number given a min and max")
    final void testCreateRandomIntegerBetween()
    {
        final int MIN = 100;
        final int MAX = 200;

        int value = RandomNumberGenerator.nextInt(MIN, MAX);

        assertThat(value)
                .isGreaterThanOrEqualTo(MIN)
                .isLessThanOrEqualTo(MAX);
    }
}
