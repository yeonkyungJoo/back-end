package com.project.devidea.modules.content.study.modelmapper;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.apply.StudyApply;
import com.project.devidea.modules.content.study.apply.StudyApplyForm;
import com.project.devidea.modules.content.study.form.StudyDetailForm;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudyMakingForm;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest(classes = {StudyMapperImpl.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class StudyMapperImplTest {
    @Autowired
    StudyMapperImpl studyMapper;
    Zone zone;
    Study study;
    StudyApply studyApply;
    Account admin;
    Account account;
    Set<Tag> tags;
    static final Boolean OPEN = true;
    static final int MAX_COUNT = 5;
    static int LIKES = 4;
    static int COUNTS = 1;
    static final Boolean RECRUITING = true;

    @PostConstruct
    void 셋팅() {
        Zone zone=new Zone().builder().city("서울").province("송파구").build();
        tags = new HashSet<>();
        tags.add(new Tag());
        tags.addAll(Arrays.asList(new Tag().builder().firstName("tag1")
                        .build(),
                new Tag().builder().firstName("tag2")
                        .build()));
        account = new Account().builder().id(1L)
                .email("근우")
                .build();
        admin = new Account().builder().id(1L)
                .email("admin")
                .build();
        study = new Study().builder().publishedDateTime(LocalDateTime.now())
                .title("title")
                .open(OPEN)
                .maxCount(MAX_COUNT)
                .recruiting(RECRUITING)
                .Likes(LIKES)
                .location(zone)
                .tags(tags)
                .shortDescription("shortDescription")
                .admin(account)
                .question("question")
                .counts(COUNTS)
                .fullDescription("fullDescription")
                .build();
        studyApply= new StudyApply().builder()
                .study(study)
                .account(admin)
                .answer("answer")
                .etc("etc")
                .build();
    }

    @Test
    void getStudyListMapper() {
        StudyListForm studyListForm = studyMapper.StudyList().map(study, StudyListForm.class);
        System.out.println(studyListForm);
    }

    @Test
    void getStudyApplyMapper() {
        StudyApplyForm studyApplyForm = studyMapper.StudyApply().map(studyApply, StudyApplyForm.class);
        System.out.println(studyApplyForm);
    }

    @Test
    void getStudyMakingMapper() {
        StudyMakingForm studyMakingForm = studyMapper.StudyMaking().map(study, StudyMakingForm.class);
        System.out.println(studyMakingForm);
    }

    @Test
    void getStudyDetailMapper() {
        StudyDetailForm studyDetailForm = studyMapper.StudyDetail().map(study, StudyDetailForm.class);
        System.out.println(studyDetailForm);
    }
}
