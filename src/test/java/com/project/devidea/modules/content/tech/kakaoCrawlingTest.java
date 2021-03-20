//package com.project.devidea.modules.content.tech;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Transactional
//@SpringBootTest
//class kakaoCrawlingTest {
//
//    @Autowired
//    KakaoCrawling kakaoCrawling;
//    @Autowired
//    TechBlogRepository techBlogRepository;
//
//    @Test
//    public void 테스트() throws Exception {
//        //given
//        kakaoCrawling.connect();
//        kakaoCrawling.executeCrawling();
//        //when
//        List<TechBlog> all = techBlogRepository.findAll();
//        all.forEach(it->{
//            System.out.println(it.toString());
//        });
////        techBlogRepository.deleteAll();
//        //then
//    }
//}
