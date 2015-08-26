package com.github.choonchernlim.testAkkaSpring.message;

public final class ResultMessage {
    private final String value;

    public ResultMessage(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
