/*
 * (C) Copyright Hemajoo Systems Inc.  2022 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.utility.string;

import com.hemajoo.utility.reflection.ReflectionHelper;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class providing convenient services for manipulating variables contained in <b>strings</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public class StringExpander
{
    /**
     * Default pattern character specifying a variable in a string.
     */
    private static final char DEFAULT_PATTERN_CHARACTER = '$';

    /**
     * Default pattern additional character specifying the beginning of variable name in a string.
     */
    private static final char ENCLOSING_START_CHARACTER = '{';

    /**
     * Default pattern additional character specifying the end of variable name in a string.
     */
    private static final char ENCLOSING_END_CHARACTER = '}';

//    /**
//     * Extract variables found in a given text string (a variable has the format: <code>${variable_name}</code>).
//     * @param text Text containing variables.
//     * @return List of variable names or null if no variable has been found.
//     */
//    public static List<String> extractVariables(final @NonNull String text)
//    {
//        List<String> variables = new ArrayList<>();
//
//        final String startPattern = DEFAULT_PATTERN_CHARACTER + String.valueOf(ENCLOSING_START_CHARACTER);
//
//        int index = -1;
//        int start;
//        int end;
//
//        while (index < text.length())
//        {
//            start = text.indexOf(startPattern, index);
//            if (start >= index)
//            {
//                end = text.indexOf(ENCLOSING_END_CHARACTER, start);
//                if (end > start)
//                {
//                    variables.add(text.substring(start + startPattern.length(), end));
//                }
//
//                index = end;
//            }
//            else
//            {
//                index = text.length();
//            }
//        }
//
//        return variables;
//    }

    /**
     * Return if the given text contains variables.
     * @param text String to check.
     * @return True if the given text contains some variables, false otherwise.
     */
    public static boolean containsVariable(final @NonNull String text)
    {
        final String startPattern = DEFAULT_PATTERN_CHARACTER + String.valueOf(ENCLOSING_START_CHARACTER);

        int index = text.indexOf(startPattern);

        return index >= 0;
    }

    /**
     * Return if the given text contains variables.
     * @param text String to check.
     * @param pattern Pattern character.
     * @return True if the given text contains some variables, false otherwise.
     */
    public static boolean containsVariable(final @NonNull String text, final char pattern)
    {
        int index = text.indexOf(pattern + String.valueOf(ENCLOSING_START_CHARACTER));

        return index >= 0;
    }

    /**
     * Return the number of variables found in a given text.
     * @param text String to check.
     * @return Number of variables found.
     */
    public static int count(final @NonNull String text)
    {
        return count(text, DEFAULT_PATTERN_CHARACTER);
    }

    /**
     * Return the number of variables found in a given text.
     * @param text String to check.
     * @return Number of variables found.
     */
    public static int count(final @NonNull String text, final char pattern)
    {
        int count = 0;
        int offset = 0;

        while (true)
        {
            offset = findNextOccurrence(text, offset, pattern + String.valueOf(ENCLOSING_START_CHARACTER));
            if (offset < 0)
            {
                return count;
            }

            count ++;
            offset = computeOffset(text, offset);
        }
    }

    /**
     * Find the next occurrence of a given pattern in a string.
     * @param text Text.
     * @param offset Offset.
     * @param pattern Pattern.
     * @return Index of the occurrence, -1 if not found.
     */
    private int findNextOccurrence(String text, int offset, String pattern)
    {
        if (offset +1 > text.length())
        {
            return -1;
        }

        return text.indexOf(pattern, offset);
    }

    /**
     * Compute the offset of the position of the '}' character in a string.
     * @param text Text.
     * @param offset Offset to start from.
     * @return Offset.
     */
    private int computeOffset(String text, int offset)
    {
        return text.indexOf(ENCLOSING_END_CHARACTER, offset);
    }

    /**
     * Expand/replace variables with values in a given string.
     * @param instance Object instance containing the real values.
     * @param text Text containing the variables to be replaced/expanded by real variable values.
     * @return Expanded text.
     * @throws StringExpanderException Thrown to indicate an error occurred while trying to expand a string.
     */
    public static String expandVariables(final Object instance, final @NonNull String text) throws StringExpanderException
    {
        return expandVariables(DEFAULT_PATTERN_CHARACTER, instance, text);
    }

    /**
     * Expand/replace variables with values in a given string.
     * @param characterPattern Character used for variable pattern (ex.: standard is $ -> ${variable} but you are free to use another one).
     * @param instance Object instance containing the real values.
     * @param text Text containing the variables to be replaced/expanded by real variable values.
     * @return Expanded text.
     * @throws StringExpanderException Thrown to indicate an error occurred while trying to expand a string.
     */
    public static String expandVariables(final char characterPattern, final Object instance, final @NonNull String text) throws StringExpanderException
    {
        Object value;
        Field field;
        String result = text;
        Class<?> declaringClass;
        Method method;

        if (!containsVariable(text))
        {
            return text;
        }

        for (String name : getVariableNames(characterPattern, text))
        {
            if (name.equals("this") && instance.getClass().isEnum())
            {
                result = StringExpander.expandByName(characterPattern, result, name, ((Enum<?>) instance).name());
            }
            else
            {
                try
                {
                    field = ReflectionHelper.findFieldInObjectInstance(instance, name);
                    declaringClass = instance.getClass();
                    method = declaringClass.getMethod("get" + StringUtils.capitalize(field.getName()));
                    value = method.invoke(instance);
                    if (value instanceof Enum<?>)
                    {
                        value = ((Enum<?>) value).name();
                    }
                    result = StringExpander.expandByName(result, name, (String) value);
                }
                catch (Exception e)
                {
                    throw new StringExpanderException(e);
                }
            }
        }

        return result;
    }

    /**
     * Expand a variable given its name and a value.
     * @param source String containing the variable to expand.
     * @param index Variable index.
     * @param value Variable value.
     * @return String with expanded variable.
     */
    public static String expandByIndex(final @NonNull String source, final int index, final @NonNull String value)
    {
        return expandByIndex(DEFAULT_PATTERN_CHARACTER, source, index, value);
    }

    /**
     * Expand a variable given its name and a value.
     * @param character Character used for variable pattern (ex.: standard is $ -> ${variable} but you are free to use another one).
     * @param source String containing the variable to expand.
     * @param index Variable index.
     * @param value Variable value.
     * @return String with expanded variable.
     */
    public static String expandByIndex(final char character, final @NonNull String source, final int index, final @NonNull String value)
    {
        String pattern = character + String.valueOf(ENCLOSING_START_CHARACTER) + getVariableName(character, source, index) + ENCLOSING_END_CHARACTER;
        return source.replace(pattern, value);
    }

    /**
     * Expand a variable given its name and a value.
     * @param source String containing the variable to resolve.
     * @param variable Variable name.
     * @param value Variable value.
     * @return String with expanded variable.
     */
    public static String expandByName(final @NonNull String source, final @NonNull String variable, final @NonNull String value)
    {
        return expandByName(DEFAULT_PATTERN_CHARACTER, source, variable, value);
    }

    /**
     * Expand a variable given its name and value.
     * @param character Character used for variable pattern (ex.: standard is $ -> ${variable} but you are free to use another one).
     * @param source String containing the variable to resolve.
     * @param variable Variable name.
     * @param value Variable value.
     * @return String with expanded variable.
     */
    public static String expandByName(final char character, final @NonNull String source, final @NonNull String variable, final @NonNull String value)
    {
        String pattern = character + String.valueOf(ENCLOSING_START_CHARACTER) + variable + ENCLOSING_END_CHARACTER;
        return source.replace(pattern, value);
    }

    /**
     * Return the field name given its getter method name.
     * @param getterName Getter method name.
     * @return Field nane.
     */
    public static String getFieldNameFromGetter(final @NonNull String getterName)
    {
        String result = getterName;

        int index;

        index = result.indexOf("get");
        if (index < 0)
        {
            index = result.indexOf("is");
            if (index < 0)
            {
                return null;
            }
            else
            {
                result = result.replaceFirst("is", "");
            }
        }
        else
        {
            result = result.replaceFirst("get", "");
        }

        result = result.substring(0, 1).toLowerCase() + result.substring(1);

        return result;
    }

    /**
     * Return the name of the variable found at the given index.
     * @param characterPattern Character pattern.
     * @param text Text containing the variable(s).
     * @param index Index of the variable.
     * @return Name of the variable if found, otherwise an {@link IllegalArgumentException} is thrown.
     */
    private static String getVariableName(final char characterPattern, final @NonNull String text, final int index)
    {
        List<String> variables = getVariableNames(characterPattern, text);

        if (index > 0 && index <= variables.size())
        {
            return variables.get(index - 1);
        }

        throw new IllegalArgumentException(String.format("Cannot find variable name at index: '%s' from: '%s'", index, text));
    }

    /**
     * Return a list of variables contained in a string.
     * @param text Text containing the variable(s).
     * @return List of variables if some have been found, otherwise an empty list.
     */
    public static List<String> getVariableNames(final @NonNull String text)
    {
        return getVariableNames(DEFAULT_PATTERN_CHARACTER, text);
    }

    /**
     * Return a list of variables contained in a string.
     * @param characterPattern Character pattern.
     * @param text Text containing the variable(s).
     * @return List of variables if some have been found, otherwise an empty list.
     */
    public static List<String> getVariableNames(final char characterPattern, final @NonNull String text)
    {
        int startOffset = 0;
        int offset = 0;
        List<String> variables = new ArrayList<>();

        while (true)
        {
            offset = findNextOccurrence(text, offset, characterPattern + String.valueOf(ENCLOSING_START_CHARACTER));
            if (offset < 0)
            {
                return variables;
            }

            startOffset = offset + 2;
            offset = computeOffset(text, offset);
            variables.add(text.substring(startOffset, offset));
        }
    }
}
