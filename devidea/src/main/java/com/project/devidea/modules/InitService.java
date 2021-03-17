package com.project.devidea.modules;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountRepository;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudySampleGenerator;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import com.project.devidea.modules.tagzone.tag.TagService;
import com.project.devidea.modules.tagzone.zone.ZoneService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
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
    private final StudySampleGenerator studySampleGenerator;

    @PostConstruct
    @Transactional
    void Setting(){
        Account account=new Account().builder()
                .nickname("DevIdea")
                .email("devidea@devidea.com")
                .emailCheckToken("abcdefghijklmn")
                .bio("bio")
                .gender("남성")
                .name("devidea")
                .password("password")
                .joinedAt(LocalDateTime.now())
                .build();
        studyRepository.saveAll(studySampleGenerator.generateDumy(30));
        accountRepository.save(account);
        studyRepository.findAll().stream().forEach(study -> {
            study.setAdmin(account);
        });
    }
}
