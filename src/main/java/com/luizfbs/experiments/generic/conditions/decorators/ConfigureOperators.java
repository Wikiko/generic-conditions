package com.luizfbs.experiments.generic.conditions.decorators;

import com.luizfbs.experiments.generic.conditions.operators.Operator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigureOperators {
    Operator[] Operators();
}
