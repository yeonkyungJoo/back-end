package com.project.devidea.modules;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountRepository;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudySampleGenerator;
import com.project.devidea.modules.content.study.StudyService;
import com.project.devidea.modules.content.study.Study_Role;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import com.project.devidea.modules.tagzone.tag.TagService;
import com.project.devidea.modules.tagzone.zone.ZoneService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@DependsOn(value={"TagService", "ZoneService"})
public class InitService {
    private final EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final StudyRepository studyRepository;
    private final StudyService studyService;
    private final StudySampleGenerator studySampleGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @PostConstruct
    @Transactional
    void Setting(){
        Account account=new Account().builder()
                .nickname("DevIdea")
                .email("devidea@devidea.com")
                .emailCheckToken("abcdefghijklmn")
                .bio("bio")
                .gender("남성")
                .roles("ROLE_USER")
                .name("devidea")
                .password("{bcrypt}" + bCryptPasswordEncoder.encode("1234"))
                .joinedAt(LocalDateTime.now())
                .build();
        Account account2=new Account().builder()
                .nickname("테스트_회원")
                .email("test@test.com")
                .emailCheckToken("abcdefghijklmn")
                .bio("bio")
                .gender("남성")
                .roles("ROLE_USER")
                .name("테스트_회원")
                .password("{bcrypt}" + bCryptPasswordEncoder.encode("1234"))
                .joinedAt(LocalDateTime.now())
                .build();
        studyRepository.saveAll(studySampleGenerator.generateDumy(30));
        accountRepository.save(account);
        accountRepository.save(account2);
        studyRepository.findAll().stream().forEach(study -> {
            studyService.addMember(account,study, Study_Role.팀장);
            studyService.addMember(account2,study, Study_Role.회원);
        });
    }
}
