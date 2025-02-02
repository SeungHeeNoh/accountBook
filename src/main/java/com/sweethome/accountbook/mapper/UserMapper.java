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
     * 전달받은 id를 가진 user의 loginFailCount값 1 증가
     * @param userId
     * @return update한 row수 반환
     */
    int updateLoginFailCount(String userId);

    /**
     * 전달받은 id를 가진 user의 lastLoginTryAt을 현재로 업데이트
     * @param userId
     * @return update한 row수 반환
     */
    int updateLastLoginTryAt(String userId);

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

    /**
     * (inactivePeriodCutoffDate)일 이상 접근하지 않은 고객 휴면 처리
     * @param inactivePeriodCutoffDate
     * @return inactive한 user수 반환
     */
    int updateInactiveUser(int inactivePeriodCutoffDate);
}
