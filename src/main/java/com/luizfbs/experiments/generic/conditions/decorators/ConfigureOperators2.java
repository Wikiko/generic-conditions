package com.luizfbs.experiments.generic.conditions.decorators;

import com.luizfbs.experiments.generic.conditions.helpers.Converter;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface ConfigureOperators2 {
    String name() default "";
    Operator2[] Operators();
    Class<? extends Converter> converter() default Converter.class;
}
