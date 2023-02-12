package com.luizfbs.experiments.generic.conditions.helpers;

import java.lang.reflect.Field;

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
}
