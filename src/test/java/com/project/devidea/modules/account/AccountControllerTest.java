package com.project.devidea.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.SHA256;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.account.repository.InterestRepository;
import com.project.devidea.modules.account.repository.MainActivityZoneRepository;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
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

import javax.mail.Message;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
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
    CustomUserDetailService customUserDetailService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    InterestRepository interestRepository;
    @Autowired
    MainActivityZoneRepository mainActivityZoneRepository;

    @BeforeEach
    void preHandle() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("일반 회원가입")
    void save() throws Exception {

//        given
        SignUp.CommonRequest request = SignUp.CommonRequest.builder().name("고범떡").email("kob@naver.com")
                .password(SHA256.encrypt("123412341234")).passwordConfirm(SHA256.encrypt("123412341234"))
                .gender("male").nickname("고범떡").build();

//        when
        mockMvc.perform(post("/sign-up")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("고범떡")))
                .andExpect(jsonPath("$.data.email", is("kob@naver.com")))
                .andExpect(jsonPath("$.data.gender", is("male")))
                .andExpect(jsonPath("$.data.nickname", is("고범떡")));

//        then
        assertEquals(request.getPassword(), SHA256.encrypt("123412341234"));
    }

    @Test
    @DisplayName("OAuth 회원가입")
    void saveOAuthGoogle() throws Exception {

//        given
        SignUp.OAuthRequest signUpOAuthRequestDto = AccountDummy.getSignUpOAuthRequestDto();
        String encryptedId = signUpOAuthRequestDto.getId();

//        when
        mockMvc.perform(post("/sign-up/oauth")
                .content(objectMapper.writeValueAsString(signUpOAuthRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

//        then
        assertEquals(encryptedId, SHA256.encrypt("google123412341234"));
    }

    @Test
    @DisplayName("로그인 시 jwt, 토큰의 username == 로그인 username 확인")
        void confirmJwtTokenAndAuthorization() throws Exception {

//        when, then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Login.Common.builder()
                        .email("test@test.com").password("1234").build())))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().exists("Authorization")).andReturn().getResponse();

        String jwtToken = mockHttpServletResponse.getHeader("Authorization").substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        assertEquals(username, "test@test.com");
    }

    @Test
    void OAuth_로그인() throws Exception {

//        given
        SignUp.OAuthRequest join = AccountDummy.getSignUpOAuthRequestDto2();
        mockMvc.perform(post("/sign-up/oauth").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(join)));
        Login.OAuth login = AccountDummy.getLoginOAuthRequestDto();

//        when
        MockHttpServletResponse response = mockMvc.perform(post("/login/oauth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andReturn().getResponse();

        String jwtToken = response.getHeader("Authorization").substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        assertEquals(username, SHA256.encrypt(join.getId()));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailService", value = "test@test.com")
    void 회원가입_상세정보_저장() throws Exception {

//        given
        LoginUser loginUser = (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        SignUp.DetailRequest signUpDetailRequestDto = AccountDummy.getSignUpDetailRequestDto();

//        when
        mockMvc.perform(post("/sign-up/detail")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDetailRequestDto)))
                .andDo(print());

//        then account
        Account account = accountRepository.findByEmailWithMainActivityZoneAndInterests(loginUser.getUsername());
        assertAll(
                () -> assertTrue(account.isReceiveEmail()),
                () -> assertEquals(account.getJob(), "웹개발"),
                () -> assertEquals(account.getProfilePath(), "1234"),
                () -> assertEquals(account.getTechStacks(), "java/python"),
                () -> assertEquals(account.getInterests().size(), 3),
                () -> assertEquals(account.getMainActivityZones().size(), 3));

//        then mainActivityZone, interest
        List<MainActivityZone> mainActivityZones = mainActivityZoneRepository.findByAccount(account);
        List<Interest> interests = interestRepository.findByAccount(account);
        assertAll(
                () -> assertEquals(mainActivityZones.size(), 3),
                () -> assertEquals(interests.size(), 3));
    }

//    ValidationTest ====================================================================================================

    @Test
    void 회원가입_유효성_테스트_1_기본적인_Valid() throws Exception {

//        given
        SignUp.CommonRequest failValidSignUpRequest = AccountDummy.getFailSignUpRequestWithValid();

//        when, then
//        이메일 공백, password Blank, passwordConfirm Blank, name 길이, nickname Blank
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(failValidSignUpRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(5)));
    }

    @Test
    void 회원가입_유효성_테스트_2_With_Validator() throws Exception {

//        given
        SignUp.CommonRequest commonRequest = AccountDummy.getFailSignUpRequestWithValidator();

//        when, then
//        이메일 중복과 패스워드, 패스워드 확인값 불일치, 닉네임 중복
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commonRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(3)));
    }

    @Test
    void 회원가입_유효성_테스트_OAuth_1_기본적인_Valid() throws Exception {

//        given
        SignUp.OAuthRequest failRequest = AccountDummy.getFailSignUpOAuthRequestWithValid();

//        when, then
//        provider 공백, email 공백, name 공백, provider 제공x, nickname 공백
        mockMvc.perform(post("/sign-up/oauth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(failRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(5)));
    }

    @Test
    void 회원가입_유효성_테스트_OAuth_2_With_Validator() throws Exception {

//        given
        SignUp.OAuthRequest failRequest = AccountDummy.getFailSignUpOAuthRequestWithValidator();

//        when, then
//        지원하지 않는 provider, nickname 중복
        mockMvc.perform(post("/sign-up/oauth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(failRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(2)));
    }

    @Test
    void 로그인_유효성_테스트_1_기본() throws Exception {

//        given
        Login.Common loginRequestDto = Login.Common.builder().email("asdfsdf").password("").build();

//        when, then
//        not email, empty password
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(2)));
    }

    @Test
    void 로그인_유효성_테스트_2_아이디와_비밀번호가_일치하지_않은_경우() throws Exception {

//        given
        Login.Common loginRequestDto = Login.Common.builder().email("test@test.com").password("11").build();

//        when, then
//        아이디와 비밀번호가 일치하지 않을 때, BadCredentialException 발생
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("회원의 아이디와 비밀번호가 일치하지 않습니다.")));
    }

    @Test
    void 로그인_유효성_테스트_3_OAuth() throws Exception {

//        given
        Login.OAuth login = Login.OAuth.builder().id("").build();

//        when, then
//        empty id
        mockMvc.perform(post("/login/oauth").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(1)));
    }

    @Test
    void 회원가입_상세정보_저장_테스트_1() throws Exception {

//        given
        SignUp.DetailRequest failRequest = AccountDummy.getFailSignUpDetailRequestWithValid();

//        when, then
//        경력년도 음수, 직업분야 empty, 닉네임 length 초과
        mockMvc.perform(post("/sign-up/detail").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(failRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(1)));
    }
}
