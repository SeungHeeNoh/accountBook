package com.sweethome.accountbook.mapper;

import com.sweethome.accountbook.config.DataBaseConfig;
import com.sweethome.accountbook.domain.User;
import com.sweethome.accountbook.dto.DeleteLogTypeDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import(DataBaseConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class UserMapperTest {

    private final UserMapper userMapper;

    @Autowired
    public UserMapperTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    void givenUser_whenUpdateLastLoginAt_thenReturningUpdateCount() {
        // given
        User param = User.builder()
                .userSeq(1L)
                .userId("nsh")
                .password("$2a$10$YMfiaCpgnax4Xl19aKSfnu8Kk8wr1J9Ncom9RFQEQ9RpKoqNDrR2S")
                .build();

        // when
        int result = userMapper.updateLastLoginAt(param);

        // then
        assertThat(result)
                .isEqualTo(1);
    }

}