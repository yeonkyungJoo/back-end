//package com.project.devidea.modules.tagzone.tag;
//
//import com.project.devidea.infra.MockMvcTest;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import static org.hamcrest.Matchers.is;
//
//@MockMvcTest
//class TagControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    @DisplayName("모든 태그 json으로 응답")
//    void findAllTags() throws Exception {
//
////        when, then, ResponseEntity 적용 후, jsonPath를 이용해 확인하기
//        mockMvc.perform(get("/tags"))
//                .andDo(print())
//                .andExpect(jsonPath("$.parent.length()", is(4)))
//                .andExpect(jsonPath("$.children.프레임워크.length()", is(12)))
//                .andExpect(jsonPath("$.children.etc.length()", is(23)))
//                .andExpect(jsonPath("$.children.개발.length()", is(6)))
//                .andExpect(jsonPath("$.children.프로그래밍언어.length()", is(17)));
//    }
//}
