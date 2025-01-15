package com.sweethome.accountbook.mapper;

import com.sweethome.accountbook.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * @param userId
     * @return user 정보 반환
     */
    User findByUserId(String userId);

    /**
     * user의 로그인 시각 업데이트
     * @param user
     * @return update한 row수 반환
     */
    int updateLastLoginAt(User user);

    /**
     * userId 중복 id 검사
     * @param userId
     * @return 중복 id 갯수 반환
     */
    int getDuplicateUserIdCount(String userId);

    /**
     * user 생성
     * @param user
     * @return insert한 row수 반환
     */
    int insertUser(User user);

    /**
     * user 비밀번호 변경
     * @param user
     * @return update한 row수 반환
     */
    int updatePassword(User user);

    /**
     * user 삭제
     * @param user
     * @return delete한 row수 반환
     */
    int deleteUser(User user);
}
