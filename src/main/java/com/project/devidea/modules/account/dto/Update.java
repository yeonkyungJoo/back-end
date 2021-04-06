package com.project.devidea.modules.account.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Update {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProfileResponse{

        private String email;
        private String name;
        private String nickname;
        private LocalDateTime joinedAt;
        private LocalDateTime modifiedAt;
        private String bio;
        private String profileImage;
        private String url;
        private String gender;
        private String job;
        private int careerYears;
        private String techStacks;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProfileRequest{

        private String bio;
        private String profileImage;
        private String url;
        private String gender;
        private String job;
        private int careerYears;
        @Builder.Default
        private List<String> techStacks = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NicknameRequest{

        @Size(min = 2, max = 8, message = "닉네임은 2자 이상 8자 이하로 입력해주세요.")
        private String nickname;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Interest{

        @Builder.Default
        private List<String> interests = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MainActivityZone{

        @Builder.Default
        List<String> mainActivityZones = new ArrayList<>();

        public Map<String, List<String>> splitCityAndProvince() {
            Map<String, List<String>> map = new HashMap<>();
            List<String> cities = new ArrayList<>();
            List<String> provinces = new ArrayList<>();

            mainActivityZones.forEach(z -> {
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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Notification {

        private boolean receiveEmail;
        private boolean receiveNotification;
        private boolean receiveTechNewsNotification;
        private boolean receiveMentoringNotification;
        private boolean receiveStudyNotification;
        private boolean receiveRecruitingNotification;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PasswordRequest {

        @NotBlank(message = "8자 이상 30자 이하로 입력해주세요.")
        private String password;

        @NotBlank(message = "비밀번호를 한번 더 입력해주세요.")
        private String passwordConfirm;
    }
}
