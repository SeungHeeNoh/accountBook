package com.sweethome.accountbook.common.exception;

import com.sweethome.accountbook.common.Code;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private Code code;

    public SystemException(String msg) {
        super(msg);
    }

    public SystemException(Code code) {
        this.code = code;
    }

    public SystemException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
