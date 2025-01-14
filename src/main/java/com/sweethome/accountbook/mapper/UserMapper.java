package com.sweethome.accountbook.mapper;

import com.sweethome.accountbook.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findByUserId(String userId);

    int updateLastLoginAt(User user);

    int getDuplicateUserIdCount(String userId);

    int insertUser(User user);

    int updatePassword(User user);
}
