//package com.project.devidea.modules.content.tech;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Transactional
//@SpringBootTest
//class DaangnCrawlingTest {
//
//    @Autowired
//    DaangnCrawling daangnCrawling;
//    @Autowired
//    TechBlogRepository techBlogRepository;
//
//    @Test
//    public void 테스트() throws Exception {
//        //given
//        daangnCrawling.connect();
//        daangnCrawling.executeCrawling();
//        //when
//        List<TechBlog> all = techBlogRepository.findAll();
//        all.forEach(it->{
//            System.out.println(it.toString());
//        });
////        techBlogRepository.deleteAll();
//        //then
//    }
//}
