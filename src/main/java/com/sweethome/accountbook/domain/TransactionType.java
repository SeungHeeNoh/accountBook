package com.sweethome.accountbook.domain;

import java.util.Arrays;

public enum TransactionType {
    ALL(0, "ALL"), DEPOSIT(1, "입금"), WITHDRAWAL(2, "출금");

    private final int code;
    private final String title;

    TransactionType(final int code, final String title) {
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public static TransactionType fromCode(int code) {
        return Arrays.stream(TransactionType.values()).filter(type -> type.getCode() == code)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
    }
}
