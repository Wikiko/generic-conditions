package com.luizfbs.experiments.generic.conditions.helpers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators2;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;

public class OperatorsHelper {
    private final Map<String, Field> fieldsByName;
    private final Map<String, Map<String, Operator2>> operatorsByField;
    private final Map<String, Class<? extends Converter>> convertersByFied;
    // private final String clazzOrAnnotationName;

    public OperatorsHelper(Class<?> clazz) {
        Supplier<Stream<Field>> fieldsAnnotated = () -> Arrays.stream(clazz.getDeclaredFields())
        .filter(declaredField -> declaredField.isAnnotationPresent(ConfigureOperators2.class))
        .peek(declaredField -> declaredField.setAccessible(true));

        fieldsByName = fieldsAnnotated.get()
        .collect(Collectors.toMap(this::getNameToField, Function.identity()));

        operatorsByField = fieldsAnnotated.get()
        .collect(Collectors.toMap(this::getNameToField, declaredField -> {
            ConfigureOperators2 config = declaredField.getDeclaredAnnotation(ConfigureOperators2.class);
            return Arrays.stream(config.Operators())
            .collect(Collectors.toMap(Enum::name, Function.identity()));
        }));

        convertersByFied = fieldsAnnotated.get()
        .filter(declaredField -> declaredField.getAnnotation(ConfigureOperators2.class).converter() != null)
        .collect(Collectors.toMap(this::getNameToField, declaredField -> {
            ConfigureOperators2 config = declaredField.getDeclaredAnnotation(ConfigureOperators2.class);
            return config.converter();
        }));

        if (clazz.isAnnotationPresent(ConfigureOperators2.class)) { //todo maybe change to use a specific annotation
            // to class, turning name required instead of optional.
            ConfigureOperators2 clazzConfig = clazz.getAnnotation(ConfigureOperators2.class);
            Map<String, Operator2> clazzOperators = Arrays.stream(clazzConfig.Operators())
                .collect(Collectors.toMap(Enum::name, Function.identity()));
            String name = clazzConfig.name().isEmpty() ? clazz.getName() : clazzConfig.name();
            operatorsByField.put(name, clazzOperators);
            convertersByFied.put(name, clazzConfig.converter());
        }
    }

    private String getNameToField(Field field) {
        ConfigureOperators2 config = field.getDeclaredAnnotation(ConfigureOperators2.class);
        return config.name().isEmpty() ? field.getName() : config.name();
    }

    public Object getValue(Object object, String property) throws IllegalArgumentException, IllegalAccessException {
        if(object.getClass().isAnnotationPresent(ConfigureOperators2.class)){
            ConfigureOperators2 objConfigureOperators2 = object.getClass().getAnnotation(ConfigureOperators2.class);
            if(objConfigureOperators2.name().equalsIgnoreCase(property) || object.getClass().getName().equalsIgnoreCase(property)){
                return object;
            }
        }
        return fieldsByName.get(property).get(object);
    }

    public Operator2 getOperator(String fieldName, String operatorName) {
        return operatorsByField.get(fieldName).get(operatorName);
    }

    public Class<? extends Converter> getConverter(String fieldName) {
        return convertersByFied.get(fieldName);
    }
}
