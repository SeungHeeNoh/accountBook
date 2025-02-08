package com.sweethome.accountbook.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweethome.accountbook.dto.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountLogControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static Stream<Arguments> deleteLogType() {
        return Stream.of(
                Arguments.of(
                        "AccountLog가 존재할 때",
                        "/api/v1/account-logs/1",
                        createExpectResult(new Response("success", "가계부 기록을 삭제하는 데 성공했습니다."))
                ),
                Arguments.of(
                        "AccountLog가 존재하지 않을 때",
                        "/api/v1/account-logs/999999",
                        createExpectResult(new Response("fail", "존재하지 않는 항목입니다."))
                )
        );
    }

    @WithUserDetails(value = "nsh")
    @ParameterizedTest(name = "${0}")
    @MethodSource("deleteLogType")
    @Transactional
    void givenLogTypeId_whenDeleteLogType_thenRemoveLogType(String displayName, String url, Map<String, Object> expectResult) throws Exception {
        // given

        // when & then
        mockMvc.perform(
                        delete(url)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectResult)))
        ;
    }

    private static Map<String, Object> createExpectResult(Object data) {
        Map<String, Object> expectResult = new HashMap<>();
        expectResult.put("data", data);

        return expectResult;
    }
}