package com.sweethome.accountbook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.TransactionType;
import com.sweethome.accountbook.domain.UserGroup;
import com.sweethome.accountbook.dto.LogTypeDto;
import com.sweethome.accountbook.dto.request.LogTypeManageRequest;
import com.sweethome.accountbook.dto.response.LogTypeResponse;
import com.sweethome.accountbook.dto.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LogTypeControllerTest {

    @Autowired
    private LogTypeController logTypeController;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static Stream<Arguments> findByParam() throws JsonProcessingException {
        return Stream.of(
                Arguments.of(
                        "조건절 없는 케이스",
                        "/log-types",
                        getExpectResult(5)
                ),
                Arguments.of(
                        "조건절 있는 케이스",
                        "/log-types?transactionType=2",
                        getExpectResult(2)
                )
        );
    }

    public static Map<String, Object> getExpectResult(int count) throws JsonProcessingException {
        List<LogTypeDto> serviceResult = new ArrayList<>();

        switch (count) {
            case 5:
                serviceResult.add(new LogTypeDto(1L,
                        "수입",
                        TransactionType.DEPOSIT,
                        "모든 수입",
                        "수입",
                        LocalDateTime.of(2024, 12, 12, 14, 47, 20),
                        "nsh",
                        null,
                        null));
            case 4:
                serviceResult.add(new LogTypeDto(2L,
                        "아내 월급",
                        TransactionType.DEPOSIT,
                        "아내 월급",
                        "수입",
                        LocalDateTime.of(2024, 12, 16, 13, 43, 59),
                        "nsh",
                        null,
                        null));
            case 3:
                serviceResult.add(new LogTypeDto(3L,
                        "남편 월급",
                        TransactionType.DEPOSIT,
                        "남편 월급",
                        "수입",
                        LocalDateTime.of(2024, 12, 16, 13, 43, 59),
                        "nsh",
                        LocalDateTime.of(2024, 12, 16, 23, 23, 55),
                        "yjh"));
            case 2:
                serviceResult.add(new LogTypeDto(4L,
                        "저축",
                        TransactionType.WITHDRAWAL,
                        "저축!!!",
                        "저축",
                        LocalDateTime.of(2024, 12, 16, 14, 2, 29),
                        "nsh",
                        null,
                        null));
            case 1:
                serviceResult.add(new LogTypeDto(5L,
                        "이사가자",
                        TransactionType.WITHDRAWAL,
                        "이사자금",
                        "저축",
                        LocalDateTime.of(2024, 12, 16, 14, 3, 42),
                        "nsh",
                        null,
                        null));
        }

        Map<String, Object> expectResult = new HashMap<>();
        expectResult.put("data", serviceResult.stream().map(LogTypeResponse::from).toList());

        return expectResult;
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("findByParam")
    void givenParam_whenFindByParam_thenReturningLogTypeDto(String displayName, String param, Map<String, Object> expectResult) throws Exception {
        // given

        // when & then
        mockMvc.perform(get(param))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectResult)))
        ;
    }

    private static Stream<Arguments> createLogType(){
        Response successResponse = new Response("success", "가계부 항목을 추가하는 데 성공했습니다.");
        Map<String, Object> expectSuccessResult = new HashMap<>();
        expectSuccessResult.put("data", successResponse);
        LogTypeManageRequest successRequest = getLogTypeManageRequest("배당금", "test", 1, 1L);

        Response failResponse = new Response("fail", "동일한 이름의 항목이 이미 존재하여\n 등록에 실패했습니다.");
        Map<String, Object> expectFailResult = new HashMap<>();
        expectFailResult.put("data", failResponse);
        LogTypeManageRequest failRequest = getLogTypeManageRequest("아내 월급", "test", 1, 1L);

        return Stream.of(
                Arguments.of(
                        "insert 성공하는 케이스",
                        successRequest,
                        expectSuccessResult
                ),
                Arguments.of(
                        "중복 데이터로 실패하는 케이스",
                        failRequest,
                        expectFailResult
                )
        );
    }

    private static LogTypeManageRequest getLogTypeManageRequest(String typename, String description, int transactionType, Long parentTypeId) {
        LogTypeManageRequest logTypeManageRequest = new LogTypeManageRequest();
        logTypeManageRequest.setTypeName(typename);
        logTypeManageRequest.setDescription(description);
        logTypeManageRequest.setTransactionType(transactionType);
        logTypeManageRequest.setParentTypeId(parentTypeId);

        return logTypeManageRequest;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("createLogType")
    @Transactional
    void givenParam_whenCreateLogType_thenReturningResultJson(String displayName, LogTypeManageRequest requestBody, Map<String, Object> expectResult) throws Exception {
        // given

        // when & then
        mockMvc.perform(
                    post("/log-type")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectResult)))
        ;
    }

    private static Stream<Arguments> findByTypeId() {

        Map<String, Object> expectExistResult = new HashMap<>();
        expectExistResult.put("data", LogTypeResponse.from(new LogTypeDto(1L,
                "수입",
                TransactionType.DEPOSIT,
                "모든 수입",
                "수입",
                LocalDateTime.of(2024, 12, 12, 14, 47, 20),
                "nsh",
                null,
                null)));

        Map<String, Object> expectEmptyResult = new HashMap<>();
        expectEmptyResult.put("data", null);

        return Stream.of(
                Arguments.of(
                        "LogType이 존재할 때",
                        "/log-type/1",
                        expectExistResult
                ),
                Arguments.of(
                        "LogType이 존재하지 않을 때",
                        "/log-type/100",
                        expectEmptyResult
                )
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("findByTypeId")
    void givenLogTypeId_whenFindByTypeId_thenReturningLogTypeData(String displayName, String url, Map<String, Object> expectResult) throws Exception {
        // given

        // when & then
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectResult)))
        ;
    }

    @Transactional
    @Test
    void givenLogTypeManageRequest_whenModifyLogType_thenReturningResultJson() throws Exception {
        // given
        LogTypeManageRequest requestBody = getLogTypeManageRequest(null, "test", 2, 0L);
        Map<String, Object> expectResult = new HashMap<>();
        expectResult.put("data", new Response("success", "가계부 항목을 수정하는 데 성공했습니다."));

        // when & then
        mockMvc.perform(
                        put("/log-type/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectResult)))
        ;
    }
}