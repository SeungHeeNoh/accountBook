package com.sweethome.accountbook.mapper;

import com.sweethome.accountbook.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findByUserId(String userId);
}
