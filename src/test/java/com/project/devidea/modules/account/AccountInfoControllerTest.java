package com.project.devidea.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.modules.account.dto.AccountProfileUpdateRequestDto;
import com.project.devidea.modules.account.dto.LoginRequestDto;
import com.project.devidea.modules.account.dto.UpdatePasswordRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@Slf4j
class AccountInfoControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired CustomUserDetailService customUserDetailService;
    @Autowired JwtTokenUtil jwtTokenUtil;
    @Autowired BCryptPasswordEncoder passwordEncoder;

    @Test
    @WithUserDetails(value = "test@test.com")
    void 유저의_프로필_가져오기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");

//        when
        MvcResult result = mockMvc.perform(get("/account/settings/profile")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
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

    @Test
    @WithUserDetails(value = "test@test.com")
    @Transactional
    void 유저_프로필_수정() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        AccountProfileUpdateRequestDto accountProfileUpdateRequestDto =
                AccountDummy.getAccountProfileUpdateRequestDto();

//        when
        mockMvc.perform(patch("/account/settings/profile")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountProfileUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());

//        then
        Account account = loginUser.getAccount();
        assertAll(
                () -> assertEquals(account.getBio(), accountProfileUpdateRequestDto.getBio()),
                () -> assertEquals(account.getProfileImage(), accountProfileUpdateRequestDto.getProfileImage()),
                () -> assertEquals(account.getUrl(), accountProfileUpdateRequestDto.getUrl()),
                () -> assertEquals(account.getGender(), accountProfileUpdateRequestDto.getGender()),
                () -> assertEquals(account.getJob(), accountProfileUpdateRequestDto.getJob()),
                () -> assertEquals(account.getCareerYears(), accountProfileUpdateRequestDto.getCareerYears()),
                () -> assertEquals(account.getTechStacks(),
                        StringUtils.join(accountProfileUpdateRequestDto.getTechStacks(), '/')));
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    @Transactional
    void 유저_패스워드_수정() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        String previousPassword = loginUser.getPassword();
        UpdatePasswordRequestDto updatePasswordRequestDto =
                AccountDummy.getUpdatePassowordRequestDto();

//        when
        mockMvc.perform(patch("/account/settings/password")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePasswordRequestDto)))
                .andDo(print());

//        then, 바뀐 비밀번호로 로그인할 경우 Bearer 토큰을 주는지?
        MockHttpServletResponse response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LoginRequestDto.builder()
                        .email("test@test.com")
                        .password(updatePasswordRequestDto.getPassword()).build())))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization")).andReturn().getResponse();

        String jwtToken = response.getHeader("Authorization").substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        assertEquals(username, "test@test.com");
    }
}