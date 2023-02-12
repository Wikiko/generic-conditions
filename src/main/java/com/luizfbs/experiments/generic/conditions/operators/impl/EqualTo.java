package com.luizfbs.experiments.generic.conditions.operators.impl;

import com.luizfbs.experiments.generic.conditions.helpers.ReflectionHelper;

public class EqualTo implements OperatorBase {
    public static boolean evaluate(Object input, String name, Object expected) {
        Object value = ReflectionHelper.getValue(input, name);
        return value.equals(expected);
    }
}