package com.luizfbs.experiments.generic.conditions.helpers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators2;
import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperatorsForClass;
import com.luizfbs.experiments.generic.conditions.decorators.SettingsOperators;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;

public class OperatorsHelper {
    private final Map<String, Field> fieldsByName;
    private final Map<String, Map<String, Operator2>> operatorsByField;
    private final Map<String, Class<? extends Converter>> convertersByFied;
    private final Set<String> classAnnotationsNames;

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

        ConfigureOperatorsForClass[] clazzConfigs = clazz.getAnnotationsByType(ConfigureOperatorsForClass.class);

        if (clazzConfigs.length > 0) { // todo maybe change to use a specific
                                       // annotation
            // to class, turning name required instead of optional.
            // if we decide to have N ConfigureOperators2 per class, we can change it to
            // clazz.getAnnotationsByType
            // and fill operatorsByField and convertersByField using the same idea.

            classAnnotationsNames = Arrays.stream(clazzConfigs)
                    .map(ConfigureOperatorsForClass::name)
                    .collect(Collectors.toSet());

            var clazzOperatorsByName = Arrays.stream(clazzConfigs).collect(Collectors.toMap(
                    ConfigureOperatorsForClass::name,
                    clazzConfig -> {
                        Map<String, Operator2> clazzOperators = Arrays.stream(clazzConfig.Operators())
                                .collect(Collectors.toMap(Enum::name, Function.identity()));
                        return clazzOperators;
                    }));

            operatorsByField.putAll(clazzOperatorsByName);

            var clazzConvertersByName = Arrays.stream(clazzConfigs).collect(Collectors.toMap(
                    ConfigureOperatorsForClass::name,
                    clazzConfig -> clazzConfig.converter()));

            convertersByFied.putAll(clazzConvertersByName);
        } else {
            classAnnotationsNames = Collections.emptySet();
        }
    }

    private String getNameToField(Field field) {
        ConfigureOperators2 config = field.getDeclaredAnnotation(ConfigureOperators2.class);
        return config.name().isEmpty() ? field.getName() : config.name();
    }

    public Object getValue(Object object, String property) throws IllegalArgumentException, IllegalAccessException {
        if(classAnnotationsNames.contains(property)){
            return object;
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
