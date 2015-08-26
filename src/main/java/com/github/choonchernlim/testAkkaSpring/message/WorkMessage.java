package com.github.choonchernlim.testAkkaSpring.message;

public final class WorkMessage {
    private final String value;

    public WorkMessage(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
