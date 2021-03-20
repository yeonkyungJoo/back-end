package com.project.devidea.modules.tagzone.zone;

import com.project.devidea.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@MockMvcTest
class ZoneControllerTest {

    @Autowired
    MockMvc mockMvc;

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