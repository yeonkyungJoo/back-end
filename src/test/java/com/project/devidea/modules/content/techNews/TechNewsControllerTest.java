//package com.project.devidea.modules.content.techNews;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.devidea.infra.MockMvcTest;
//import org.assertj.core.api.Assertions;
//import org.junit.Before;
//
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@MockMvcTest
//public class TechNewsControllerTest {
//
//    @Autowired
//    TechNewsRepository techNewsRepository;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @BeforeAll
//    public static void init(@Autowired TechNewsRepository techNewsRepository) {
//        List<TechNews> daangnTechNews = TechNewsDummy.getDaangnTechNews();
//        List<TechNews> javableTechNews = TechNewsDummy.getJavableTechNews();
//        List<TechNews> kakaoTechNews = TechNewsDummy.getKakaoTechNews();
//        List<TechNews> lineTechNews = TechNewsDummy.getLineTechNews();
//        techNewsRepository.saveAll(daangnTechNews);
//        techNewsRepository.saveAll(javableTechNews);
//        techNewsRepository.saveAll(kakaoTechNews);
//        techNewsRepository.saveAll(lineTechNews);
//    }
//
//    @Test
//    @DisplayName("전체 테크 뉴스 조회")
//    public void getAllTechNews() throws Exception {
//
//        //given : 테크뉴스 당근마켓 3개, Javable 3개, 카카오 3개, 라인 3개 존재
//        //when : get, /techNews 으로 요청 시 전체 테크 뉴스 조회
//        MvcResult mvcResult = mockMvc.perform(get("/techNews"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        //then : 12개의 테크 뉴스 정상 조회 확인
//        String strResult = mvcResult.getResponse().getContentAsString();
//        List<TechNews> response = objectMapper.readValue(strResult,
//                new TypeReference<List<TechNews>>() {});
//        assertThat(response.size()).isEqualTo(12);
//    }
//
//    @Test
//    @DisplayName("당근마켓 테크뉴스 조회")
//    public void getDaangnTechNews() throws Exception {
//
//        //given : 당근마켓 테크뉴스 3개 존재
//        //when : get, /techNews/daangn 으로 요청 시 당근마켓 테크 뉴스 조회
//        MvcResult mvcResult = mockMvc.perform(get("/techNews/daangn"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        //then : 3개의 당큰마켓 테크 뉴스 정상 조회 확인
//        String strResult = mvcResult.getResponse().getContentAsString();
//        List<TechNews> response = objectMapper.readValue(strResult,
//                new TypeReference<List<TechNews>>() {});
//
//        String[] titles = new String[] {"당근 제목1","당근 제목2","당근 제목3"};
//        assertAll(
//                () -> assertThat(response.size()).isEqualTo(3),
//                () -> response.forEach(techNews -> assertThat(titles)
//                    .contains(techNews.getTitle()))
//        );
//    }
//
//    @Test
//    @DisplayName("Javable 테크뉴스 조회")
//    public void getJavableTechNews() throws Exception {
//
//        //given : Javable 테크뉴스 3개 존재
//        //when : get, /techNews/javable 으로 요청 시 Javable 테크 뉴스 조회
//        MvcResult mvcResult = mockMvc.perform(get("/techNews/javable"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        //then : 3개의 Javable 테크 뉴스 정상 조회 확인
//        String strResult = mvcResult.getResponse().getContentAsString();
//        List<TechNews> response = objectMapper.readValue(strResult,
//                new TypeReference<List<TechNews>>() {});
//
//        String[] titles = new String[] {"Javable 제목1","Javable 제목2","Javable 제목3"};
//        assertAll(
//                () -> assertThat(response.size()).isEqualTo(3),
//                () -> response.forEach(techNews -> assertThat(titles)
//                        .contains(techNews.getTitle()))
//        );
//    }
//
//    @Test
//    @DisplayName("카카오 테크뉴스 조회")
//    public void getKakaoTechNews() throws Exception {
//
//        //given : 카카오 테크뉴스 3개 존재
//        //when : get, /techNews/kakao 으로 요청 시 카카오 테크 뉴스 조회
//        MvcResult mvcResult = mockMvc.perform(get("/techNews/kakao"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        //then : 3개의 카카오 테크 뉴스 정상 조회 확인
//        String strResult = mvcResult.getResponse().getContentAsString();
//        List<TechNews> response = objectMapper.readValue(strResult,
//                new TypeReference<List<TechNews>>() {});
//
//        String[] titles = new String[] {"카카오 제목1","카카오 제목2","카카오 제목3"};
//        assertAll(
//                () -> assertThat(response.size()).isEqualTo(3),
//                () -> response.forEach(techNews -> assertThat(titles)
//                        .contains(techNews.getTitle()))
//        );
//    }
//
//    @Test
//    @DisplayName("라인 테크뉴스 조회")
//    public void getLineTechNews() throws Exception {
//
//        //given : 라인 테크뉴스 3개 존재
//        //when : get, /techNews/line 으로 요청 시 라인 테크 뉴스 조회
//        MvcResult mvcResult = mockMvc.perform(get("/techNews/line"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        //then : 3개의 라인 테크 뉴스 정상 조회 확인
//        String strResult = mvcResult.getResponse().getContentAsString();
//        List<TechNews> response = objectMapper.readValue(strResult,
//                new TypeReference<List<TechNews>>() {});
//
//        String[] titles = new String[] {"라인 제목1","라인 제목2","라인 제목3"};
//        assertAll(
//                () -> assertThat(response.size()).isEqualTo(3),
//                () -> response.forEach(techNews -> assertThat(titles)
//                        .contains(techNews.getTitle()))
//        );
//    }
//}
