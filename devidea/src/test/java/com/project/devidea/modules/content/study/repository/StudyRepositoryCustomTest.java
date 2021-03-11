package com.project.devidea.modules.content.study.repository;

import com.project.devidea.infra.aop.annotation.LogExecutionTime;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudySampleGenerator;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudyRequestForm;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class StudyRepositoryCustomTest {
    static int MAX_DATA_SIZE = 300;

    @Autowired
    StudyRepository studyRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    StudySampleGenerator studySampleGenerator;

    final String LOCAL_CITY_NAME = "서울특별시";
    final String PROVINCE = "송파구";
    final String KEYWORD = "spring";
    final List<String> TAGS = Arrays.asList("spring", "hadoop", "java", "ai", "웹개발", "백앤드");

    StudyRequestForm BASE_REQUEST;
    StudyRequestForm KEYWORD_REQUEST;
    StudyRequestForm TAG_REQUEST;
    StudyRequestForm ZONE_REQUEST;
    StudyRequestForm RECRUITING_REQUEST;
    StudyRequestForm TOTAL_REQUEST;

    @DisplayName("셋팅_given")
    @PostConstruct
    public void 셋팅() {

        //given
        BASE_REQUEST = new StudyRequestForm().builder()
                .pageSize(MAX_DATA_SIZE).build();

        KEYWORD_REQUEST = new StudyRequestForm().builder()
                .keyword(KEYWORD)
                .pageSize(MAX_DATA_SIZE)
                .build();

        TAG_REQUEST = new StudyRequestForm().builder()
                .tags(TAGS)
                .pageSize(MAX_DATA_SIZE)
                .build();

        ZONE_REQUEST = new StudyRequestForm().builder()
                .localNameOfCity(LOCAL_CITY_NAME)
                .province(PROVINCE)
                .pageSize(MAX_DATA_SIZE)
                .build();
        RECRUITING_REQUEST = new StudyRequestForm().builder()
                .recruiting(true)
                .pageSize(MAX_DATA_SIZE)
                .build();
        TOTAL_REQUEST = new StudyRequestForm().builder()
                .keyword(KEYWORD)
                .tags(TAGS)
                .pageSize(20)
                .localNameOfCity(LOCAL_CITY_NAME)
                .recruiting(true)
                .build();
    }

    @BeforeEach
    void InitData() {
        studyRepository.saveAll(studySampleGenerator.generateDumy(MAX_DATA_SIZE));
        List<Study> studyList = studyRepository.findAll();
    }

    @DisplayName("기본 검색 테스트")
    @LogExecutionTime
    @Test
    @Transactional
    void 기본검색_테스트() {
        //when
        List<StudyListForm> studyList = studyRepository.findByCondition(BASE_REQUEST);
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
        List<StudyListForm> listFormList = studyRepository.findByCondition(KEYWORD_REQUEST);
        //then
        listFormList.stream().forEach(studyListForm -> {
            assert (studyListForm.getTitle().contains(KEYWORD)) || (
                    studyListForm.getTags().contains(KEYWORD)) ||
                    studyListForm.getProvince().contains(KEYWORD) ||
                    studyListForm.getLocalNameOfCity().contains(KEYWORD);
        });
    }

    @Test
    @DisplayName("태그 검색 테스트")
    @LogExecutionTime
    void 태그검색_테스트() {
        //when
        List<StudyListForm> listFormList = studyRepository.findByCondition(TAG_REQUEST);
        //then
        listFormList.stream().forEach(studyListForm -> {
            Assertions.assertNotNull(studyListForm.getTags().stream()
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
        List<StudyListForm> listFormList = studyRepository.findByCondition(ZONE_REQUEST);
        //then
        listFormList.stream().forEach(studyListForm -> {
            assert studyListForm.getLocalNameOfCity().equals(LOCAL_CITY_NAME);
            assert studyListForm.getProvince().equals(PROVINCE);
        });
    }

    @Test
    @DisplayName("모집중인 스터디 검색 테스트")
    @LogExecutionTime
    void 모집중인_스터디_검색_테스트() {
        //when
        List<StudyListForm> listFormList = studyRepository.findByCondition(RECRUITING_REQUEST);
        //then
        listFormList.stream().forEach(studyListForm -> {
            assert studyListForm.isRecruiting() == true;
        });
    }

    @Test
    @DisplayName("통합 스터디 검색 테스트")
    @LogExecutionTime
    void 통합_스터디_검색_테스트() {
        //when
        List<StudyListForm> listFormList = studyRepository.findByCondition(TOTAL_REQUEST);
        //then
        listFormList.stream().forEach(studyListForm -> {
            assertAll(
                    () -> assertEquals(studyListForm.isRecruiting(), true),
                    () -> assertEquals(studyListForm.getLocalNameOfCity(), LOCAL_CITY_NAME),
                    () -> assertEquals(studyListForm.getTitle().contains(KEYWORD) || (
                            studyListForm.getTags().contains(KEYWORD)) ||
                            studyListForm.getProvince().contains(KEYWORD) || studyListForm.getLocalNameOfCity().contains(KEYWORD), true),
                    () -> assertNotNull(studyListForm.getTags().stream()
                            .distinct()
                            .filter(TAGS::contains)
                            .collect(Collectors.toSet()))
            );
        });
    }
}