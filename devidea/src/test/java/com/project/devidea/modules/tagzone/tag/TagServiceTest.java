package com.project.devidea.modules.tagzone.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TagServiceTest {
    @Autowired
    TagRepository tagRepository;

    @DisplayName("Tag 잘주입됐는지 확인")
    @Test
    void Print() {
        List<Tag> tagList = tagRepository.findAll();
        tagList.stream().forEach((tag) -> System.out.println("tag:"+tag.toString()));
        return;
    }
}