package com.project.devidea.modules.account.repository;

import com.project.devidea.infra.TestConfig;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void init() {
        accountRepository.save(
                Account.builder()
                        .id(1L)
                        .name("고범석")
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
