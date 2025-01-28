package com.sweethome.accountbook.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenNothing_whenLoginOnSecondLogin_thenFirstSessionTerminated() throws Exception {
        MvcResult mvcResult = mockMvc.perform(formLogin()
                        .user("nsh")
                        .password("1234")
                )
                .andExpect(authenticated())
                .andReturn();

        MockHttpSession firstLoginSession = (MockHttpSession) mvcResult.getRequest().getSession(false);

        mockMvc.perform(get("/").session(firstLoginSession))
                .andExpect(authenticated());

        mockMvc.perform(formLogin()
                .user("nsh")
                .password("1234")
        ).andExpect(authenticated());

        mockMvc.perform(get("/").session(firstLoginSession))
                .andExpect(unauthenticated());
    }
}