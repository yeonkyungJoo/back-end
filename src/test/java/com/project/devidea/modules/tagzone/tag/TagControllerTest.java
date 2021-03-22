package com.project.devidea.modules.tagzone.tag;

import com.project.devidea.infra.MockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.is;

@MockMvcTest
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TagRepository tagRepository;

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

    @Test
    @DisplayName("모든 태그 json으로 응답")
    void findAllTags() throws Exception {

//        when, then, ResponseEntity 적용 후, jsonPath를 이용해 확인하기
        mockMvc.perform(get("/tags"))
                .andDo(print())
                .andExpect(jsonPath("$.parent.length()", is(4)))
                .andExpect(jsonPath("$.children.프레임워크.length()", is(12)))
                .andExpect(jsonPath("$.children.etc.length()", is(23)))
                .andExpect(jsonPath("$.children.개발.length()", is(6)))
                .andExpect(jsonPath("$.children.프로그래밍언어.length()", is(17)));
    }
}
