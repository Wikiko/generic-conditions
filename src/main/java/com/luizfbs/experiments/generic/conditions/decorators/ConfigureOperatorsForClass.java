package com.luizfbs.experiments.generic.conditions.decorators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.luizfbs.experiments.generic.conditions.helpers.Converter;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(value = SettingsOperators.class)
public @interface ConfigureOperatorsForClass {
    String name();
    Operator2[] Operators();
    Class<? extends Converter> converter();
}
