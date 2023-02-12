package com.luizfbs.experiments.generic.conditions.operators;

import com.luizfbs.experiments.generic.conditions.operators.impl.EqualTo;
import com.luizfbs.experiments.generic.conditions.operators.impl.In;
import com.luizfbs.experiments.generic.conditions.operators.impl.Unknown;

import org.assertj.core.util.TriFunction;

public enum Operator {
    EQUAL_TO(EqualTo::evaluate),
    GREATER_THAN(Unknown::evaluate),
    IN(In::evaluate),
    IS(Unknown::evaluate),
    IS_NOT(Unknown::evaluate),
    LESS_THAN(Unknown::evaluate),
    NOT_EQUAL_TO(Unknown::evaluate),
    NOT_IN(Unknown::evaluate);

    public final TriFunction<Object, String, Object, Boolean> evaluate;

    Operator(TriFunction<Object, String, Object, Boolean> evaluate) {
        this.evaluate = evaluate;
    }
}
