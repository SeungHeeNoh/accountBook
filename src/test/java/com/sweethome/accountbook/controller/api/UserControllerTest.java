package com.sweethome.accountbook.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweethome.accountbook.dto.request.UserRequest;
import com.sweethome.accountbook.dto.response.Response;
import com.sweethome.accountbook.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static Stream<Arguments> createUser(){
        return Stream.of(
                Arguments.of(
                        "user insert 성공 케이스",
                        createUserRequest("yjh", null),
                        createExpectResult(new Response("success", "사용자를 등록하는 데 성공했습니다."))
                ),
                Arguments.of(
                        "중복 userId로 실패하는 케이스",
                        createUserRequest("nsh", null),
                        createExpectResult(new Response("fail", "동일한 id가 이미 존재하여\n, 등록에 실패했습니다."))
                )
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("createUser")
    @Transactional
    void givenParam_whenCreateUser_thenReturningResultJson(String displayName, UserRequest requestBody, Map<String, Object> expectResult) throws Exception {
        // given

        // when & then
        mockMvc.perform(
                        post("/api/v1/user")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectResult)))
        ;
    }

    @WithUserDetails(value = "nsh")
    @Transactional
    @Test
    void givenParam_whenGetUser_thenReturningResultJson() throws Exception {
        // given

        // when & then
        mockMvc.perform(
                        get("/api/v1/user")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        createExpectResult(new UserResponse("nsh", LocalDateTime.of(2025, 1, 13, 13, 00, 51), null))
                )))
        ;
    }

    private static Stream<Arguments> modifyPassword(){
        return Stream.of(
                Arguments.of(
                        "password 수정 성공 케이스",
                        createUserRequest(null, "Qwerty12345"),
                        createExpectResult(new Response("success", "비밀번호를 수정하는 데 성공했습니다."))
                ),
                Arguments.of(
                        "기존과 동일한 password를 입력했을 때",
                        createUserRequest(null, "1234"),
                        createExpectResult(new Response("fail", "기존과 동일한 비밀번호를 사용할 수 없습니다."))
                ),
                Arguments.of(
                        "비밀번호가 8자리 미만 또는 16자리 이상일 때",
                        createUserRequest(null, "Qwe1"),
                        createExpectResult(new Response("fail", "비밀번호는 8자리 이상 16자리 미만이여야 합니다."))
                ),
                Arguments.of(
                        "숫자가 하나도 존재하지 않을 때",
                        createUserRequest(null, "Qwertyuiop"),
                        createExpectResult(new Response("fail", "비밀번호에 숫자가 하나 이상 포함되어야 합니다."))
                ),
                Arguments.of(
                        "대문자가 하나도 존재하지 않을 때",
                        createUserRequest(null, "qwerty12345"),
                        createExpectResult(new Response("fail", "비밀번호에 대문자 하나 이상 포함되어야 합니다."))
                )
        );
    }

    @WithUserDetails(value = "nsh")
    @Transactional
    @ParameterizedTest(name = "{0}")
    @MethodSource("modifyPassword")
    void givenParam_whenModifyPassword_thenReturningResultJson(String displayName, UserRequest requestBody, Map<String, Object> expectResult) throws Exception {
        // given

        // when & then
        mockMvc.perform(
                        patch("/api/v1/user/password")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectResult)))
        ;
    }

    // fixture
    private static UserRequest createUserRequest(String userId, String password) {
        UserRequest requestBody = new UserRequest();
        requestBody.setUserId(userId != null ? userId : "nsh");
        requestBody.setPassword(password != null ? password : "1234");

        return requestBody;
    }

    private static Map<String, Object> createExpectResult(Object data) {
        Map<String, Object> expectResult = new HashMap<>();
        expectResult.put("data", data);

        return expectResult;
    }
}