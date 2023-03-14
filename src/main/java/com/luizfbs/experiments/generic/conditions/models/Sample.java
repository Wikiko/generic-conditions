package com.luizfbs.experiments.generic.conditions.models;

import java.math.BigDecimal;
import java.util.List;

import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators;
import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators2;
import com.luizfbs.experiments.generic.conditions.operators.Operator;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;

public class Sample {
    @ConfigureOperators(Operators = {Operator.EQUAL_TO, Operator.NOT_EQUAL_TO})
    @ConfigureOperators2(Operators = {Operator2.EQUAL_TO})
    private String channel;

    @ConfigureOperators2(Operators = {Operator2.IN})
    private List<String> tags;

    @ConfigureOperators2(Operators = {Operator2.EQUAL_TO})
    private BigDecimal price;

    public Sample(String channel, List<String> tags, BigDecimal price) {
        this.channel = channel;
        this.tags = tags;
        this.price = price;
    }

    public String getChannel() {
        return this.channel;
    }

    public List<String> getTags() {
        return this.tags;
    }
}