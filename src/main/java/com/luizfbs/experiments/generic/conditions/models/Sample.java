package com.luizfbs.experiments.generic.conditions.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;

import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators;
import com.luizfbs.experiments.generic.conditions.decorators.ConfigureOperators2;
import com.luizfbs.experiments.generic.conditions.helpers.Converter;
import com.luizfbs.experiments.generic.conditions.helpers.HasTargetItemConverter;
import com.luizfbs.experiments.generic.conditions.operators.Operator;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;

public class Sample {
    @ConfigureOperators(Operators = {Operator.EQUAL_TO, Operator.NOT_EQUAL_TO})
    @ConfigureOperators2(Operators = {Operator2.EQUAL_TO})
    private String channel;

    @ConfigureOperators2(Operators = {Operator2.IN}, name = "merchant_tags")
    private List<String> tags;

    @ConfigureOperators2(Operators = {Operator2.EQUAL_TO}, converter = Converter.class)
    private BigDecimal price;

    @ConfigureOperators2(Operators = {Operator2.EQUAL_TO}, converter = HasTargetItemConverter.class)
    private boolean hasTargetItem;

    public Sample(String channel, List<String> tags, BigDecimal price, boolean hasTargetItem) {
        this.channel = channel;
        this.tags = tags;
        this.price = price;
        this.hasTargetItem = hasTargetItem;
    }

    public String getChannel() {
        return this.channel;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public boolean hasTargetItem() {
        return this.hasTargetItem;
    }
}