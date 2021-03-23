package com.project.devidea.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@Slf4j
class AccountInfoControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Test
    @WithUserDetails(value = "test@test.com")
    void 유저의_프로필_가져오기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");

//        when
        MvcResult result = mockMvc.perform(get("/account/settings/profile")
                .header("Authorization", jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

//        then
        Account account = loginUser.getAccount();
        String str = result.getResponse().getContentAsString();
        Map<String, String> map = objectMapper.readValue(str, Map.class);
        assertAll(() -> assertEquals(account.getEmail(), "test@test.com"),
                () -> assertEquals(account.getNickname(), "테스트_회원"));
    }
}