package com.project.devidea.modules.content.study;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.infra.WithAccount;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountRepository;
import com.project.devidea.modules.content.study.form.StudyDetailForm;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudyMakingForm;
import com.project.devidea.modules.content.study.form.StudySearchForm;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import com.project.devidea.modules.content.study.repository.StudyRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@MockMvcTest
class StudyControllerTest {


    @Autowired
    StudyService studyService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ModelMapper studyMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyMemberRepository studyMemberRepository;
    @Autowired
    StudySampleGenerator studySampleGenerator;
    @Autowired
    EntityManager entityManager;
    Account User1,User2;

    StudySearchForm BASE_REQUEST;
    @BeforeEach
    public void init() {
        User1 = new Account().builder()
                .email("ggg@gmail.com")
                .name("user1")
                .nickname("user1")
                .password("password")
                .build();
        User2 = new Account().builder()
                .email("bbb@gmail.com")
                .name("user2")
                .nickname("user2")
                .password("password")
                .build();
        accountRepository.save(User1);
        accountRepository.save(User2);
        studyRepository.deleteAll();
        //given 계정1개준비 및 스터디 30개 준비
    }

    @Test
    @WithAnonymousUser
    @DisplayName("[스터디 조회] GET /study")
    void 조회() throws Exception {

        //given : 스터디 100개준비
        List<Study> studies = studySampleGenerator.generateDumy(100);
        studyRepository.saveAll(studies);
        //when 전체검색
        BASE_REQUEST = new StudySearchForm().builder()
                .pageSize(100).build();
        //then : 조회된 StudyList갯수 100개
        MvcResult result = mockMvc.perform(get("/study")
                .content(objectMapper.writeValueAsString(BASE_REQUEST))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String strResult = result.getResponse().getContentAsString();
        List<StudyListForm> response = objectMapper.readValue(strResult, new TypeReference<List<StudyListForm>>() {
        });
        Assertions.assertEquals(response.size(), 100);
    }

    @Test
    @DisplayName("[스터디 등록] POST /study")
    @WithAccount(NickName ="근우")
    void 등록() throws Exception {

        //given : 스터디 1개준비
        Study study = studySampleGenerator.generateDumy(1).get(0);
        StudyMakingForm studyMakingForm = studyMapper.map(study, StudyMakingForm.class);
        //when 스터디 등록했을경우
        MvcResult result = mockMvc.perform(post("/study")
                .content(objectMapper.writeValueAsString(studyMakingForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String strResult = result.getResponse().getContentAsString();
        StudyDetailForm response = objectMapper.readValue(strResult, StudyDetailForm.class);
        Assertions.assertAll(
                () -> assertEquals(response.getTitle(), study.getTitle()),
                () -> assertEquals(studyRepository.findAll().size(), 1)
        );
    }

    @Test
    @DisplayName("[내 스터디] POST /study/mystudy")
    @WithAccount(NickName ="하이하이")
    void 내스터디() throws Exception {
        Account me=accountRepository.findByNickname("하이하이");
        List<Study> studies = studySampleGenerator.generateDumy(30);
        //given 스터디 30개를 account를 등록
        Iterator<Study> studyIterator=studies.listIterator();
        while(studyIterator.hasNext()){
            Study study=studyIterator.next();
            study.addMember(me,Study_Role.회원);
            studyRepository.save(study);
        }
        //when 조회
        MvcResult result=mockMvc.perform(post("/study/mystudy"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        String strResult=result.getResponse().getContentAsString();
        List<StudyListForm> studyListForms= objectMapper.readValue(strResult,
                new TypeReference<List<StudyListForm>>(){});
        //보이는 스터디가 30개가 맞는지 체크
        Assertions.assertEquals(studies.size(),studyListForms.size());
    }

    @Test
    @DisplayName("[스터디 디테일] Get /study/{id}")
    @WithMockUser
    void 스터디_자세한정보() throws Exception {
        //given 스터디 1개 만들기 거기에 member추가
        Study study=studySampleGenerator.generateDumy(1).get(0);
        study.addMember(User1,Study_Role.팀장);
        study.addMember(User2,Study_Role.회원);
        studyRepository.save(study);
        //when 스터디 자세한 정보 조회
        MvcResult result=mockMvc.perform(get("/study/{id}",study.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        String strResult=result.getResponse().getContentAsString();
        StudyDetailForm studyDetailForm= objectMapper.readValue(strResult,
                StudyDetailForm.class);
        //해당스터디의 자세한정보의 필드중 하나인 맴버들이 잘 주입됐는지 체크..
        Assertions.assertEquals(studyDetailForm.getMembers().toString(),study.getMembers().toString());

    }

    @Test
    @DisplayName("[스터디 삭제] Post /study/{id}/delete")
    @WithAccount(NickName = "권위자")
    void 스터디_삭제() throws Exception {
        //given 스터디 1개 만들기 거기에 member 및 팀장 추가
        Account admin=accountRepository.findByNickname("권위자");
        Study study=studySampleGenerator.generateDumy(1).get(0);
        study.addMember(admin,Study_Role.팀장);
        study.addMember(User1,Study_Role.회원);
        study.addMember(User2,Study_Role.회원);
        studyRepository.save(study);
        Long before=studyMemberRepository.count();
        //when 스터디 권위자 계정으로 삭제
        mockMvc.perform(post("/study/{id}/delete",study.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        //스터디가 잘 삭제 됐는지 체크
        Study deletedStudy=studyRepository.findById(study.getId()).orElse(new Study());
        StudyMember studyMember=studyMemberRepository.findByStudyAndMember(study,admin);
        Assertions.assertAll(
                ()-> assertEquals(User1.getStudies().size(),0),
                ()-> assertNull(deletedStudy.getId()),
                ()-> assertEquals(studyMemberRepository.count(),0)
        );
    }

    @Test
    @DisplayName("[스터디 나가기] Post /study/{id}/leave")
    @WithAccount(NickName = "스터디원") //팀장일때 나가면 스터디 삭제
    void 스터디에서_나가기() throws Exception {
        //given 스터디 1개 만들기 거기에 member 및 팀장 추가
        Account me=accountRepository.findByNickname("스터디원");
        Study study=studySampleGenerator.generateDumy(1).get(0);
        study.addMember(me,Study_Role.회원);
        study.addMember(User1,Study_Role.팀장);
        study.addMember(User2,Study_Role.회원);
        studyRepository.save(study);
        //when 스터디 권위자 계정으로 삭제
        mockMvc.perform(post("/study/{id}/leave",study.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        entityManager.flush();
        //then 1:스터디에서 스터디원이 있는지 체크
        Iterator<Account> accountIterator=study.getMember().listIterator();
        while(accountIterator.hasNext()){
            assertFalse(accountIterator.next().equals(me));
        }
        //then 2:스터디_멤버 도메인에서도 사라졌는지 체크
        StudyMember studyMember=studyMemberRepository.findByStudyAndMember(study,me);
        assertNull(studyMember);
    }

    @Test
    void 스터디_승인() {
    }

    @Test
    void 스터디_거절() {
    }

    @Test
    void 가입_신청_리스트() {
    }

    @Test
    void 가입_신청_디테일_보기() {
    }

    @Test
    void 스터디_공개_및_모집여부_설정폼() {
    }

    @Test
    void 스터디_공개_및_모집여부_변경() {
    }

    @Test
    void 지역_태그_설정폼() {
    }

    @Test
    void 지역_태그_설정_변경() {
    }
}