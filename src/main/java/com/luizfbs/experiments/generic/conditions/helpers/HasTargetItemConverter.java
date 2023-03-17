package com.luizfbs.experiments.generic.conditions.helpers;

public class HasTargetItemConverter implements Converter {

    @Override
    public Object execute(Object object) {
        if (object instanceof Boolean objectBoolean && objectBoolean.equals(true)) {
            return "ITEM";
        }
        return "";
    }

}
