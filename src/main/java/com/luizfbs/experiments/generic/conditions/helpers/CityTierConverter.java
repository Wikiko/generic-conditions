package com.luizfbs.experiments.generic.conditions.helpers;

import com.luizfbs.experiments.generic.conditions.models.Sample;

public class CityTierConverter implements Converter {

    @Override
    public Object execute(Object object) {
        Sample sample = (Sample) object;
        if(sample.getCity().equalsIgnoreCase("Nova Odessa") && sample.getState().equalsIgnoreCase("SP")){
            return "tier 1";
        }
        return "tier 2";
    }
    
}
