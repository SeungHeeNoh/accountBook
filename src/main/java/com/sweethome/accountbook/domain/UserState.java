package com.sweethome.accountbook.domain;

import java.util.Arrays;

public enum UserState {
    ACTIVE(0),
    INACTIVE(1)
    ;

    private int value;

    UserState(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserState fromValue(int value) {
        return Arrays.stream(UserState.values()).filter(state -> state.getValue() == value)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid code: " + value));
    }
}
