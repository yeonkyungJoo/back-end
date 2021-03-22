package com.project.devidea.modules.account.repository;

import com.project.devidea.infra.TestConfig;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountDummy;
import com.project.devidea.modules.account.Interest;
import com.project.devidea.modules.account.MainActivityZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@Transactional
@DataJpaTest
@ActiveProfiles("profile")
@Import(TestConfig.class)
class AccountRepositoryCustomImplTest {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void init() {
        accountRepository.save(AccountDummy.getAccount());
    }

    @Test
    @DisplayName("이메일_혹은_닉네임으로_interests_mainActivities_한번에_가져오기")
    void fetchJoinTest() throws Exception {

//        when
        Account account = accountRepository
                .findByEmailWithMainActivityZoneAndInterests("ko@naver.com");

//        then
        Set<Interest> interests = account.getInterests();
        Set<MainActivityZone> mainActivityZones = account.getMainActivityZones();
        assertAll(
                () -> assertNotNull(interests),
                () -> assertNotNull(mainActivityZones)
        );
    }

}
