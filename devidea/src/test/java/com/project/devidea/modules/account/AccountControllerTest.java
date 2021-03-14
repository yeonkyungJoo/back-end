package com.project.devidea.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.config.jwt.JwtTokenUtil;
import com.project.devidea.modules.account.form.LoginRequestDto;
import com.project.devidea.modules.account.form.SignUpRequestDto;
import com.project.devidea.modules.account.form.SignUpResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
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
    @MockBean
    AccountService accountService;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("회원가입")
    void save() throws Exception {

//        given
        SignUpRequestDto request = SignUpRequestDto.builder().name("고범석").nickname("고범석").email("ko@naver.com")
                .password("123412341234").passwordConfirm("123412341234").gender("male").build();
        SignUpResponseDto response = SignUpResponseDto.builder().id("1").name(request.getName())
                .nickname(request.getNickname()).email(request.getEmail()).gender(request.getGender()).build();
        when(accountService.save(any())).thenReturn(response);

//        when, then
        mockMvc.perform(post("/sign-up")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("고범석")))
                .andExpect(jsonPath("$.nickname", is("고범석")))
                .andExpect(jsonPath("$.email", is("ko@naver.com")))
                .andExpect(jsonPath("$.gender", is("male")));
    }

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
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/login")
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
    @DisplayName("구글 로그인 redirect")
    void googleLogin() throws Exception {

        mockMvc.perform(get("/login/oauth/google"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile&response_type=code&redirect_uri=http://localhost:8080/login/oauth/google/callback&client_id=9719839326-fnc7hcgbq8nit8qp6s3ip7vrfn01gche.apps.googleusercontent.com"));
    }

    @Test
    @DisplayName("깃허브 로그인 redirect")
    void githubLogin() throws Exception {

        mockMvc.perform(get("/login/oauth/github"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://github.com/login/oauth/authorize?scope=read:user%20user:email&redirect_uri=http://localhost:8080/login/oauth/github/callback&client_id=9bc5902314cd6dbcb181"));
    }
}
