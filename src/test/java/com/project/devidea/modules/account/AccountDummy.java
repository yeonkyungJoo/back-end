package com.project.devidea.modules.account;

import ch.qos.logback.core.net.LoginAuthenticator;
import com.mysql.cj.xdevapi.Collection;
import com.project.devidea.modules.account.form.LoginOAuthRequestDto;
import com.project.devidea.modules.account.form.SignUpDetailRequestDto;
import com.project.devidea.modules.account.form.SignUpOAuthRequestDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDummy {

    public static SignUpDetailRequestDto getSignUpDetailRequestDto() {
        return SignUpDetailRequestDto.builder()
                .careerYears(3).receiveEmail(true).jobField("웹개발").profileImage("1234")
                .zones(Arrays.asList("서울특별시 광진구", "서울특별시 중랑구", "경기도 수원시"))
                .techStacks(Arrays.asList("java", "python"))
                .interests(Arrays.asList("react", "Vue.js", "spring"))
                .build();
    }

    public static Account getAccount() {

        return Account.builder()
                .interests(new HashSet<>())
                .mainActivityZones(new HashSet<>())
                .nickname("고범석").password("1234").name("고범석").roles("ROLE_USER").email("ko@naver.com").build();
    }

    public static SignUpOAuthRequestDto getSignUpOAuthRequestDto() {
        return SignUpOAuthRequestDto.builder().email("kokoko@google.com").name("구글범석").nickname("구글범석")
                .profileImage("12341234").provider("google").build();
    }

    public static SignUpOAuthRequestDto getSignUpOAuthRequestDto2() {
        return SignUpOAuthRequestDto.builder().email("ko@google.com").name("고오범석").nickname("고오범석")
                .profileImage("12341234").provider("google").build();
    }

    public static LoginOAuthRequestDto getLoginOAuthRequestDto() {
        return LoginOAuthRequestDto.builder().provider("google").email("kokoko@google.com").build();
    }

    public static LoginOAuthRequestDto getLoginOAuthRequestDto2() {
        return LoginOAuthRequestDto.builder().provider("google").email("ko@google.com").build();
    }
}
