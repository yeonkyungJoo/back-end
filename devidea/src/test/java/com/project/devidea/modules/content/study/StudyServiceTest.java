package com.project.devidea.modules.content.study;

import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.modules.account.AccountRepository;
import com.project.devidea.modules.account.AccountService;
import com.project.devidea.modules.content.study.apply.StudyApplyRepository;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {
    @Mock
    ModelMapper studyMapper;
    @Mock
    StudyRepository studyRepository;
    @Mock
    ZoneRepository zoneRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    StudyApplyRepository studyApplyRepository;
    @Mock
    StudyMemberRepository studyMemberRepository;

    @Mock
    Study study;

    @InjectMocks
    StudyService studyService;

    @Test
    void searchByCondition() {

    }

    @Test
    void getDetailStudy() {
    }

    @Test
    void makingStudy() {
    }

    @Test
    void applyStudy() {
    }

    @Test
    void decideJoin() {
    }

    @Test
    void getApplyForm() {
    }

    @Test
    void deleteStudy() {
    }

    @Test
    void leaveStudy() {
    }

    @Test
    void myStudy() {
    }

    @Test
    void getApplyList() {
    }

    @Test
    void getApplyDetail() {
    }

    @Test
    void getOpenRecruitForm() {
    }

    @Test
    void getTagandZone() {
    }

    @Test
    void updateOpenRecruiting() {
    }

    @Test
    void updateTagAndZOne() {
    }
}
