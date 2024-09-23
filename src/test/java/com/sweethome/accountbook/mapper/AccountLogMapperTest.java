package com.sweethome.accountbook.mapper;


import com.sweethome.accountbook.config.DataBaseConfig;
import com.sweethome.accountbook.domain.AccountLog;
import com.sweethome.accountbook.domain.AuditInfo;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.dto.AccountLogDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(DataBaseConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class AccountLogMapperTest {

    private final AccountLogMapper accountLogMapper;

    @Autowired
    public AccountLogMapperTest(AccountLogMapper accountLogMapper) {
        this.accountLogMapper = accountLogMapper;
    }

    @Test
    void givenTypeIdAndDuration_whenSelectAll_thenReturningAccountLogList() {
        // given
        LogType logType = LogType.builder().typeId(11L).build();
        AccountLogDto param = AccountLogDto.builder()
                .logType(logType)
                .startDate(LocalDate.of(2024, 5, 27))
                .endDate(LocalDate.of(2024, 6, 27))
                .build();

        // when
        List<AccountLog> accountLogs = accountLogMapper.findByParam(param);

        // then
        assertThat(accountLogs)
                .hasSize(1)
                .contains(AccountLog.builder()
                        .logSeq(7L)
                        .logType(LogType.builder()
                                .typeId(11L)
                                .isDeposit(false)
                                .build())
                        .value(305200L)
                        .date(LocalDate.of(2024, 5, 27))
                        .description("7/72")
                        .auditInfo(AuditInfo.builder()
                                .createdAt(LocalDateTime.of(2024, 8, 9, 13, 46, 6))
                                .createdBy("1")
                                .build())
                        .build());
    }

    @Test
    void givenAccountLog_whenInsertData_thenInsertAccountLog() {
        // given
        LogType logType = LogType.builder().typeId(11L).build();
        AccountLog param = AccountLog.builder()
                .logType(logType)
                .value(50000L)
                .date(LocalDate.of(2024, 8, 27))
                .description("insert test")
                .auditInfo(AuditInfo.builder().createdBy("1").build())
                .build();

        AccountLogDto queryParam = AccountLogDto.builder()
                .logType(logType)
                .startDate(LocalDate.of(2024, 7, 27))
                .endDate(LocalDate.of(2024, 8, 27))
                .build();

        // when
        accountLogMapper.insert(param);
        List<AccountLog> result = accountLogMapper.findByParam(queryParam);

        // then
        assertThat(result)
                .hasSize(1);
    }

    @Test
    void givenAccountLog_whenUpdateData_thenUpdateAccountLog() {
        // given
        AccountLogDto queryParam = AccountLogDto.builder()
                .logSeq(1L)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2024, 6, 27))
                .build();

        AccountLog updateParam = AccountLog.builder()
                .logSeq(1L)
                .logType(LogType.builder().typeId(12L).build())
                .value(50000L)
                .date(LocalDate.of(2024, 6, 27))
                .description("update test")
                .auditInfo(AuditInfo.builder().modifiedBy("2").build())
                .build();

        // when
        accountLogMapper.update(updateParam);
        List<AccountLog> result = accountLogMapper.findByParam(queryParam);

        // then
        assertThat(result)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("value", 50000L)
                .hasFieldOrPropertyWithValue("date", LocalDate.of(2024, 6, 27))
                .hasFieldOrPropertyWithValue("description", "update test")
                .hasFieldOrPropertyWithValue("auditInfo.modifiedBy", "2");
    }

    @Test
    void givenLogSeq_whenDeleteData_thenReturningNothing() {
        // given
        AccountLogDto queryParam = AccountLogDto.builder()
                .logSeq(1L)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2024, 6, 27))
                .build();

        AccountLog deleteParam = AccountLog.builder()
                .logSeq(1L)
                .build();

        // when
        accountLogMapper.delete(deleteParam);
        List<AccountLog> result = accountLogMapper.findByParam(queryParam);

        // then
        assertThat(result)
                .hasSize(0);
    }
}