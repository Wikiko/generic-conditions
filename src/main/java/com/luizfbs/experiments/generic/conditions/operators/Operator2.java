package com.luizfbs.experiments.generic.conditions.operators;

import java.util.function.BiFunction;

import com.luizfbs.experiments.generic.conditions.operators.impl.InGeneral;

public enum Operator2 {
    EQUAL_TO(EqualToGeneral::evaluate),
    IN(InGeneral::evaluate);

    private final BiFunction<Object, Object, Boolean> evaluate;

    <T> Operator2(BiFunction<Object, Object, Boolean> evaluate) {
        this.evaluate = evaluate;
    }

    public boolean apply(Object value, Object expected) {
        return this.evaluate.apply(value, expected);
    }
}
