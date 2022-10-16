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
package com.hemajoo.utility.test.string;

import com.hemajoo.utility.string.StringExpander;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class StringExpanderUnitTest
{
    @ValueSource(strings = {
            "spring.datasource.${database-name}",
            "spring.datasource.${database-name}.${environment}"
    })
    @ParameterizedTest
    @DisplayName("Check a string contains some variables?")
    final void testCheckContainsVariable(final @NonNull String source)
    {
        assertThat(StringExpander.containsVariable(source)).isTrue();
    }

    @ValueSource(strings = {
            "spring.datasource.database-name",
            "spring.datasource.database-name.environment"
    })
    @ParameterizedTest
    @DisplayName("Check a string does not contain variables?")
    final void testCheckDoesNotContainVariable(final @NonNull String source)
    {
        assertThat(StringExpander.containsVariable(source)).isFalse();
    }

    @CsvSource(value = {
            "spring.datasource.@{database-name}.@{environment};@;2",
            "spring.datasource.@{database-name}.@{environment}.@{name}.feature.option.@{title}.@{menu}.@{option};@;6",
            "spring.datasource.!{database-name}.!{environment};!;2",
            "spring.datasource.#{database-name}.#{environment};#;2",
            "spring.datasource.database-name.environment;@;0",
            "spring.datasource.${database-name}.${environment};!;0",
            "spring.datasource.${database-name}.#{environment}.@{name};§;0",
    }, delimiter = ';')
    @ParameterizedTest
    @DisplayName("Count the number of variables in a string?")
    final void testCountVariable(final @NonNull String source, final char pattern, final int expected)
    {
        assertThat(StringExpander.count(source, pattern)).isEqualTo(expected);
    }

    @CsvSource(value = {
            "spring.datasource.${database-name}.${environment};$;database-name;test-db;spring.datasource.test-db.${environment}",
            "spring.datasource.@{database-name}.@{environment};@;environment;dev;spring.datasource.@{database-name}.dev",
    }, delimiter = ';')
    @ParameterizedTest
    @DisplayName("Expand a variable by its name")
    final void testExpandVariableByName(final @NonNull String source, final char pattern, final @NonNull String variableName, final @NonNull String variableValue, final @NonNull String target)
    {
        assertThat(StringExpander.expandByName(pattern, source, variableName, variableValue)).isEqualTo(target);
    }

    @CsvSource(value = {
            "spring.datasource.${database-name}.${environment};$;1;test-db;spring.datasource.test-db.${environment}",
            "spring.datasource.@{database-name}.@{environment};@;2;dev;spring.datasource.@{database-name}.dev",
    }, delimiter = ';')
    @ParameterizedTest
    @DisplayName("Expand a variable by its index")
    final void testExpandVariableByIndex(final @NonNull String source, final char pattern, final int index, final @NonNull String variableValue, final @NonNull String target)
    {
        assertThat(StringExpander.expandByIndex(pattern, source, index, variableValue)).isEqualTo(target);
    }

    @CsvSource(value = {
            "spring.datasource.${database-name}.${environment};$;1;test-db;spring.datasource.test-db.${environment}",
            "spring.datasource.@{database-name}.@{environment};@;2;dev;spring.datasource.@{database-name}.dev",
    }, delimiter = ';')
    @ParameterizedTest
    @DisplayName("Expand all variables in a given string")
    final void testExpandVariables(final @NonNull String source, final char pattern, final int index, final @NonNull String variableValue, final @NonNull String target)
    {
        assertThat(StringExpander.expandByIndex(pattern, source, index, variableValue)).isEqualTo(target);
    }
}
