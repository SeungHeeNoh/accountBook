package com.sweethome.accountbook.domain;

import java.util.Arrays;

public enum TransactionType {
    ALL(0), DEPOSIT(1), WITHDRAWAL(2);

    private final int code;

    TransactionType(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TransactionType fromCode(int code) {
        return Arrays.stream(TransactionType.values()).filter(type -> type.getCode() == code)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
    }
}
