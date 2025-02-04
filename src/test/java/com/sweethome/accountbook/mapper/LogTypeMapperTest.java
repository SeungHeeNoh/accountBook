package com.sweethome.accountbook.mapper;

import com.sweethome.accountbook.config.DataBaseConfig;
import com.sweethome.accountbook.domain.AuditInfo;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.TransactionType;
import com.sweethome.accountbook.domain.UserGroup;
import com.sweethome.accountbook.dto.DeleteLogTypeDto;
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
                                null,
                                null,
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
                                null,
                                null,
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
                                null,
                                null,
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
                                null,
                                null,
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
                                1L,
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
                                1L,
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
                                1L,
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
                        new LogTypeDto(11L,
                                "연금보험",
                                TransactionType.WITHDRAWAL,
                                "100세 만족 연금보험",
                                4L,
                                "저축",
                                LocalDateTime.of(2024, 12, 21, 6, 23, 34),
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

    private static Stream<Arguments> findByTypeId() {
        return Stream.of(
                Arguments.of(
                        "LogType이 존재할 때",
                        LogType.builder()
                                .typeId(1L)
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .build(),
                        new LogTypeDto(1L,
                                "수입",
                                TransactionType.DEPOSIT,
                                "모든 수입",
                                null,
                                null,
                                LocalDateTime.of(2024, 12, 12, 14, 47, 20),
                                "nsh",
                                null,
                                null)
                ),
                Arguments.of(
                        "LogType이 존재하지 않을 때",
                        LogType.builder()
                                .typeId(100L)
                                .userGroup(UserGroup.builder().groupSeq(1L).build())
                                .build(),
                        null
                )
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("findByTypeId")
    void givenLogTypeId_whenFindByTypeId_thenReturningLogTypeData(String displayName, LogType param, LogTypeDto expectResult) {
        // given

        // when
        LogTypeDto result = logTypeMapper.findByTypeId(param);

        // then
        assertThat(result)
                .isEqualTo(expectResult);
    }

    @Test
    void givenLogType_whenUpdate_thenUpdateLogType() {
        // given
        LogType param = LogType.builder()
                .typeId(1L)
                .transactionType(TransactionType.DEPOSIT)
                .description("test")
                .auditInfo(AuditInfo.builder().modifiedBy("test").build())
                .userGroup(UserGroup.builder().groupSeq(1L).build())
                .build();

        // when
        int result = logTypeMapper.update(param);

        // then
        assertThat(result)
                .isEqualTo(1);
    }

    @Test
    void givenLogTypeIdsAndGroupSeq_whenDelete_thenRemoveLogTypes() {
        // given
        DeleteLogTypeDto param = new DeleteLogTypeDto();
        param.setTypeIds(List.of(1L, 2L, 3L));
        param.setGroupSeq(1L);

        // when
        int result = logTypeMapper.delete(param);

        // then
        assertThat(result)
                .isEqualTo(3);
    }
}