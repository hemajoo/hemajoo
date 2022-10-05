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
     * Extract variables found in a given text string (a variable has the format: <code>${variable_name}</code>).
     * @param text Text containing variables.
     * @return List of variable names or null if no variable has been found.
     */
    public static List<String> extractVariables(final @NonNull String text)
    {
        List<String> variables = new ArrayList<>();

        final String startPattern = "${";
        final String endPattern = "}";

        int index = -1;
        int start;
        int end;

        while (index < text.length())
        {
            start = text.indexOf(startPattern, index);
            if (start >= index)
            {
                end = text.indexOf(endPattern, start);
                if (end > start)
                {
                    variables.add(text.substring(start + startPattern.length(), end));
                }

                index = end;
            }
            else
            {
                index = text.length();
            }
        }

        return variables;
    }

    /**
     * Return if the given text contains variables.
     * @param text String to check.
     * @return True if the given text contains some variables, false otherwise.
     */
    public static boolean containsVariable(final @NonNull String text)
    {
        final String startPattern = "${";

        int index = text.indexOf(startPattern);

        return index >= 0;
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
        Object value;
        Field field;
        String result = text;
        Class<?> declaringClass;
        Method method;

        if (!containsVariable(text))
        {
            return text;
        }

        for (String name : extractVariables(text))
        {
            if (name.equals("this") && instance.getClass().isEnum())
            {
                result = StringExpander.expandVariable(result, name, ((Enum<?>) instance).name());
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
                    result = StringExpander.expandVariable(result, name, (String) value);
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
     * Expand/replace the given variable with a value in a given string.
     * @param source String containing the variable to expand/replace.
     * @param variable Variable to expand.
     * @param value Value for the variable to expand.
     * @return Expanded string.
     */
    private static String expandVariable(final @NonNull String source, final @NonNull String variable, final @NonNull String value)
    {
        String pattern = ("${" + variable + "}");
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
}
