package com.sweethome.accountbook.mapper;

import com.sweethome.accountbook.config.DataBaseConfig;
import com.sweethome.accountbook.domain.AuditInfo;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.TransactionType;
import com.sweethome.accountbook.domain.UserGroup;
import com.sweethome.accountbook.dto.LogTypeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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

    private static Stream<Arguments> findByParam() {
        return Stream.of(
                Arguments.of(
                        "조건절 없는 케이스",
                        LogType.builder()
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .build(),
                        new LogTypeDto(1L,
                                "수입",
                                TransactionType.DEPOSIT,
                                "모든 수입",
                                "수입",
                                LocalDateTime.of(2024, 12, 12, 14, 47, 20),
                                "nsh",
                                null,
                                null)
                ),
                Arguments.of(
                        "typeId 조건절",
                        LogType.builder()
                                .typeId(1L)
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .build(),
                        new LogTypeDto(1L,
                                "수입",
                                TransactionType.DEPOSIT,
                                "모든 수입",
                                "수입",
                                LocalDateTime.of(2024, 12, 12, 14, 47, 20),
                                "nsh",
                                null,
                                null)
                ),
                Arguments.of(
                        "typeName 조건절",
                        LogType.builder()
                                .typeName("수입")
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .build(),
                        new LogTypeDto(1L,
                                "수입",
                                TransactionType.DEPOSIT,
                                "모든 수입",
                                "수입",
                                LocalDateTime.of(2024, 12, 12, 14, 47, 20),
                                "nsh",
                                null,
                                null)
                ),
                Arguments.of(
                        "transactionType 조건절",
                        LogType.builder()
                                .transactionType(TransactionType.WITHDRAWAL)
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .build(),
                        new LogTypeDto(4L,
                                "저축",
                                TransactionType.WITHDRAWAL,
                                "저축!!!",
                                "저축",
                                LocalDateTime.of(2024, 12, 16, 14, 2, 29),
                                "nsh",
                                null,
                                null)
                ),
                Arguments.of(
                        "description 조건절",
                        LogType.builder()
                                .description("월급")
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .build(),
                        new LogTypeDto(2L,
                                "아내 월급",
                                TransactionType.DEPOSIT,
                                "아내 월급",
                                "수입",
                                LocalDateTime.of(2024, 12, 16, 13, 43, 59),
                                "nsh",
                                null,
                                null)
                ),
                Arguments.of(
                        "createdBy 조건절",
                        LogType.builder()
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .auditInfo(AuditInfo.builder().createdBy("nsh").build())
                                .build(),
                        new LogTypeDto(2L,
                                "아내 월급",
                                TransactionType.DEPOSIT,
                                "아내 월급",
                                "수입",
                                LocalDateTime.of(2024, 12, 16, 13, 43, 59),
                                "nsh",
                                null,
                                null)
                ),
                Arguments.of(
                        "modifiedBy 조건절",
                        LogType.builder()
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .auditInfo(AuditInfo.builder().modifiedBy("yjh").build())
                                .build(),
                        new LogTypeDto(3L,
                                "남편 월급",
                                TransactionType.DEPOSIT,
                                "남편 월급",
                                "수입",
                                LocalDateTime.of(2024, 12, 16, 13, 43, 59),
                                "nsh",
                                LocalDateTime.of(2024, 12, 16, 23, 23, 55),
                                "yjh")
                ),
                Arguments.of(
                        "parentTypeId 조건절",
                        LogType.builder()
                                .parentTypeId(4L)
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .build(),
                        new LogTypeDto(4L,
                                "저축",
                                TransactionType.WITHDRAWAL,
                                "저축!!!",
                                "저축",
                                LocalDateTime.of(2024, 12, 16, 14, 2, 29),
                                "nsh",
                                null,
                                null)
                )
        );
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("findByParam")
    void givenParam_whenFindByParam_thenReturningLogTypeDto(String displayName, LogType param, LogTypeDto expectResult) {
        // given

        // when
        List<LogTypeDto> result = logTypeMapper.findByParam(param);

        // then
        assertThat(result)
                .contains(expectResult);
    }

    @Test
    void givenLogType_whenInsertLogType_thenReturningInsertCount() {
        // given
        UserGroup loginUserGroup = UserGroup.builder().groupSeq(1L).build();
        int count = logTypeMapper.findByParam(LogType.builder().userGroup(loginUserGroup).build()).size();
        LogType param = LogType.builder()
                .typeName("월급")
                .transactionType(TransactionType.DEPOSIT)
                .description("test")
                .parentTypeId(null)
                .auditInfo(AuditInfo.builder()
                        .createdBy("nsh").build())
                .parentTypeId(1L)
                .userGroup(loginUserGroup)
                .build();

        // when
        logTypeMapper.insert(param);

        // then
        assertThat(logTypeMapper.findByParam(LogType.builder().userGroup(loginUserGroup).build()).size())
                .isEqualTo(count + 1);
    }
}