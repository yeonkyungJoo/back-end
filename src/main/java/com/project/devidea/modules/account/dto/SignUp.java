package com.project.devidea.modules.account.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommonRequest{

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식으로 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
        private String password;

        @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
        private String passwordConfirm;

        @Size(min = 2, max = 8, message = "2자 이상 입력해주세요.")
        private String name;

        @Size(min = 2, max = 8, message = "닉네임을 2자 이상 8자 이하로 입력해주세요.")
        private String nickname;

        private String gender;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OAuthRequest {

        @NotBlank(message = "인증한 소셜 사이트 제공자를 입력해주세요.")
        private String provider;

        @NotBlank(message = "인증한 소셜 사이트의 고유 식별자 값을 입력해주세요.")
        private String id;

        @NotBlank(message = "성함을 입력해주세요.")
        private String name;

        @Size(min = 2, max = 8, message = "닉네임을 2자 이상 8자 이하로 입력해주세요.")
        private String nickname;

        private String gender;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{

        private String id;

        private String email;

        private String name;

        private String nickname;

        private String gender;

        private String provider;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DetailRequest{

        private String profilePath;

        private boolean receiveEmail;

        @Min(value = 0,message = "0년 이상의 값을 입력해주세요.(구직중일 경우 0년을 입력해주세요.)")
        private int careerYears;

        private String jobField;

        @Builder.Default
        private List<String> zones = new ArrayList<>();

        @Builder.Default
        private List<String> techStacks = new ArrayList<>();

        @Builder.Default
        private List<String> interests = new ArrayList<>();

        public Map<String, List<String>> splitCitiesAndProvinces() {
            Map<String, List<String>> map = new HashMap<>();
            List<String> cities = new ArrayList<>();
            List<String> provinces = new ArrayList<>();

            zones.forEach(z -> {
                String[] cityProvince = z.split("/");
                String c = cityProvince[0];
                String p = cityProvince[1];
                cities.add(c);
                provinces.add(p);
            });

            map.put("city", cities);
            map.put("province", provinces);
            return map;
        }
    }
}
