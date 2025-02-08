package com.sweethome.accountbook.mapper;

import com.sweethome.accountbook.domain.AccountLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountLogMapper {

    List<AccountLog> findByParam(AccountLog accountLog);

    int insert(AccountLog accountLog);

    int update(AccountLog accountLog);

    /**
     * 가계부 상태를 active 상태에서 inactive 상태로 변경
     * @param accountLog
     * @return update한 row수 반환
     */
    int delete(AccountLog accountLog);
}
