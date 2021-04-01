package com.project.devidea.modules.account;

import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.tagzone.tag.TagDummy;
import com.project.devidea.modules.tagzone.zone.ZoneDummy;

import java.time.LocalDateTime;
import java.util.*;

public class AccountDummy {

    public static SignUpDetailRequestDto getSignUpDetailRequestDto() {
        return SignUpDetailRequestDto.builder()
                .careerYears(3).receiveEmail(true).jobField("웹개발").profileImage("1234")
                .nickname("고범석짱짱짱")
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

    public static SignUpOAuthRequestDto getSignUpOAuthRequestDto() {
        return SignUpOAuthRequestDto.builder().email("kokoko@google.com").name("구글범석")
                .profileImage("12341234").provider("google").build();
    }

    public static SignUpOAuthRequestDto getSignUpOAuthRequestDto2() {
        return SignUpOAuthRequestDto.builder().email("ko@google.com").name("고오범석")
                .profileImage("12341234").provider("google").build();
    }

    public static LoginOAuthRequestDto getLoginOAuthRequestDto() {
        return LoginOAuthRequestDto.builder().provider("google").email("kokoko@google.com").build();
    }

    public static LoginOAuthRequestDto getLoginOAuthRequestDto2() {
        return LoginOAuthRequestDto.builder().provider("google").email("ko@google.com").build();
    }

    public static AccountProfileResponseDto getAccountProfileResponseDtoAtMockito() {
        return AccountProfileResponseDto.builder()
                .name("").bio("").careerYears(3).email("ko@naver.com").gender("male").job("PM")
                .joinedAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).nickname("고범석")
                .profileImage("1231234").techStacks("java/spring/jpa").url("github.com")
                .build();
    }

    public static AccountProfileUpdateRequestDto getAccountProfileUpdateRequestDto() {
        return AccountProfileUpdateRequestDto.builder().bio("12345").careerYears(1).gender("female")
                .job("웹개발").profileImage("123456").techStacks(Arrays.asList("django", "jpa")).build();
    }

    public static UpdatePasswordRequestDto getUpdatePassowordRequestDto() {
        return UpdatePasswordRequestDto.builder().password("123123123123").passwordConfirm("123123123123").build();
    }

    public static InterestsUpdateRequestDto getInterestsUpdateRequestDto() {
        return InterestsUpdateRequestDto.builder().interests(Arrays.asList("Vue.js", "java", "docker")).build();
    }

    public static MainActivityZonesUpdateRequestDto getMainActivityZonesUpdateRequestDto() {
        return MainActivityZonesUpdateRequestDto.builder()
                .citiesAndProvinces(Arrays.asList("서울특별시/중랑구", "서울특별시/노원구"))
                .build();
    }

    public static SignUpRequestDto getFailSignUpRequestWithValid() {
        return SignUpRequestDto.builder()
                .email("").name("").password("").passwordConfirm("").build();
    }

    public static SignUpRequestDto getFailSignUpRequestWithValidator() {
        return SignUpRequestDto.builder().email("test@test.com").name("고범석")
                .password("12341234").passwordConfirm("123412341234").build();
    }

    public static SignUpOAuthRequestDto getFailSignUpOAuthRequestWithValid() {
        return SignUpOAuthRequestDto.builder().provider("").email("").name("").build();
    }

    public static SignUpOAuthRequestDto getFailSignUpOAuthRequestWithValidator() {
        return SignUpOAuthRequestDto.builder().provider("kakao").email("asdfasdf").name("고범석").build();
    }

    public static SignUpDetailRequestDto getFailSignUpDetailRequestWithValid(){
        return SignUpDetailRequestDto.builder()
                .careerYears(-1).jobField("").nickname("testusers").build();
    }

    public static SignUpDetailRequestDto getFailSignUpDetailRequestWithValidator() {
        return SignUpDetailRequestDto.builder().nickname("DevIdea")
                .careerYears(2).jobField("웹개발").build();
    }
}
