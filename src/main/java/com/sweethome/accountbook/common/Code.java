package com.sweethome.accountbook.common;

public enum Code {

    /**
     * log_type관련 error_code
     * 0~99 할당
     * */
    DUPLICATE_LOGTYPE_EXIST(1L, "동일한 이름의 항목이 이미 존재하여\n 등록에 실패했습니다."),
    REQUEST_LOGTYPE_NOTEXIST(2L, "존재하지 않는 항목입니다."),

    /**
     * USER 관련 error_code
     * 100~199 할당
     * */
    DUPLICATE_USERID_EXIST(100L, "동일한 id가 이미 존재하여\n, 등록에 실패했습니다."),
    SAME_CURRENT_PASSWORD(101L, "기존과 동일한 비밀번호를 사용할 수 없습니다."),
    INVALID_PASSWORD_LENGTH(102L, "비밀번호는 8자리 이상 16자리 미만이여야 합니다."),
    NO_EXIST_DIGIT(103L, "비밀번호에 숫자가 하나 이상 포함되어야 합니다."),
    NO_EXIST_UPPERCASE(104L, "비밀번호에 대문자 하나 이상 포함되어야 합니다."),
    BAD_CREDENTIAL(105L, "아이디 혹은 비밀번호가 맞지 않습니다."),
    LOCKED_USER(106L, "비밀번호가 연속으로 5번 틀려 계정이 잠겼습니다. 30분 뒤 로그인을 다시 할 수 있습니다."),
    INACTIVE_USER(107L, "휴면 고객입니다. 관리자에게 휴면 해제를 요청해주세요.")
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
