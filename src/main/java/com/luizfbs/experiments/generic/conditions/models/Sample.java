package com.luizfbs.experiments.generic.conditions.models;

import java.util.List;

public class Sample {
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