package com.luizfbs.experiments.generic.conditions.models;

import java.util.List;

import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators;
import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators2;
import com.luizfbs.experiments.generic.conditions.operators.Operator;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;

public class Sample {
    @ConfigureOperators(Operators = {Operator.EQUAL_TO, Operator.NOT_EQUAL_TO})
    @ConfigureOperators2(Operators = {Operator2.EQUAL_TO, Operator2.NOT_EQUAL_TO})
    private String channel;
    private List<String> tags;

    public Sample(String channel, List<String> tags) {
        this.channel = channel;
        this.tags = tags;
    }

    public String getChannel() {
        return this.channel;
    }

    public List<String> getTags() {
        return this.tags;
    }
}