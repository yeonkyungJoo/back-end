package com.project.devidea.modules.account;

import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagDummy;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneDummy;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void 프로필_업데이트() throws Exception {

//        given
        Account account = AccountDummy.getAccount();
        Update.ProfileRequest accountProfileUpdateRequestDto =
                AccountDummy.getAccountProfileUpdateRequestDto();

//        when
        account.updateProfile(accountProfileUpdateRequestDto);

//        then
        String techStacks = account.getTechStacks();
        assertAll(
                () -> assertEquals(account.getBio(), accountProfileUpdateRequestDto.getBio()),
                () -> assertEquals(account.getCareerYears(), accountProfileUpdateRequestDto.getCareerYears()),
                () -> assertEquals(account.getGender(), accountProfileUpdateRequestDto.getGender()),
                () -> assertEquals(account.getUrl(), accountProfileUpdateRequestDto.getUrl()),
                () -> assertEquals(account.getJob(), accountProfileUpdateRequestDto.getJob()),
                () -> assertEquals(account.getProfilePath(), accountProfileUpdateRequestDto.getProfileImage()),
                () -> assertEquals(techStacks, StringUtils.join(accountProfileUpdateRequestDto.getTechStacks(), '/')));
    }

    @Test
    void 비밀번호_변경() throws Exception {

//        given
        Account account = AccountDummy.getAccount();
        Update.PasswordRequest updatePasswordRequestDto = AccountDummy.getUpdatePassowordRequestDto();

//        when
        account.updatePassword(updatePasswordRequestDto.getPassword());

//        then
        assertEquals(account.getPassword(), updatePasswordRequestDto.getPassword());
    }

    @Test
    void 관심기술_수정() throws Exception {

//        given
        Account account = AccountDummy.getAccount();
        Set<Interest> interests = new HashSet<>();
        List<Tag> tags = TagDummy.getInterestDummy();
        tags.forEach(tag -> interests.add(Interest.builder().account(account).tag(tag).build()));

//        when
        account.updateInterests(interests);

//        then
        assertEquals(account.getInterests().size(), 5);
    }

    @Test
    void 활동지역_수정() throws Exception {

//        given
        Account account = AccountDummy.getAccount();
        Set<MainActivityZone> mainActivityZones = new HashSet<>();
        List<Zone> zones = ZoneDummy.getZoneDummy();
        zones.forEach(zone -> mainActivityZones.add(MainActivityZone.builder().account(account).zone(zone).build()));

//        when
        account.updateMainActivityZones(mainActivityZones);

//        then
        assertEquals(account.getMainActivityZones().size(), 5);
    }

    @Test
    void 닉네임_변경() throws Exception {

//        given
        Account account = AccountDummy.getAccount();
        Update.NicknameRequest request = Update.NicknameRequest.builder().nickname("변경닉네임").build();

//        when
        account.changeNickname(request.getNickname());

//        then
        assertThat(account.getNickname()).isEqualTo(request.getNickname());
    }

    @Test
    void 알림_설정_변경() throws Exception{

//        given
        Account account = AccountDummy.getAccount();
        Update.Notification request = Update.Notification.builder().receiveEmail(true)
                .receiveMentoringNotification(true).receiveNotification(true).receiveRecruitingNotification(true)
                .receiveStudyNotification(true).receiveTechNewsNotification(true).build();

//        when
        account.updateNotifications(request);

//        then
        assertAll(
                () -> assertTrue(account.isReceiveEmail()),
                () -> assertTrue(account.isReceiveMentoringNotification()),
                () -> assertTrue(account.isReceiveNotification()),
                () -> assertTrue(account.isReceiveRecruitingNotification()),
                () -> assertTrue(account.isReceiveStudyNotification()),
                () -> assertTrue(account.isReceiveTechNewsNotification()));
    }
}