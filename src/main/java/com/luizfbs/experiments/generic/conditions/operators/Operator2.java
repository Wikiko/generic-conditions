package com.luizfbs.experiments.generic.conditions.operators;

import java.util.function.BiFunction;

public enum Operator2 {
    EQUAL_TO((Object value, Object expected) -> value.equals(expected)),
    NOT_EQUAL_TO((Object value, Object expected) -> false);

    public final BiFunction<Object, Object, Boolean> evaluate;

    Operator2(BiFunction<Object, Object, Boolean> evaluate) {
        this.evaluate = evaluate;
    }
}
