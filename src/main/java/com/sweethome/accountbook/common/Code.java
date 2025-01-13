package com.sweethome.accountbook.common;

public enum Code {

    /**
     * log_type관련 error_code
     * 0~99 할당
     * */
    DUPLICATE_LOGTYPE_EXIST(1L, "동일한 이름의 항목이 이미 존재하여\n 등록에 실패했습니다."),
    REQUEST_LOGTYPE_NOTEXIST(2L, "존재하지 않는 항목입니다."),

    /**
     * log_type관련 error_code
     * 100~199 할당
     * */
    DUPLICATE_USERID_EXIST(100L, "동일한 id가 이미 존재하여\n, 등록에 실패했습니다.")
    ;

    private final long id;
    private final String msg;

    Code(final long id, final String msg) {
        this.id = id;
        this.msg = msg;
    }

    public long getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
}
