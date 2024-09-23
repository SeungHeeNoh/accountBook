package com.sweethome.accountbook.mapper;

import com.sweethome.accountbook.domain.AccountLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountLogMapper {

    List<AccountLog> findByParam(AccountLog accountLog);

    int insert(AccountLog accountLog);

    int update(AccountLog accountLog);

    int delete(AccountLog accountLog);
}
