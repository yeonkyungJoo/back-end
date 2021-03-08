package com.project.devidea.modules.account;

import com.project.devidea.infra.config.jwt.JwtTokenUtil;
import com.project.devidea.infra.config.jwt.JwtUserDetailsService;
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
    JwtUserDetailsService jwtUserDetailsService;
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
        SignUpResponseDto responseDto = accountService.save(request);

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
}