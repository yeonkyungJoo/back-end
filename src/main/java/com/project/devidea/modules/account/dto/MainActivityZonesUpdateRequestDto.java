package com.project.devidea.modules.account.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainActivityZonesUpdateRequestDto {

    @Builder.Default
    List<String> citiesAndProvinces = new ArrayList<>();

    public Map<String, List<String>> splitCityAndProvince() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> cities = new ArrayList<>();
        List<String> provinces = new ArrayList<>();

        citiesAndProvinces.forEach(z -> {
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
