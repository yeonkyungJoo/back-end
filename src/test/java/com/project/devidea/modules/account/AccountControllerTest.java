package com.project.devidea.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.modules.account.form.*;
import com.project.devidea.modules.account.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

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
                .andExpect(jsonPath("$.id", is("1")))
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

//        given
        Account savedAccount = accountRepository.save(Account.builder().email("ko@naver.com")
                .password(passwordEncoder.encode("123412341234")).name("고범석").nickname("고범석")
                .joinedAt(LocalDateTime.now()).roles("ROLE_USER").build());
        Map<String, String> map = new HashMap<>();
        map.put("header", jwtTokenUtil.getHeader());
        map.put("token", "Bearer " + jwtTokenUtil.generateToken(savedAccount.getEmail()));
        when(accountService.login(any())).thenReturn(map);

//        when, then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LoginRequestDto.builder()
                        .email(savedAccount.getEmail()).password("123412341234").build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization")).andReturn().getResponse();

        String jwtToken = mockHttpServletResponse.getHeader("Authorization").substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        assertEquals(username, savedAccount.getEmail());
    }

    @Test
    @DisplayName("OAuth 로그인")
    void confirmJwtTokenAndAuthorizationWithOAuth() throws Exception {

//        given
        accountService.signUpOAuth(AccountDummy.getSignUpOAuthRequestDto());
        LoginOAuthRequestDto loginOAuthRequestDto = AccountDummy.getLoginOAuthRequestDto();

//        when
        MockHttpServletResponse response = mockMvc.perform(post("/login/oauth")
                .content(objectMapper.writeValueAsString(loginOAuthRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization")).andReturn().getResponse();

        String jwtToken = response.getHeader("Authorization").substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        assertEquals(username, "ko@google.com");
    }

//    @Test
//    @DisplayName("비밀번호가 틀렸을 경우, BadCredential Exception 확인하기")
//    void confirmBadCredentialException() throws Exception {
//
////        given
//        accountRepository.save(Account.builder().email("kobu@naver.com").password("123123123").name("고범숙")
//                .nickname("고범숙").build());
//        LoginRequestDto login = LoginRequestDto.builder()
//                .email("ko@naver.com").password("1111")
//                .build();
//        when(accountService.login(any())).thenThrow(BadCredentialsException.class);
//
////        when, then
//        mockMvc.perform(post("/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(login)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.statusCode", is(400)))
//                .andExpect(jsonPath("$.description", is("회원의 아이디와 비밀번호가 일치하지 않습니다.")));
//    }

//    @Test
//    @DisplayName("회원가입 상세정보 저장")
//    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailService", value = "test@test.com")
//    void signUpDetail() throws Exception {
//
////        given
//        LoginUser loginUser = (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
//        SignUpDetailRequestDto signUpDetailRequestDto = AccountDummy.getSignUpDetailRequestDto();
//        String token = "Bearer " + jwtTokenUtil.generateToken(loginUser.getUsername());
//
////        when
//        mockMvc.perform(post("/sign-up/detail").contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", token)
//                .content(objectMapper.writeValueAsString(signUpDetailRequestDto))).andDo(print())
//                .andExpect(status().isOk());
//    }
}
