package com.project.devidea.modules.tagzone.tag;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    /**
     * initService를 주석한 상태에서 진행한 beforeEach입니다.
     * init이 정상적으로 작동되면 이 메서드를 지워주세요! -범석
     */
    @BeforeEach
    void init() throws Exception{
        if (tagRepository.count() == 0) {
            Resource resource = new ClassPathResource("tag_kr.csv");
            Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .forEach(line -> {
                        String[] split = line.split(",");
                        Tag tag = Tag.builder()
                                .firstName(split[1])
                                .secondName(split[2].equals("null")?null:split[2])
                                .thirdName(split[3].equals("null")?null:split[3])
                                .build();
                        if (!split[0].equals("parent")){
                            Tag tagParent = tagRepository.findByFirstName(split[0]);
                            tagParent.addChild(tag);
                        }
                        tagRepository.save(tag);
                    });
        }
    }

    @DisplayName("Tag 잘주입됐는지 확인")
    @Test
    void Print() {
        List<Tag> tagList = tagRepository.findAll();
        tagList.stream().forEach((tag) -> System.out.println("tag:" + tag.toString()));
        return;
    }

    @Test
    @DisplayName("모든 태그 가져오기")
    void findAllTagsTest() throws Exception {

//        when
        TagsResponseDto response = tagService.findAll();
        List<String> parent = response.getParent();
        Map<String, List<String>> children = response.getChildren();

//        then
        assertEquals(parent.size(), 4);
        assertEquals(children.keySet().size(), 4);
    }
}
