package com.project.devidea.modules.content.tech;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class JavableCrawlingTest {

    @Autowired
    JavableCrawling javableCrawling;
    @Autowired
    TechBlogRepository techBlogRepository;

    @Test
    public void 테스트() throws Exception {
        //given
        javableCrawling.connect();
        javableCrawling.executeCrawling();
        //when
        List<TechBlog> all = techBlogRepository.findAll();
        System.out.println(all.size());
//        techBlogRepository.deleteAll();
        //then
    }
}
