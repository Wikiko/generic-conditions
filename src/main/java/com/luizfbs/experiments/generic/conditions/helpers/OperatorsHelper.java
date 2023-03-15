package com.luizfbs.experiments.generic.conditions.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators;
import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators2;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;

public class OperatorsHelper {
    private final Map<String, Field> fields;
    private final Map<String, Map<String, Operator2>> operators;

    public OperatorsHelper(Class<?> clazz) {
        Supplier<Stream<Field>> fieldsAnnotated = () -> Arrays.stream(clazz.getDeclaredFields())
        .filter(declaredField -> declaredField.isAnnotationPresent(ConfigureOperators2.class))
        .peek(declaredField -> declaredField.setAccessible(true));

        fields = fieldsAnnotated.get()
        .collect(Collectors.toMap(this::getNameToField, Function.identity()));

        operators = fieldsAnnotated.get()
        .collect(Collectors.toMap(this::getNameToField, declaredField -> {
            ConfigureOperators2 config = declaredField.getDeclaredAnnotation(ConfigureOperators2.class);
            return Arrays.stream(config.Operators())
            .collect(Collectors.toMap(Enum::name, Function.identity()));
        }));
    }

    private String getNameToField(Field field) {
        ConfigureOperators2 config = field.getDeclaredAnnotation(ConfigureOperators2.class);
        return config.name().isEmpty() ? field.getName() : config.name();
    }

    public Object getValue(Object object, String property) throws IllegalArgumentException, IllegalAccessException {
        return fields.get(property).get(object);
    }

    public Operator2 getOperator(String fieldName, String operatorName) {
        return operators.get(fieldName).get(operatorName);
    }

    public Class<Converter> getConverter(String fieldName) {
        ConfigureOperators2 config = fields.get(fieldName).getDeclaredAnnotation(ConfigureOperators2.class);
        try {
            return (Class<Converter>) config.converter();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
