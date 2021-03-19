//package com.project.devidea.modules.content.study;
//
//import com.project.devidea.modules.account.Account;
//import com.project.devidea.modules.account.AccountRepository;
//import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
//import com.project.devidea.modules.content.study.repository.StudyRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//import javax.persistence.EntityManager;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Transactional
//public class StudyDomainTest {
//    @Autowired
//    AccountRepository accountRepository;
//    @Autowired
//    EntityManager entityManager;
//    @Autowired
//    StudyRepository studyRepository;
//
//    @Autowired
//    StudyMemberRepository studyMemberRepository;
//    @Autowired
//    StudySampleGenerator studySampleGenerator;
//    Study study;
//    Account 근우, 범석, 세로;
//
//    @BeforeEach
//    @DisplayName("초기 데이터셋팅")
//    void init() {
//        근우 = Account.builder()
//                .name("근우")
//                .nickname("ginious")
//                .email("geunwoo@naver.com")
//                .password("1234")
//                .build();
//        범석 = Account.builder()
//                .name("범석")
//                .nickname("suk")
//                .email("ko@naver.com")
//                .password("1234")
//                .build();
//        세로 = Account.builder()
//                .name("세로")
//                .nickname("column")
//                .email("sero@naver.com")
//                .password("1234")
//                .build();
//        study = studySampleGenerator.generateDumy(1).get(0);
//        accountRepository.save(근우);
//        accountRepository.save(세로);
//        accountRepository.save(범석);
//        study.addMember(근우, Study_Role.팀장);
//        study.addMember(세로, Study_Role.회원);
//        study.addMember(범석, Study_Role.회원);
//        studyRepository.save(study);
//    }
//
//}
