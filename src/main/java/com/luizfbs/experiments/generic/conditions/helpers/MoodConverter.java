package com.luizfbs.experiments.generic.conditions.helpers;

import com.luizfbs.experiments.generic.conditions.models.Sample;

public class MoodConverter implements Converter {

    @Override
    public Object execute(Object object) {
        Sample sample = (Sample) object;
        if(sample.hasTargetItem()) {
            return "HAPPY";
        }
        return "SAD";
    }
    
}
