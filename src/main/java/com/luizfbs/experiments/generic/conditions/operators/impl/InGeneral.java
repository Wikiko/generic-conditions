package com.luizfbs.experiments.generic.conditions.operators.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InGeneral {
    private static String COMMA_SEPARATOR = ",";

    public static boolean evaluate(Object value, Object expectedValue) {
        // fazer com que todo metodo espere receber não nulo, essa validação deve estar
        // externa aos metodos de comparação.
        if (!(value instanceof String)) {
            System.out.println("Objeto não é uma string, não deve funcionar com IN");
            return false;
        }
        Set<String> matchValues = Arrays.stream(((String) value).split(COMMA_SEPARATOR))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        if(expectedValue instanceof String expectedString) {
            return matchValues.contains(expectedString);
        }

        if (expectedValue instanceof List<?> expectedList && !expectedList.isEmpty()
                && expectedList.get(0) instanceof String) {

            return expectedList.stream()
                    .map(String::valueOf)
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .anyMatch(matchValues::contains);
        }

        return false;
    }
}
