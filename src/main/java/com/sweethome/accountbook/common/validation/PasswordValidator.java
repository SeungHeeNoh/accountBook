package com.sweethome.accountbook.common.validation;

import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.common.Code;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class PasswordValidator {

    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");

    /**
     * @param password
     * @return 유효성 검사 통과시 true 반환
     * @throws SystemException INVALID_PASSWORD_LENGTH : 8자리 미만 또는 16자리 이상일 때
     * @throws SystemException NO_EXIST_DIGIT : 숫자가 하나도 존재하지 않을 때
     * @throws SystemException NO_EXIST_UPPERCASE : 대문자가 하나도 존재하지 않을 때
     */
    public static boolean isValidPassword(String password) {
        log.debug("password = {}", password);

        // password는 8자리 이상 16자리 이하여야 한다.
        if (password == null || password.length() < 8 || password.length() > 16) {
            throw new SystemException(Code.INVALID_PASSWORD_LENGTH);
        }

        // password 내부에 숫자가 하나 이상 포함되어야 한다.
        if(!DIGIT_PATTERN.matcher(password).matches()) {
            throw new SystemException(Code.NO_EXIST_DIGIT);
        }

        // password 내부에 대문자가 하나 이상 포함되어야 한다.
        if(!UPPERCASE_PATTERN.matcher(password).matches()) {
            throw new SystemException(Code.NO_EXIST_UPPERCASE);
        }

        return true;
    }
}
