package com.project.devidea.modules.tagzone.zone;

import com.project.devidea.infra.MockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@MockMvcTest
class ZoneControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ZoneRepository zoneRepository;

    /**
     * initService를 주석한 상태에서 진행한 beforeEach입니다.
     * init이 정상적으로 작동되면 이 메서드를 지워주세요! -범석
     */
    @BeforeEach
    void init() throws Exception {
        Map<String, Map<String,Boolean>> CheckMap=new HashMap<String, Map<String, Boolean>>();
        if (zoneRepository.count() == 0) {
            Resource resource = new ClassPathResource("zones_kr.csv");
            List<Zone> zoneList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .map(line -> {
                        String[] split = line.split(",");
                        return Zone.builder().city(split[0]).province(split[1]).build();
                    }).collect(Collectors.toList());
            zoneRepository.saveAll(zoneList);
        }
    }

    @Test
    @DisplayName("모든 위치 가져오기")
    void findAllZones() throws Exception {

//        when, then
        mockMvc.perform(get("/zones"))
                .andDo(print())
                .andExpect(jsonPath("$.cities.length()", is(17)))
                .andExpect(jsonPath("$.provinces.울산광역시.length()", is(5)))
                .andExpect(jsonPath("$.provinces.대전광역시.length()", is(5)))
                .andExpect(jsonPath("$.provinces.광주광역시.length()", is(5)))
                .andExpect(jsonPath("$.provinces.전라남도.length()", is(28)))
                .andExpect(jsonPath("$.provinces.경상남도.length()", is(44)))
                .andExpect(jsonPath("$.provinces.전라북도.length()", is(23)))
                .andExpect(jsonPath("$.provinces.부산광역시.length()", is(16)))
                .andExpect(jsonPath("$.provinces.경기도.length()", is(73)))
                .andExpect(jsonPath("$.provinces.대구광역시.length()", is(8)))
                .andExpect(jsonPath("$.provinces.충청남도.length()", is(28)))
                .andExpect(jsonPath("$.provinces.충청북도.length()", is(21)))
                .andExpect(jsonPath("$.provinces.서울특별시.length()", is(25)))
                .andExpect(jsonPath("$.provinces.제주특별자치도.length()", is(4)))
                .andExpect(jsonPath("$.provinces.경상북도.length()", is(37)))
                .andExpect(jsonPath("$.provinces.강원도.length()", is(22)))
                .andExpect(jsonPath("$.provinces.인천광역시.length()", is(15)))
                .andExpect(jsonPath("$.provinces.세종특별자치시.length()", is(1)));

    }
}
