package com.project.devidea.modules.tagzone.tag;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class TagServiceTest {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagService tagService;

    @DisplayName("Tag 잘주입됐는지 확인")
    @Test
    void Print() {
        List<Tag> tagList = tagRepository.findAll();
        tagList.stream().forEach((tag) -> System.out.println("tag:" + tag.toString()));
        return;
    }

    @Test
    @DisplayName("모든 태그 가져오고 계층형 구조로 표현하기")
    void findAllTagsTest() throws Exception {

//        when
        TagsResponseDto response = tagService.findAll();
        List<String> parent = response.getParent();
        Map<String, List<String>> children = response.getChildren();

//        then
        assertEquals(parent.size(), 4);
        assertEquals(children.keySet().size(), 4);

//        print
        parent.forEach(str -> log.info("parent : {}", str));
        log.info("==============================구분===================================");
        children.entrySet().forEach(str -> log.info("children : {}", str));
    }
}