//package com.project.devidea.modules.content.study;
//
//import com.project.devidea.infra.MockMvcTest;
//import com.project.devidea.infra.config.AppConfig;
//import com.project.devidea.modules.account.Account;
//import com.project.devidea.modules.account.AccountService;
//import com.project.devidea.modules.account.form.SignUpResponseDto;
//import com.project.devidea.modules.account.repository.AccountRepository;
//import com.project.devidea.modules.content.study.apply.StudyApplyRepository;
//import com.project.devidea.modules.content.study.form.StudyDetailForm;
//import com.project.devidea.modules.content.study.form.StudyMakingForm;
//import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
//import com.project.devidea.modules.content.study.repository.StudyRepository;
//import com.project.devidea.modules.tagzone.tag.Tag;
//import com.project.devidea.modules.tagzone.tag.TagRepository;
//import com.project.devidea.modules.tagzone.zone.Zone;
//import com.project.devidea.modules.tagzone.zone.ZoneRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.NameTokenizers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.awt.*;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest(classes = {AppConfig.class, StudyService.class},
//        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
//@Slf4j
//public class StudyServiceTest {
//    @SpyBean
//    ModelMapper studyMapper;
//    @MockBean
//    ZoneRepository zoneRepository;
//    @MockBean
//    TagRepository tagRepository;
//
//    @MockBean
//    StudyRepository studyRepository;
//    @MockBean
//    AccountRepository accountRepository;
//    @MockBean
//    StudyApplyRepository studyApplyRepository;
//    @MockBean
//    StudyMemberRepository studyMemberRepository;
//
//
//    Study study;
//    Account account1, account2;
//    StudyMember studyMember1, studyMember2;
//
//    @Autowired
//    StudyService studyService;
//
//    @BeforeEach
//    void init() {
//        Zone 서울송파구 = new Zone().builder().id(1L)
//                .city("서울")
//                .province("송파구")
//                .build();
//        Zone 서울강남구 = new Zone().builder().id(2L)
//                .city("서울")
//                .province("강남구")
//                .build();
//        Tag spring = new Tag().builder()
//                .id(1L)
//                .firstName("java")
//                .build();
//        Tag java = new Tag().builder()
//                .id(2L)
//                .firstName("spring")
//                .build();
//
//        account1 = Account.builder()
//                .id(1L)
//                .email("account1@email.com")
//                .password("password")
//                .name("account1")
//                .nickname("account1")
//                .roles("ROLE_USER")
//                .joinedAt(LocalDateTime.now())
//                .gender("남성")
//                .build();
//        account2 = Account.builder()
//                .id(1L)
//                .email("account2@email.com")
//                .password("password")
//                .name("account2")
//                .nickname("account2")
//                .roles("ROLE_USER")
//                .joinedAt(LocalDateTime.now())
//                .gender("남성")
//                .build();
//        studyMember1 = new StudyMember().builder()
//                .id(1L)
//                .study(study)
//                .member(account1)
//                .build();
//        studyMember2 = new StudyMember().builder()
//                .id(2L)
//                .study(study)
//                .member(account2)
//                .build();
//
//        when(zoneRepository.findAll())
//                .thenReturn(Arrays.asList(
//                        서울송파구, 서울강남구));
//
//        when(zoneRepository.findByCityAndProvince("서울", "송파구"))
//                .thenReturn(서울송파구);
//        when(tagRepository.findAll())
//                .thenReturn(Arrays.asList(
//                        spring, java));
//        when(accountRepository.findAll())
//                .thenReturn(Arrays.asList(
//                        account1, account2));
//        when(accountRepository.findByNickname("account1"))
//                .thenReturn(account1);
//
//        when(studyMemberRepository.findAll())
//                .thenReturn(Arrays.asList(studyMember1, studyMember2));
//
//        study = new Study().builder()
//                .id(1L)
//                .likes(0)
//                .title("title")
//                .shortDescription("shortDescription")
//                .location(서울송파구)
//                .members(new HashSet<StudyMember>(studyMemberRepository.findAll()))
//                .fullDescription("fullDescription")
//                .shortDescription("shortDescription")
//                .recruiting(true)
//                .publishedDateTime(LocalDateTime.now())
//                .open(true)
//                .maxCount(6)
//                .tags(new HashSet<Tag>(tagRepository.findAll()))
//                .build();
//
//    }
//
//    @Test
//    @DisplayName("스터디 상세 폼보기")
//    void getDetailStudy() {
//        //given
//        when(studyRepository.findById(study.getId()))
//                .thenReturn(java.util.Optional.ofNullable(study));
//        //when
//        StudyDetailForm studyDetailForm = studyService.getDetailStudy((study.getId()));
//        assertAll(
//                () -> assertNotNull(studyDetailForm),
//                () -> assertEquals(studyDetailForm.getId(), study.getId()),
//                () -> assertEquals(studyDetailForm.getMembers(), study.getMembers().stream()
//                        .map(studyMember -> {
//                            return studyMember.toString();
//                        }).collect(Collectors.toSet())),
//                () -> assertEquals(studyDetailForm.getTitle(), study.getTitle()),
//                () -> assertEquals(studyDetailForm.getTags().toString(), study.getTags().toString()),
//                () -> assertEquals(studyDetailForm.getLocation(), study.getLocation().toString()),
//                () -> assertEquals(studyDetailForm.getCounts(), study.getCounts())
//        );
//    }
//
////    @Test
////    @DisplayName("스터디 만들기")
////    void makingStudy() {
////        //given
////        StudyMakingForm studyMakingForm = new StudyMakingForm();
////        studyMakingForm.setOpen(true);
////        studyMakingForm.setQuestion("question");
////        studyMakingForm.setLocation("서울/송파구");
////        studyMakingForm.setLevel(Level.입문);
////        studyMakingForm.setMaxCount(6);
////        studyMakingForm.setTitle("TITLE");
////        studyMakingForm.setRecruiting(true);
////        studyMakingForm.setTags(new HashSet<String>(tagRepository.findAll().stream()
////                .map(tag -> {
////                    return tag.toString();
////                }).collect(Collectors.toSet())));
////        //when
////        StudyDetailForm studyDetailForm = studyService.makingStudy(account1.getNickname(), studyMakingForm);
////        assertAll(
////                () -> assertNotNull(studyDetailForm),
////                () -> assertEquals(studyDetailForm.getId(), study.getId()),
////                () -> assertEquals(studyDetailForm.getMembers(), study.getMembers().stream()
////                        .map(studyMember -> {
////                            return studyMember.toString();
////                        }).collect(Collectors.toSet())),
////                () -> assertEquals(studyDetailForm.getTitle(), study.getTitle()),
////                () -> assertEquals(studyDetailForm.getTags().toString(), study.getTags().toString()),
////                () -> assertEquals(studyDetailForm.getLocation(), study.getLocation().toString()),
////                () -> assertEquals(studyDetailForm.getCounts(), study.getCounts())
////        );
////    }
//
//
//    @Test
//    void applyStudy() {
//    }
//
//    @Test
//    void decideJoin() {
//    }
//
//    @Test
//    void getApplyForm() {
//    }
//
//    @Test
//    void deleteStudy() {
//    }
//
//    @Test
//    void leaveStudy() {
//    }
//
//    @Test
//    void myStudy() {
//    }
//
//    @Test
//    void getApplyList() {
//    }
//
//    @Test
//    void getApplyDetail() {
//    }
//
//    @Test
//    void getOpenRecruitForm() {
//    }
//
//    @Test
//    void getTagandZone() {
//    }
//
//    @Test
//    void updateOpenRecruiting() {
//    }
//
//    @Test
//    void updateTagAndZOne() {
//    }
////
////    public static class StudyDomainSample{
////
////
////    }
//}
