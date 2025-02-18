package com.project.devidea.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.modules.account.AccountService;
import com.project.devidea.modules.account.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@Slf4j
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AccountService accountService;
    @Autowired
    CustomUserDetailService customUserDetailService;

    @BeforeEach
    void preHandle() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("회원가입")
    void save() throws Exception {

//        given
        SignUpRequestDto request = SignUpRequestDto.builder().name("고범떡").nickname("고범떡").email("kob@naver.com")
                .password("123412341234").passwordConfirm("123412341234").gender("male").build();
        SignUpResponseDto response = SignUpResponseDto.builder().id("1").name(request.getName())
                .nickname(request.getNickname()).email(request.getEmail()).gender(request.getGender()).build();

//        when, then
        mockMvc.perform(post("/sign-up")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("고범떡")))
                .andExpect(jsonPath("$.nickname", is("고범떡")))
                .andExpect(jsonPath("$.email", is("kob@naver.com")))
                .andExpect(jsonPath("$.gender", is("male")));
    }

    @Test
    @DisplayName("OAuth 회원가입")
    void saveOAuthGoogle() throws Exception {

//        given
        SignUpOAuthRequestDto signUpOAuthRequestDto = AccountDummy.getSignUpOAuthRequestDto();

//        when, then
        mockMvc.perform(post("/sign-up/oauth")
                .content(objectMapper.writeValueAsString(signUpOAuthRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
//    @Test
//    @DisplayName("회원가입 validation - 입력값 확인하기")
//    void confirmJoinValidation() throws Exception {
//
////        given
//        SignUpRequestDto request = SignUpRequestDto.builder()
//                .email("1234").gender("male").password("123").passwordConfirm("12").nickname("고").name("고").build();
//
////        when, then
//        mockMvc.perform(post("/sign-up")
//                .content(objectMapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.statusCode", is(400)))
//                .andExpect(jsonPath("$.errorFieldAndMessageList", hasSize(5)));
//    }

//    @Test
//    @DisplayName("회원가입 validation - 닉네임과 이메일 중복")
//    void confirmMultipleNicknameAndEmail() throws Exception {
//
////        given
//        accountRepository.save(Account.builder().email("ko@naver.com").password("123123123").name("고범석")
//                .nickname("고범석").build());
//        SignUpRequestDto request = SignUpRequestDto.builder()
//                .email("ko@naver.com").gender("male").password("123123123").passwordConfirm("123123123")
//                .nickname("고범석").name("고범석").build();
//
////        when, then
//        mockMvc.perform(post("/sign-up")
//                .content(objectMapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.statusCode", is(400)))
//                .andExpect(jsonPath("$.errorFieldAndMessageList", hasSize(2)));
//    }

    @Test
    @DisplayName("로그인 시 jwt, 토큰의 username == 로그인 username 확인")
    void confirmJwtTokenAndAuthorization() throws Exception {

//        when, then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LoginRequestDto.builder()
                        .email("test@test.com").password("1234").build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization")).andReturn().getResponse();

        String jwtToken = mockHttpServletResponse.getHeader("Authorization").substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        assertEquals(username, "test@test.com");
    }

    @Test
    @DisplayName("회원가입 상세정보 저장")
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailService", value = "test@test.com")
    @Transactional
    void signUpDetail() throws Exception {

//        given
        LoginUser loginUser = (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        Account account = loginUser.getAccount();
        SignUpDetailRequestDto signUpDetailRequestDto = AccountDummy.getSignUpDetailRequestDto();

//        when
        mockMvc.perform(post("/sign-up/detail")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDetailRequestDto)))
                .andDo(print());

//        then
        Set<Interest> getInterests = account.getInterests();
        List<String> tagNames = getInterests.stream()
                .map(interest -> interest.getTag().getFirstName()).collect(Collectors.toList());

        Set<MainActivityZone> getMainActivityZones = account.getMainActivityZones();
        List<String> zoneNames = getMainActivityZones.stream()
                .map(zone -> zone.getZone().getCity() + " " + zone.getZone().getProvince())
                .collect(Collectors.toList());

        assertAll(
                () -> assertEquals(getInterests.size(), 3),
                () -> assertEquals(getMainActivityZones.size(), 3),
                () -> assertThat(tagNames).contains("react", "Vue.js", "spring"),
                () -> assertThat(zoneNames).contains("서울특별시 광진구", "서울특별시 중랑구", "경기도 수원시"));
    }
}
