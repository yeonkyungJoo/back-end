package com.project.devidea.modules.content.study;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.WithAccount;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.study.apply.StudyApply;
import com.project.devidea.modules.content.study.apply.StudyApplyForm;
import com.project.devidea.modules.content.study.apply.StudyApplyRepository;
import com.project.devidea.modules.content.study.form.StudyDetailForm;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudyMakingForm;
import com.project.devidea.modules.content.study.form.StudySearchForm;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import com.project.devidea.modules.content.study.repository.StudyRepository;

import com.project.devidea.utils.MultiValueMapConverter;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
    StudyApplyRepository studyApplyRepository;
    Account User1, User2;

    StudySearchForm BASE_REQUEST;

    @BeforeEach
    @Transactional
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
//        studyRepository.deleteAll();
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
       Object obj= MultiValueMapConverter.convert(BASE_REQUEST);
        MvcResult result = mockMvc.perform(get("/study")
                .queryParams(MultiValueMapConverter.convert(BASE_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String strResult = result.getResponse().getContentAsString();
        List<StudyListForm> response = objectMapper.readValue(strResult, new TypeReference<List<StudyListForm>>() {
        });
        Assertions.assertEquals(response.size(), 100);
    }

    @Test
    @DisplayName("[스터디 등록] POST /study")
    @WithAccount(NickName = "근우")
    void 등록() throws Exception {
        studyRepository.deleteAll();
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
    @WithAccount(NickName = "하이하이")
    void 내스터디() throws Exception {
        Account me = accountRepository.findByNickname("하이하이");
        List<Study> studies = studySampleGenerator.generateDumy(30);
        studyRepository.saveAll(studies);
        //given 스터디 30개를 account를 등록
        Iterator<Study> studyIterator = studies.listIterator();
        while (studyIterator.hasNext()) {
            Study study = studyIterator.next();
            studyService.addMember(me, study, StudyRole.팀장);
        }
        //when 조회
        MvcResult result = mockMvc.perform(post("/study/mystudy"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        String strResult = result.getResponse().getContentAsString();
        List<StudyListForm> studyListForms = objectMapper.readValue(strResult,
                new TypeReference<List<StudyListForm>>() {
                });
        //보이는 스터디가 30개가 맞는지 체크
        Assertions.assertEquals(studies.size(), studyListForms.size());
    }

        @Test
        @DisplayName("[스터디 디테일] Get /study/{id}")
        @WithMockUser
        void 스터디_자세한정보() throws Exception {
            Study study = studySampleGenerator.generateDumy(1).get(0);
            studyRepository.save(study);
            studyService.addMember(User1, study, StudyRole.팀장);
            studyService.addMember(User2, study, StudyRole.회원);
            //when 스터디 자세한 정보 조회
            MvcResult result = mockMvc.perform(get("/study/{id}", study.getId().toString()))
                    .andDo(print())
                    .andExpect(status().isOk()).andReturn();
            String strResult = result.getResponse().getContentAsString();
            StudyDetailForm studyDetailForm = objectMapper.readValue(strResult,
                    StudyDetailForm.class);
            //해당스터디의 자세한정보의 필드중 하나인 타이틀이 잘 주입됐는지 체크
            Assertions.assertEquals(studyDetailForm.getTitle().toString(), study.getTitle().toString());

        }




    @Test
    @DisplayName("[스터디 삭제] Post /study/{id}/delete")
    @WithAccount(NickName = "권위자")
    void 스터디_삭제() throws Exception {
        studyRepository.deleteAll();
        //given 스터디 1개 만들기 거기에 member 및 팀장 추가
        Account admin = accountRepository.findByNickname("권위자");
        Study study = studySampleGenerator.generateDumy(1).get(0);
        studyRepository.save(study);
        studyService.addMember(admin, study, StudyRole.팀장);
        studyService.addMember(User1, study, StudyRole.회원);
        studyService.addMember(User2, study, StudyRole.회원);
        List<StudyMember> studyMemberRepositoryAll = studyMemberRepository.findAll();
        //when 스터디 권위자 계정으로 삭제
        mockMvc.perform(post("/study/{id}/delete", study.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("[스터디 나가기] Post /study/{id}/leave")
    @WithAccount(NickName = "스터디원") //팀장일때 나가면 스터디 삭제
    void 스터디에서_나가기() throws Exception {
        //given 스터디 1개 만들기 및 가입하기
        Study study = studySampleGenerator.generateDumy(1).get(0);
        Account me = accountRepository.findByNickname("스터디원");
        studyRepository.save(study);
        studyService.addMember(me,study, StudyRole.회원);
        //when 스터디 나가기
        mockMvc.perform(post("/study/{id}/leave",study.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        // 맴버 체크
        assertFalse(study.getMembers().contains(me));
    }

    @DisplayName("스터디 가입신청, Get /study/apply")
    @Test
    @WithAccount(NickName = "me") //팀장일때 나가면 스터디 삭제
    void 가입_신청() throws Exception {
        //given 스터디 하나만들기
        Account account=accountRepository.findByNickname("me");
        Study study = studySampleGenerator.generateDumy(1).get(0);
        studyRepository.save(study);
        StudyApplyForm studyApplyForm=new StudyApplyForm().builder()
                .studyId(study.getId())
                .study(study.getTitle())
                .applicant(account.getNickname())
                .answer("answer")
                .etc("etc")
                .build();
        //when+then 요청했을시 제대로 작동하는지 체크
        mockMvc.perform(post("/study/{id}/apply",study.getId().toString())
                .content(objectMapper.writeValueAsString(studyApplyForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        Assertions.assertNotNull(studyApplyRepository.findByStudy_Id(study.getId()));
    }
    @Test
    @DisplayName("스터디-거절")
    @WithAccount(NickName = "me") //팀장일때 나가면 스터디 삭제
    void 스터디_거절() throws Exception {
        //given 1.스터디 만들기 2.스터디장을 me로 등록 3.User1이 지원
        Account account=accountRepository.findByNickname("me");
        Study study = studySampleGenerator.generateDumy(1).get(0);
        studyRepository.save(study);
        studyMemberRepository.save(studyService.generateStudyMember(study,account, StudyRole.팀장));
        StudyApply studyApply=studyService.generateStudyApply(study,User1);
        studyApplyRepository.save(studyApply);
        //when+then 거절했을시 잘 거절확인
        mockMvc.perform(post("/study/{study_id}/apply/{apply_id}/reject",study.getId().toString(),studyApply.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        Assertions.assertFalse(studyApplyRepository.findById(studyApply.getId()).get().getAccpted());
    }

    @Test
    @DisplayName("가입-신청-리스트")
    @WithAccount(NickName = "me") //팀장일때 나가면 스터디 삭제
    public void 가입_신청_리스트() throws Exception {
        //given 1.스터디 만들기 2.스터디장을 me로 등록 3.User1,User2지원
        Account account=accountRepository.findByNickname("me");
        Study study = studySampleGenerator.generateDumy(1).get(0);
        studyRepository.save(study);
        studyMemberRepository.save(studyService.generateStudyMember(study,account, StudyRole.팀장));
        StudyApply studyApply1=studyService.generateStudyApply(study,User1);
        StudyApply studyApply2=studyService.generateStudyApply(study,User2);
        studyApplyRepository.saveAll(Arrays.asList(studyApply1,studyApply2));

        mockMvc.perform(get("/study/{study_id}/apply/list",study.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(studyApplyRepository.findByStudy_Id(study.getId()).size(),2);
    }

    @Test
    @DisplayName("가입-신청-디테일")
    @WithAccount(NickName = "me") //팀장일때 나가면 스터디 삭제
    public void 가입_신청_디테일() throws Exception {
        //given 1.스터디 만들기 2.스터디장을 me로 등록 3.User1,User2지원
        Account account=accountRepository.findByNickname("me");
        Study study = studySampleGenerator.generateDumy(1).get(0);
        studyRepository.save(study);
        studyMemberRepository.save(studyService.generateStudyMember(study,account, StudyRole.팀장));
        StudyApply studyApply1=new StudyApply().builder().study(study)
                            .applicant(User1)
                            .answer("answer")
                            .etc("etc").build();
        studyApplyRepository.save(studyApply1);

        MvcResult result = mockMvc.perform(get("/study/{study_id}/apply/{apply_id}",study.getId().toString(),studyApply1.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String strResult = result.getResponse().getContentAsString();
        StudyApplyForm response = objectMapper.readValue(strResult, StudyApplyForm.class);
        Assertions.assertAll(
                () -> assertEquals(response.getStudy(), studyApply1.getStudy().toString()),
                () -> assertEquals(response.getAnswer(),studyApply1.getAnswer()),
                () -> assertEquals(response.getEtc(),studyApply1.getEtc())
        );
    }


}