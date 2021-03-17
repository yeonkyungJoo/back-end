package com.project.devidea.modules.content.study.repository;

import com.project.devidea.infra.aop.annotation.LogExecutionTime;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudySampleGenerator;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudySearchForm;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@WithAnonymousUser
class StudyRepositoryCustomTest {
    static int MAX_DATA_SIZE = 300;

    @Autowired
    StudyRepository studyRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    StudySampleGenerator studySampleGenerator;

    final String CITY = "서울특별시";
    final String PROVINCE = "송파구";
    final String KEYWORD = "하둡";
    final List<String> TAGS = Arrays.asList("spring", "hadoop", "java", "ai", "웹개발", "백앤드");

    StudySearchForm BASE_REQUEST;
    StudySearchForm KEYWORD_REQUEST;
    StudySearchForm TAG_REQUEST;
    StudySearchForm ZONE_REQUEST;
    StudySearchForm RECRUITING_REQUEST;
    StudySearchForm TOTAL_REQUEST;

    @DisplayName("셋팅_given")
    @PostConstruct
    public void 셋팅() {

        //given
        BASE_REQUEST = new StudySearchForm().builder()
                .pageSize(MAX_DATA_SIZE).build();

        KEYWORD_REQUEST = new StudySearchForm().builder()
                .keyword(KEYWORD)
                .pageSize(MAX_DATA_SIZE)
                .build();

        TAG_REQUEST = new StudySearchForm().builder()
                .tags(TAGS)
                .pageSize(MAX_DATA_SIZE)
                .build();

        ZONE_REQUEST = new StudySearchForm().builder()
                .city(CITY)
                .province(PROVINCE)
                .pageSize(MAX_DATA_SIZE)
                .build();
        RECRUITING_REQUEST = new StudySearchForm().builder()
                .recruiting(true)
                .pageSize(MAX_DATA_SIZE)
                .build();
        TOTAL_REQUEST = new StudySearchForm().builder()
                .keyword(KEYWORD)
                .tags(TAGS)
                .pageSize(20)
                .city(CITY)
                .recruiting(true)
                .build();
    }

    @BeforeEach
    void InitData() {
        studyRepository.saveAll(studySampleGenerator.generateDumy(MAX_DATA_SIZE));
    }

    @DisplayName("기본 검색 테스트")
    @LogExecutionTime
    @Test
    @Transactional
    void 기본검색_테스트() {
        //when
        List<Study> studyList = studyRepository.findByCondition(BASE_REQUEST);
        //then
        assertAll(
                () -> assertEquals(studyList.size(), MAX_DATA_SIZE)
        );
    }

    @Test
    @DisplayName("키워드 검색 테스트")
    @LogExecutionTime
    void 키워드검색_테스트() {
        //when
        List<Study> studies = studyRepository.findByCondition(KEYWORD_REQUEST);
        //then
        studies.stream().forEach(study -> {
            assert (study.getTitle().contains(KEYWORD)) || (
                    study.getTags().contains(KEYWORD)) ||
                    study.getLocation().getProvince().contains(KEYWORD) ||
                    study.getLocation().getCity().contains(KEYWORD);
        });
    }

    @Test
    @DisplayName("태그 검색 테스트")
    @LogExecutionTime
    void 태그검색_테스트() {
        //when
        List<Study> studies = studyRepository.findByCondition(TAG_REQUEST);
        //then
        studies.stream().forEach(study -> {
            Assertions.assertNotNull(study.getTags().stream()
                            .distinct()
                            .filter(TAGS::contains)
                            .collect(Collectors.toSet()),
                    "일치하는 tag가 없습니다."
            );
        });
    }

    @Test
    @DisplayName("지역 검색 테스트")
    @LogExecutionTime
    void 지역_검색_테스트() {
        //when
        List<Study> studies = studyRepository.findByCondition(ZONE_REQUEST);
        //then
        studies.stream().forEach(study -> {
            assert study.getLocation().getCity().equals(CITY);
            assert study.getLocation().getProvince().equals(PROVINCE);
        });
    }

    @Test
    @DisplayName("모집중인 스터디 검색 테스트")
    @LogExecutionTime
    void 모집중인_스터디_검색_테스트() {
        //when
        List<Study> studies = studyRepository.findByCondition(RECRUITING_REQUEST);
        //then
        studies.stream().forEach(study -> {
            assert study.isRecruiting() == true;
        });
    }

    @Test
    @DisplayName("통합 스터디 검색 테스트")
    @LogExecutionTime
    void 통합_스터디_검색_테스트() {
        //when
        List<Study> studies = studyRepository.findByCondition(TOTAL_REQUEST);
        //then
        studies.stream().forEach(study -> {
            assertAll(
                    () -> assertEquals(study.isRecruiting(), true),
                    () -> assertEquals(study.getLocation().getCity(), CITY),
                    () -> assertEquals(study.getTitle().contains(KEYWORD) || (
                            study.getTags().contains(KEYWORD)) ||
                            study.getLocation().getProvince().contains(KEYWORD) || study.getLocation().getCity().contains(KEYWORD), true),
                    () -> assertNotNull(study.getTags().stream()
                            .distinct()
                            .filter(TAGS::contains)
                            .collect(Collectors.toSet()))
            );
        });
    }
}