package com.project.devidea.modules.account;

import com.project.devidea.infra.SHA256;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.tagzone.tag.TagDummy;
import com.project.devidea.modules.tagzone.zone.ZoneDummy;

import java.time.LocalDateTime;
import java.util.*;

public class AccountDummy {

    public static SignUp.DetailRequest getSignUpDetailRequestDto() {
        return SignUp.DetailRequest.builder()
                .careerYears(3).receiveEmail(true).jobField("웹개발").profilePath("1234")
                .zones(Arrays.asList("서울특별시/광진구", "서울특별시/중랑구", "경기도/수원시"))
                .techStacks(Arrays.asList("java", "python"))
                .interests(Arrays.asList("react", "Vue.js", "spring"))
                .build();
    }

    public static Account getAccount() {
        return Account.builder().interests(new HashSet<>()).mainActivityZones(new HashSet<>()).nickname("고범석")
                .password("1234").name("고범석").roles("ROLE_USER").email("ko@naver.com").build();
    }

    public static Set<Interest> getInterests(Account account) {
        Set<Interest> interests = new HashSet<>();
        TagDummy.getInterestDummy()
                .forEach(tag -> interests.add(Interest.builder()
                        .account(account).tag(tag).build()));
        return interests;
    }

    public static Set<MainActivityZone> getMainActivityZones(Account account) {
        Set<MainActivityZone> zones = new HashSet<>();
        ZoneDummy.getZoneDummy()
                .forEach(zone -> zones.add(MainActivityZone.builder()
                        .account(account).zone(zone).build()));
        return zones;
    }

    public static SignUp.OAuthRequest getSignUpOAuthRequestDto() {
        return SignUp.OAuthRequest.builder().id(SHA256.encrypt("google123412341234"))
                .name("구글범석").provider("google").build();
    }

    public static SignUp.OAuthRequest getSignUpOAuthRequestDto2() {
        return SignUp.OAuthRequest.builder().id(SHA256.encrypt("google56785678"))
                .nickname("고오범석").name("고오범석").provider("google").build();
    }

    public static Login.OAuth getLoginOAuthRequestDto() {
        return Login.OAuth.builder().id(SHA256.encrypt("google56785678")).build();
    }

    public static Update.ProfileResponse getAccountProfileResponseDtoAtMockito() {
        return Update.ProfileResponse.builder()
                .name("").bio("").careerYears(3).email("ko@naver.com").gender("male").job("PM")
                .joinedAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).nickname("고범석")
                .profileImage("1231234").techStacks("java/spring/jpa").url("github.com")
                .build();
    }

    public static Update.ProfileRequest getAccountProfileUpdateRequestDto() {
        return Update.ProfileRequest.builder().bio("12345").careerYears(1).gender("female")
                .job("웹개발").profileImage("123456").techStacks(Arrays.asList("django", "jpa")).build();
    }

    public static Update.PasswordRequest getUpdatePassowordRequestDto() {
        return Update.PasswordRequest.builder()
                .password(SHA256.encrypt("123123123123"))
                .passwordConfirm(SHA256.encrypt("123123123123")).build();
    }

    public static Update.PasswordRequest getNotEqualsPasswordAndPasswordConfirm() {
        return Update.PasswordRequest.builder()
                .password(SHA256.encrypt("1234567890"))
                .passwordConfirm(SHA256.encrypt("123")).build();
    }

    public static Update.PasswordRequest getBlankPasswordRequest() {
        return Update.PasswordRequest.builder()
                .password("  ")
                .passwordConfirm("").build();
    }

    public static Update.Interest getInterestsUpdateRequestDto() {
        return Update.Interest.builder().interests(Arrays.asList("Vue.js", "java", "docker")).build();
    }

    public static Update.MainActivityZone getMainActivityZonesUpdateRequestDto() {
        return Update.MainActivityZone.builder()
                .mainActivityZones(Arrays.asList("서울특별시/중랑구", "서울특별시/노원구"))
                .build();
    }

    public static SignUp.CommonRequest getFailSignUpRequestWithValid() {
        return SignUp.CommonRequest.builder()
                .email("").name("").password("").passwordConfirm("").nickname("").build();
    }

    public static SignUp.CommonRequest getFailSignUpRequestWithValidator() {
        return SignUp.CommonRequest.builder().email("test@test.com").name("고범석").nickname("DevIdea")
                .password("12341234").passwordConfirm("123412341234").build();
    }

    public static SignUp.OAuthRequest getFailSignUpOAuthRequestWithValid() {
        return SignUp.OAuthRequest.builder().provider("").id("").name("").nickname("").build();
    }

    public static SignUp.OAuthRequest getFailSignUpOAuthRequestWithValidator() {
        return SignUp.OAuthRequest.builder().provider("kakao").id("asdfasdf").name("고범석").
                nickname("DevIdea").build();
    }

    public static SignUp.DetailRequest getFailSignUpDetailRequestWithValid(){
        return SignUp.DetailRequest.builder()
                .careerYears(-1).build();
    }
}
