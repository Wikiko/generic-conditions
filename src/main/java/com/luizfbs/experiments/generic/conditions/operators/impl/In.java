package com.luizfbs.experiments.generic.conditions.operators.impl;

import com.luizfbs.experiments.generic.conditions.helpers.ReflectionHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class In implements OperatorBase {
    private static String COMMA_SEPARATOR = ",";

    public static boolean evaluate(Object input, String name, Object expected) {
        List<String> value = (List<String>) ReflectionHelper.getValue(input, name);

        if (Objects.isNull(value)) {
            return false;
        }

        Set<String> matchValues = Arrays.stream(String.valueOf(expected).split(COMMA_SEPARATOR))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        return value
                .stream()
                .map(String::trim)
                .map(String::toUpperCase)
                .anyMatch(matchValues::contains);
    }
}