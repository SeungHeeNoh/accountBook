package com.sweethome.accountbook.domain;

import java.util.Arrays;

public enum LogState {
    ACTIVE(0),
    INACTIVE(1)
    ;

    private int value;

    LogState(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static LogState fromValue(int value) {
        return Arrays.stream(LogState.values()).filter(state -> state.getValue() == value)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid value: " + value));
    }
}
