package com.luizfbs.experiments.generic.conditions.operators;

public class EqualToGeneral {

    public static <T> boolean evaluate(T value, T expectedValue) {
        // fazer com que todo metodo espere receber não nulo, essa validação deve estar externa aos metodos de comparação.
        if (value instanceof String) {
            return ((String) value).equalsIgnoreCase((String) expectedValue);
        }

        return value.equals(expectedValue);
    }

}
