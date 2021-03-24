//package com.project.devidea.modules.tagzone.tag;
//
//import com.project.devidea.infra.TestConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//@ActiveProfiles("test")
//@Import(TestConfig.class)
//@Slf4j
//class TagRepositoryTest {
//
//    @Autowired
//    TagRepository tagRepository;
//
//    /**
//     * initService를 주석한 상태에서 진행한 beforeEach입니다.
//     * init이 정상적으로 작동되면 이 메서드를 지워주세요! -범석
//     */
//    @BeforeEach
//    void init() throws IOException {
//        if (tagRepository.count() == 0) {
//            Resource resource = new ClassPathResource("tag_kr.csv");
//            Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
//                    .forEach(line -> {
//                        String[] split = line.split(",");
//                        Tag tag = Tag.builder()
//                                .firstName(split[1])
//                                .secondName(split[2].equals("null") ? null : split[2])
//                                .thirdName(split[3].equals("null") ? null : split[3])
//                                .build();
//                        if (!split[0].equals("parent")) {
//                            Tag tagParent = tagRepository.findByFirstName(split[0]);
//                            tagParent.addChild(tag);
//                        }
//                        tagRepository.save(tag);
//                    });
//        }
//    }
//
//    @Test
//    @DisplayName("모든 태그 가져오기 테스트")
//    void findAllTags() throws Exception {
//
////        when
//        List<Tag> tags = tagRepository.findAll();
//
////        then
//        assertEquals(tags.size(), 62);
//        for (Tag tag : tags) {
////            log.info("tag = {}", tag);
//        }
//    }
//
//    @Test
//    @DisplayName("내가 선택한 태그 가져오기")
//    void findByFirstNameInTest() throws Exception {
//
////        given
//        List<String> findString = Arrays.asList("java", "python", "javascript");
//
////        when
//        List<Tag> tags = tagRepository.findByFirstNameIn(findString);
//        List<String> firstNames = tags.stream().map(Tag::getFirstName).collect(Collectors.toList());
//        List<String> secondNames = tags.stream().map(Tag::getSecondName).collect(Collectors.toList());
//
////        then
//        assertEquals(tags.size(), 3);
//        assertThat(firstNames).contains("java", "python", "javascript");
//        assertThat(secondNames).contains("자바", "파이썬", "자바스크립트");
//    }
//}
