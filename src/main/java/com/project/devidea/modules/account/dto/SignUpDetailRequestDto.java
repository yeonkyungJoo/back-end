package com.project.devidea.modules.account.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDetailRequestDto {

    private String profileImage;
    private boolean receiveEmail;

    @Min(value = 0,message = "0년 이상의 값을 입력해주세요.(구직중일 경우 0년을 입력해주세요.)")
    private int careerYears;

    @NotBlank(message = "본인의 직업 분야를 입력해주세요.(구직중일 경우 원하는 직업 분야를 입력해주세요.)")
    private String jobField;

    @Builder.Default
    private List<String> zones = new ArrayList<>();

    @Builder.Default
    private List<String> techStacks = new ArrayList<>();

    @Builder.Default
    private List<String> interests = new ArrayList<>();

    public Map<String, List<String>> getCitiesAndProvinces() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> cities = new ArrayList<>();
        List<String> provinces = new ArrayList<>();

        zones.forEach(z -> {
            String[] cityProvince = z.split(" ");
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
