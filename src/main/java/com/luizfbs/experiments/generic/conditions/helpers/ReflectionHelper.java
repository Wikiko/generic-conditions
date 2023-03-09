package com.luizfbs.experiments.generic.conditions.helpers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators;
import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators2;
import com.luizfbs.experiments.generic.conditions.models.Sample;
import com.luizfbs.experiments.generic.conditions.operators.Operator;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;

public class ReflectionHelper {
    public static Object getValue(Object object, String property) {
        try {
            Field field = object.getClass().getDeclaredField(property);
            field.setAccessible(true);
            Object value = field.get(object);
            field.setAccessible(false);
            return value;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    public static List<Operator> getOperatorsOfSample(String fieldName) {
        Field field;
        try {
            field = Sample.class.getDeclaredField(fieldName);
            Operator[] operators = field.getDeclaredAnnotation(ConfigureOperators.class).Operators();
            return List.of(operators);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return List.of();
        }
    }

    public static Optional<Operator> getOperatorOfSample(String fieldName, String operatorName) {
        List<Operator> operators = getOperatorsOfSample(fieldName);
        return operators.stream()
                .filter(operator -> operator.name().equalsIgnoreCase(operatorName))
                .findAny();
    }

    public static List<Operator2> getOperators2OfSample(String fieldName) {
        Field field;
        try {
            field = Sample.class.getDeclaredField(fieldName);
            Operator2[] operators = field.getDeclaredAnnotation(ConfigureOperators2.class).Operators();
            return List.of(operators);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return List.of();
        }
    }

    public static Optional<Operator2> getOperator2OfSample(String fieldName, String operatorName) {
        List<Operator2> operators = getOperators2OfSample(fieldName);
        return operators.stream()
                .filter(operator -> operator.name().equalsIgnoreCase(operatorName))
                .findAny();
    }
}
