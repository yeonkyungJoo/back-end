package com.project.devidea.modules.tagzone.zone;

import lombok.*;

import java.util.*;

@Getter @Setter
public class ZonesResponseDto {

    private Set<String> cities = new HashSet<>();
    private Map<String, List<String>> provinces = new HashMap<>();
}
