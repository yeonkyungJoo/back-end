package com.project.devidea.modules.account;

import com.project.devidea.modules.account.form.SignUpDetailRequestDto;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagDummy;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

//    Account account;
//    List<Zone> zones;
//    List<Tag> techStacks;
//    List<Tag> interests;
//    Tag job;
//
//    @BeforeEach
//    void init() {
//        account = Account.builder().id(1L).email("ko@naver.com").password("1234").name("고범석")
//                .nickname("고범석").build();
//        zones = ZoneDummy.getZoneDummy();
//        techStacks = TagDummy.getTechStackDummy();
//        interests = TagDummy.getInterestDummy();
//        job = TagDummy.getJobDummy();
//    }
//
//    @Test
//    @DisplayName("매핑테이블 연관관계 테스트 1. Zone")
//    void setZones() throws Exception {
//
////        given, when
//        account.setZones(zones);
//
////        then
//        Set<MainActivityZone> mainActivityZones = account.getMainActivityZones();
//        assertThat(mainActivityZones.size()).isEqualTo(5);
//    }
//
//    @Test
//    @DisplayName("매핑테이블 연관관계 테스트 2. TechStack")
//    void setTechStacks() throws Exception {
//
////        given, when
//        account.setTechStacks(techStacks);
//
////        then
//        Set<TechStack> accountTechStacks = account.getTechStacks();
//        assertThat(accountTechStacks.size()).isEqualTo(4);
//    }
//
//    @Test
//    @DisplayName("매핑테이블 연관관계 테스트 3. Interest")
//    void setInterests() throws Exception {
//
////        given, when
//        account.setInterests(interests);
//
////        then
//        Set<Interest> accountInterests = account.getInterests();
//        assertThat(accountInterests.size()).isEqualTo(5);
//    }
//
//    @Test
//    @DisplayName("매핑테이블 연관관계 테스트 4. 회원가입 디테일")
//    void setSaveSignUpDetail() throws Exception{
//
////        given
//        SignUpDetailRequestDto request = SignUpDetailRequestDto.builder().profileImage("123123123")
//                .receiveEmail(true).careerYears(3).jobField("웹개발")
//                .zones(zones.stream().map(zone -> zone.getCity() + " " + zone.getProvince()).collect(Collectors.toList()))
//                .techStacks(techStacks.stream().map(Tag::getFirstName).collect(Collectors.toList()))
//                .interests(interests.stream().map(Tag::getFirstName).collect(Collectors.toList()))
//                .build();
//
////        when
//        account.saveSignUpDetail(request, job, zones, techStacks, interests);
//
////        then zone : 5, techStack : 4, Interest : 5
//        Set<MainActivityZone> mainActivityZones = account.getMainActivityZones();
//        Set<TechStack> accountTechStacks = account.getTechStacks();
//        Set<Interest> accountInterests = account.getInterests();
//        assertAll(
//                () -> assertEquals(mainActivityZones.size(), 5),
//                () -> assertEquals(accountTechStacks.size(), 4),
//                () -> assertEquals(accountInterests.size(), 5)
//        );
//    }
}