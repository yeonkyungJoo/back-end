//package com.project.devidea.modules.content.techNews;
//
//import com.project.devidea.modules.content.techNews.crawling.DaangnCrawling;
//import com.project.devidea.modules.content.techNews.crawling.JavableCrawling;
//import com.project.devidea.modules.content.techNews.crawling.KakaoCrawling;
//import com.project.devidea.modules.content.techNews.crawling.LineCrawling;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ActiveProfiles("test")
//@Transactional
//@SpringBootTest
//class CrawlingTest {
//
//    @Autowired
//    DaangnCrawling daangnCrawling;
//
//    @Autowired
//    JavableCrawling javableCrawling;
//
//    @Autowired
//    KakaoCrawling kakaoCrawling;
//
//    @Autowired
//    LineCrawling lineCrawling;
//
//    @Autowired
//    TechNewsRepository techBlogRepository;
//
//    @Test
//    @DisplayName("당근마켓 크롤링")
//    public void DaangnCrawling() {
//
//        //given
//        //when : 당근마켓 기술블로그 크롤링
//        daangnCrawling.connect();
//        daangnCrawling.executeCrawling();
//        List<TechNews> techBlogs = techBlogRepository.findAll();
//        techBlogs = techBlogs.stream().filter(techBlog -> techBlog.getTechSite().equals(TechSite.DAANGN)).collect(Collectors.toList());
//
//        //then : 크롤링 된 당근마켓 기술블로그 1개이상 존재, 제공 정보 모두 정상 크롤링되었는지 확인
//        assertTrue(techBlogs.size() > 0);
//        TechNews techBlog = techBlogs.get(0);
//        assertAll(
//                () -> assertNotNull(techBlog.getTitle()),
//                () -> assertNotNull(techBlog.getImgUrl()),
//                () -> assertNotNull(techBlog.getUrl()),
//                () -> assertNotNull(techBlog.getContent()),
//                () -> assertNotNull(techBlog.getWriterImgUrl()),
//                () -> assertNotNull(techBlog.getWriterName()),
//                () -> assertNotNull(techBlog.getCreatedDate())
//        );
//    }
//
//    @Test
//    @DisplayName("Javable 크롤링")
//    public void JavableCrawling() {
//
//        //given
//        //when : Javable 기술블로그 크롤링
//        javableCrawling.connect();
//        javableCrawling.executeCrawling();
//        List<TechNews> techBlogs = techBlogRepository.findAll();
//        techBlogs = techBlogs.stream().filter(techBlog -> techBlog.getTechSite().equals(TechSite.JAVABLE)).collect(Collectors.toList());
//
//        //then : 크롤링 된 Javable 기술블로그 1개이상 존재, 제공 정보 모두 정상 크롤링되었는지 확인
//        assertTrue(techBlogs.size() > 0);
//        TechNews techBlog = techBlogs.get(0);
//        assertAll(
//                () -> assertNotNull(techBlog.getTitle()),
//                () -> assertNotNull(techBlog.getImgUrl()),
//                () -> assertNotNull(techBlog.getUrl()),
//                () -> assertNotNull(techBlog.getContent()),
//                () -> assertNotNull(techBlog.getWriterImgUrl()),
//                () -> assertNotNull(techBlog.getWriterName()),
//                () -> assertNotNull(techBlog.getCreatedDate())
//        );
//    }
//
//    @Test
//    @DisplayName("카카오 크롤링")
//    public void KakaoCrawling() {
//
//        //given
//        //when : Kakao 기술블로그 크롤링
//        kakaoCrawling.connect();
//        kakaoCrawling.executeCrawling();
//        List<TechNews> techBlogs = techBlogRepository.findAll();
//        techBlogs = techBlogs.stream().filter(techBlog -> techBlog.getTechSite().equals(TechSite.KAKAO)).collect(Collectors.toList());
//
//        //then : 크롤링 된 Kakao 기술블로그 1개이상 존재, 제공 정보 모두 정상 크롤링되었는지 확인
//        assertTrue(techBlogs.size() > 0);
//        TechNews techBlog = techBlogs.get(0);
//        assertAll(
//                () -> assertNotNull(techBlog.getTitle()),
//                () -> assertNotNull(techBlog.getImgUrl()),
//                () -> assertNotNull(techBlog.getUrl()),
//                () -> assertNotNull(techBlog.getContent()),
//                () -> assertNotNull(techBlog.getWriterImgUrl()),
//                () -> assertNotNull(techBlog.getWriterName()),
//                () -> assertNotNull(techBlog.getCreatedDate())
//        );
//    }
//
//    @Test
//    @DisplayName("라인 크롤링")
//    public void LineCrawling() {
//
//        //given
//        //when : Line 기술블로그 크롤링
//        lineCrawling.connect();
//        lineCrawling.executeCrawling();
//        List<TechNews> techBlogs = techBlogRepository.findAll();
//        techBlogs = techBlogs.stream().filter(techBlog -> techBlog.getTechSite().equals(TechSite.LINE)).collect(Collectors.toList());
//
//        //then : 크롤링 된 Line 기술블로그 1개이상 존재, 제공 정보 모두 정상 크롤링되었는지 확인
//        assertTrue(techBlogs.size() > 0);
//        TechNews techBlog = techBlogs.get(0);
//        assertAll(
//                () -> assertNotNull(techBlog.getTitle()),
//                () -> assertNotNull(techBlog.getImgUrl()),
//                () -> assertNotNull(techBlog.getUrl()),
//                () -> assertNotNull(techBlog.getContent()),
//                () -> assertNotNull(techBlog.getWriterImgUrl()),
//                () -> assertNotNull(techBlog.getWriterName()),
//                () -> assertNotNull(techBlog.getCreatedDate())
//        );
//    }
//}
