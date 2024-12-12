package com.sweethome.accountbook.mapper;

import com.sweethome.accountbook.config.DataBaseConfig;
import com.sweethome.accountbook.domain.AuditInfo;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.TransactionType;
import com.sweethome.accountbook.domain.UserGroup;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;


@Import(DataBaseConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class LogTypeMapperTest {

    private final LogTypeMapper logTypeMapper;

    @Autowired
    public LogTypeMapperTest(LogTypeMapper logTypeMapper) {
        this.logTypeMapper = logTypeMapper;
    }

    @Test
    @Commit
    void givenLogType_whenInsertLogType_thenReturnId() {
        // given
        LogType param = LogType.builder()
                .typeName("월급")
                .transactionType(TransactionType.DEPOSIT)
                .description("test")
                .parentTypeId(null)
                .auditInfo(AuditInfo.builder()
                        .createdBy("nsh").build())
                .parentTypeId(1L)
                .userGroup(UserGroup.builder().groupSeq(1L).build())
                .build();
        param.setTypeId(1L);

        // when
        int insert = logTypeMapper.insert(param);

        // then
        assertThat(insert)
                .isEqualTo(1);
    }
}