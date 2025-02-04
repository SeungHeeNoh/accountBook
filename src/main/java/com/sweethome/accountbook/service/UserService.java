package com.sweethome.accountbook.service;

import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    /**
     * @param userId
     * @return user 정보 반환
     */
    User searchUser(String userId) throws UsernameNotFoundException;

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
     * user 생성
     * @param user
     * @return insert한 row수 반환
     */
    int createUser(User user);

    /**
     * user 비밀번호 변경
     * @param user
     * @return update한 row수 반환
     * @throws SystemException SAME_CURRENT_PASSWORD : 기존과 동일한 password로 update시도시
     * @throws SystemException INVALID_PASSWORD_LENGTH : 8자리 미만 또는 16자리 이상일 때
     * @throws SystemException NO_EXIST_DIGIT : 숫자가 하나도 존재하지 않을 때
     * @throws SystemException NO_EXIST_UPPERCASE : 대문자가 하나도 존재하지 않을 때
     */
    int modifyPassword(User user);

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
    int convertToInactiveUser(int inactivePeriodCutoffDate);
}
