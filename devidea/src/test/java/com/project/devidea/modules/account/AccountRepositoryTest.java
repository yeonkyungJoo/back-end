package com.project.devidea.modules.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void init() {
        accountRepository.save(
                Account.builder()
                        .id(1L)
                        .nickname("hello")
                        .email("ko@naver.com")
                        .password("1234")
                        .build()
        );
    }

    @Test
    @DisplayName("존재하는 닉네임")
    void existsByNickname() {
        String nickname = "hello";
        boolean result = accountRepository.existsByNickname(nickname);
        assertTrue(result);
    }

    @Test
    @DisplayName("존재하지 않는 닉네임")
    void NotexistsByNickname() {
        String nickname = "kobeomseok";
        boolean result = accountRepository.existsByNickname(nickname);
        assertFalse(result);
    }

    @Test
    @DisplayName("존재하는 이메일")
    void existsByEmail() {
        String nickname = "ko@naver.com";
        boolean result = accountRepository.existsByEmail(nickname);
        assertTrue(result);
    }

    @Test
    @DisplayName("존재하지 않는 이메일")
    void NotexistsByEmail() {
        String nickname = "123123@naver.com";
        boolean result = accountRepository.existsByEmail(nickname);
        assertFalse(result);
    }
}