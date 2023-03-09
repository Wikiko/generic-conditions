package com.luizfbs.experiments.generic.conditions;

import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators;
import com.luizfbs.experiments.generic.conditions.operators.Operator;

import java.lang.reflect.Field;
import java.util.List;

public class Condition {
    @ConfigureOperators(Operators = {Operator.IN, Operator.NOT_IN, Operator.EQUAL_TO, Operator.NOT_EQUAL_TO})
    public static String TAGS = "tags";
    public static String CHANNEL = "channel";

    public static Field getCondition(String conditionName) {
        try {
            return Condition.class.getField(conditionName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static String getConditionName(String conditionName) {
        Field condition = getCondition(conditionName);
        if(condition == null) {
            return null;
        }
        return condition.getName();
    }

    public static Object getConditionReference(String conditionName) {
        try {
            Field condition = getCondition(conditionName);
            if(condition == null) {
                return null;
            }
            return condition.get(null);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public static List<Operator> getOperators(String conditionName){
        try {
            Field condition = Condition.class.getField(conditionName);
            Operator[] operators = condition.getAnnotation(ConfigureOperators.class).Operators();
            return List.of(operators);
        } catch(NoSuchFieldException exception) {
            return List.of();
        }
    }

    public static Operator getOperator(String conditionName, String operatorName) {
        List<Operator> operators = getOperators(conditionName);
        return operators.stream().filter(op -> op.name().equals(operatorName))
                                    .findFirst()
                                        .orElseGet(null);
    }
}