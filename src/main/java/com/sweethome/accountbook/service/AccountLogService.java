package com.sweethome.accountbook.service;

import com.sweethome.accountbook.domain.AccountLog;

public interface AccountLogService {


    /**
     * 가계부 상태를 active 상태에서 inactive 상태로 변경
     * @param requestParam
     * @return update한 row수 반환
     */
    int deleteAccountLog(AccountLog requestParam);
}
