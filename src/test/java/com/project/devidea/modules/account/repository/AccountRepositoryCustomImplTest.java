package com.project.devidea.modules.account.repository;

import com.project.devidea.infra.TestConfig;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountDummy;
import com.project.devidea.modules.account.Interest;
import com.project.devidea.modules.account.MainActivityZone;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagDummy;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneDummy;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("profile")
@Import(TestConfig.class)
class AccountRepositoryCustomImplTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    MainActivityZoneRepository mainActivityZoneRepository;
    @Autowired
    InterestRepository interestRepository;
    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    TagRepository tagRepository;

    @BeforeEach
    void init() {
        Account account = accountRepository.save(AccountDummy.getAccount());
        List<Zone> zones = zoneRepository.saveAll(ZoneDummy.getZoneDummy());
        List<Tag> tags = tagRepository.saveAll(TagDummy.getInterestDummy());

        Set<MainActivityZone> mainActivityZones = new HashSet<>();
        zones.forEach(zone -> mainActivityZones.add(MainActivityZone.builder().account(account).zone(zone).build()));
        mainActivityZoneRepository.saveAll(mainActivityZones);
        account.getMainActivityZones().addAll(mainActivityZones);

        Set<Interest> interests = new HashSet<>();
        tags.forEach(tag -> interests.add(Interest.builder().account(account).tag(tag).build()));
        interestRepository.saveAll(interests);
        account.getInterests().addAll(interests);
    }

    @Test
    void 이메일_혹은_닉네임으로_interests_mainActivities_한번에_가져오기() throws Exception {

//        when
        Account findAccount = accountRepository
                .findByEmailWithMainActivityZoneAndInterests("ko@naver.com");

//        then
        Set<Interest> findInterests = findAccount.getInterests();
        Set<MainActivityZone> findMainActivityZones = findAccount.getMainActivityZones();
        assertAll(
                () -> assertNotNull(findInterests),
                () -> assertNotNull(findMainActivityZones),
                () -> assertEquals(findInterests.size(), 5),
                () -> assertEquals(findMainActivityZones.size(), 5));
    }

    @Test
    void 이메일로_interests_페치조인() throws Exception {

//        when
        Account findAccount = accountRepository.findByEmailWithInterests("ko@naver.com");

//        then
        Set<Interest> interests = findAccount.getInterests();
        assertEquals(interests.size(), 5);
    }

    @Test
    void 이메일로_mainActivityZones_페치조인() throws Exception {

//        when
        Account findAccount = accountRepository.findByEmailWithMainActivityZones("ko@naver.com");

//        then
        Set<MainActivityZone> mainActivityZones = findAccount.getMainActivityZones();
        assertEquals(mainActivityZones.size(), 5);
    }
}