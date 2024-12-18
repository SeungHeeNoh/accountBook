package com.sweethome.accountbook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweethome.accountbook.domain.TransactionType;
import com.sweethome.accountbook.dto.LogTypeDto;
import com.sweethome.accountbook.dto.response.LogTypeResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}