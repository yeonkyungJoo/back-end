package com.project.devidea.modules.content.study.repository;

import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudySampleGenerator;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudyRequestForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudyRepositoryCustomTest {
    @Autowired
    StudyRepository studyRepository;

    @Autowired
    StudySampleGenerator studySampleGenerator;

    StudyRequestForm KeyRequest;
    StudyRequestForm NoneKeyRequest;
    StudyRequestForm NoneRecruitingRequest;
    StudyRequestForm ReCrutingRequest;

    @BeforeEach
    @DisplayName("sampleData 100개 넣기")
    void setUp() {
        KeyRequest = new StudyRequestForm().builder()
                .keyword("spring")
                .page(0L)
                .pageSize(10)
                .build();
        NoneKeyRequest = new StudyRequestForm().builder()
                .page(1L)
                .pageSize(10)
                .build();
        studyRepository.saveAll(studySampleGenerator.generateDumy(100));
    }

    @Test
    @DisplayName("기본 검색 테스트")
    void 기본_테스트() {
        List<StudyListForm> listFormList = studyRepository.findByCondition(NoneKeyRequest);
        assertAll(
                () -> assertNotEquals(listFormList.size(), 0)
        );
    }
}