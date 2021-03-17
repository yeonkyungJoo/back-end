package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.modules.account.form.LoginRequestDto;
import com.project.devidea.modules.account.form.SignUpRequestDto;
import com.project.devidea.modules.account.form.SignUpResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    CustomUserDetailService customUserDetailService;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @InjectMocks
    AccountService accountService;
    @Mock
    ModelMapper modelMapper;

    Account account;
    SignUpRequestDto request;
    SignUpResponseDto response;

    @BeforeEach
    void init() {
        request = SignUpRequestDto.builder()
                .email("ko@naver.com")
                .name("고범석")
                .password("1234")
                .passwordConfirm("1234")
                .gender("male")
                .build();

        account = Account.builder()
                .id(1L)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .nickname(request.getNickname())
                .roles("ROLE_USER")
                .joinedAt(LocalDateTime.now())
                .gender(request.getGender())
                .build();

        response = SignUpResponseDto.builder()
                .id(account.getId().toString())
                .email(account.getEmail())
                .gender(account.getGender())
                .name(account.getName())
                .nickname(account.getNickname())
                .build();
    }

    @Test
    @DisplayName("회원가입")
    void save() throws Exception {

//        given
        when(accountRepository.save(any(Account.class)))
                .thenReturn(account);
        when(modelMapper.map(account, SignUpResponseDto.class))
                .thenReturn(response);

//        when
        SignUpResponseDto responseDto = accountService.signUp(request);

//        then
        verify(modelMapper).map(account, SignUpResponseDto.class);
        assertAll(
                () -> assertNotNull(responseDto),
                () -> assertEquals(responseDto.getId(), account.getId().toString()),
                () -> assertEquals(responseDto.getEmail(), account.getEmail()),
                () -> assertEquals(responseDto.getNickname(), account.getNickname()),
                () -> assertEquals(responseDto.getName(), account.getName()),
                () -> assertEquals(responseDto.getGender(), account.getGender())
        );
    }

    @Test
    @DisplayName("로그인 시 jwt 코드 반환")
    void loginAndConfirm() throws Exception {

//        given
        accountRepository.save(Account.builder().id(1L).email("ko@naver.com")
                .password(passwordEncoder.encode("123412341234")).nickname("고범석").name("고범석").build());
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("ko@naver.com")
                .password("123412341234")
                .build();
        when(jwtTokenUtil.generateToken(any(String.class)))
                .thenReturn("jwttoken");

//        when
        Map<String, String> result = accountService.login(loginRequestDto);

//        then
        verify(jwtTokenUtil).generateToken(any(String.class));
        assertEquals(result.get("token"), "Bearer jwttoken");
    }
}