//package com.project.devidea.modules.content.techNews;
//
//
//import com.project.devidea.infra.MockMvcTest;
//import com.project.devidea.modules.content.techNews.TechNewsRepository;
//import com.project.devidea.modules.content.techNews.crawling.DaangnCrawling;
//import com.project.devidea.modules.content.techNews.crawling.JavableCrawling;
//import com.project.devidea.modules.content.techNews.crawling.KakaoCrawling;
//import com.project.devidea.modules.content.techNews.crawling.LineCrawling;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ActiveProfiles("test")
////@Transactional
////@SpringBootTest
////@AutoConfigureDataMongo
//@DataMongoTest
//public class TechNewsRepositoryTest {
//
//    @Autowired
//    TechNewsRepository techNewsRepository;
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
//    @DisplayName("제목과 내용으로 중복된 테크뉴스 확인_존재")
//    public void existsByTitleAndContent_exist() throws Exception {
//
//        //given : title : 카카오 제목1, content : 카카오 내용1 으로 저장되어 있는 테크 뉴스 존재
//        //when : 제목과 내용으로 해당 테크 뉴스 존재 확인
//        boolean result = techNewsRepository.existsByTitleAndContent("카카오 제목1", "카카오 내용1");
//
//        //then : 존재 여부 확인
//        assertThat(result).isEqualTo(true);
//    }
//
//    @Test
//    @DisplayName("제목과 내용으로 중복된 테크뉴스 확인_존재하지 않음")
//    public void existsByTitleAndContent_notExist() throws Exception {
//
//        //given
//        //when : 존재하지 않는 제목과 내용으로 테크 뉴스 조회
//        boolean result = techNewsRepository.existsByTitleAndContent("존재하지 않는 제목", "존재하지 않는 내용");
//
//        //then : 존재 하지 않음 확인
//        assertThat(result).isEqualTo(false);
//    }
//
//    @Test
//    @DisplayName("전체 테크뉴스 조회")
//    public void findAll() throws Exception {
//
//        //given : 테크뉴스 당근마켓 3개, Javable 3개, 카카오 3개, 라인 3개 존재
//        //when : 전체 테크뉴스 조회
//        List<TechNews> allTechNews = techNewsRepository.findAll();
//
//        //then : 전체 12개 존재 확인
//        assertThat(allTechNews.size()).isEqualTo(12);
//    }
//
//    @Test
//    @DisplayName("당근 마켓 테크 뉴스 조회")
//    public void findAllByTechSite_daangn() throws Exception {
//
//        //given : 당근 마켓 테크 뉴스 3개 존재
//        //when : 당근 마켓 테크 뉴스 조회
//        List<TechNews> techNewsList = techNewsRepository.findAllByTechSite(TechSite.DAANGN);
//
//        //then : 3개의 테크뉴스 정상 조회 확인
//        String[] titles = new String[] {"당근 제목1","당근 제목2","당근 제목3"};
//        assertAll(
//                () -> assertThat(techNewsList.size()).isEqualTo(3),
//                () -> techNewsList.forEach(techNews -> assertThat(titles)
//                    .contains(techNews.getTitle()))
//        );
//    }
//
//    @Test
//    @DisplayName("Javable 마켓 테크 뉴스 조회")
//    public void findAllByTechSite_javable() throws Exception {
//
//        //given : Javable 테크 뉴스 3개 존재
//        //when : Javable 테크 뉴스 조회
//        List<TechNews> techNewsList = techNewsRepository.findAllByTechSite(TechSite.JAVABLE);
//
//        //then : 3개의 테크뉴스 정상 조회 확인
//        String[] titles = new String[] {"Javable 제목1","Javable 제목2","Javable 제목3"};
//        assertAll(
//                () -> assertThat(techNewsList.size()).isEqualTo(3),
//                () -> techNewsList.forEach(techNews -> assertThat(titles)
//                        .contains(techNews.getTitle()))
//        );
//    }
//
//    @Test
//    @DisplayName("카카오 테크 뉴스 조회")
//    public void findAllByTechSite_kakao() throws Exception {
//
//        //given : 카카오 테크 뉴스 3개 존재
//        //when : 카카오 테크 뉴스 조회
//        List<TechNews> techNewsList = techNewsRepository.findAllByTechSite(TechSite.KAKAO);
//
//        //then : 3개의 테크뉴스 정상 조회 확인
//        String[] titles = new String[] {"카카오 제목1","카카오 제목2","카카오 제목3"};
//        assertAll(
//                () -> assertThat(techNewsList.size()).isEqualTo(3),
//                () -> techNewsList.forEach(techNews -> assertThat(titles)
//                        .contains(techNews.getTitle()))
//        );
//    }
//
//    @Test
//    @DisplayName("라인 테크 뉴스 조회")
//    public void findAllByTechSite_line() throws Exception {
//
//        //given : 라인 테크 뉴스 3개 존재
//        //when : 라인 테크 뉴스 조회
//        List<TechNews> techNewsList = techNewsRepository.findAllByTechSite(TechSite.LINE);
//
//        //then : 3개의 테크뉴스 정상 조회 확인
//        String[] titles = new String[] {"라인 제목1","라인 제목2","라인 제목3"};
//        assertAll(
//                () -> assertThat(techNewsList.size()).isEqualTo(3),
//                () -> techNewsList.forEach(techNews -> assertThat(titles)
//                        .contains(techNews.getTitle()))
//        );
//    }
//}
