package com.sweethome.accountbook.service;

import com.sweethome.accountbook.common.Code;
import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.domain.AccountLog;
import com.sweethome.accountbook.domain.LogState;
import com.sweethome.accountbook.mapper.AccountLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountLogServiceImpl implements AccountLogService{

    private final AccountLogMapper accountLogMapper;

    @Override
    public int deleteAccountLog(AccountLog requestParam) {
        requestParam.setLogState(LogState.INACTIVE);
        int result = accountLogMapper.delete(requestParam);

        if(result == 0) {
            throw new SystemException(Code.REQUEST_ACCOUNTLOG_NOTEXIST);
        }

        return result;
    }
}
