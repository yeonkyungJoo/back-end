package com.project.devidea.modules;

import com.project.devidea.infra.config.security.SHA256;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.mentoring.Mentee;
import com.project.devidea.modules.content.mentoring.MenteeRepository;
import com.project.devidea.modules.content.mentoring.Mentor;
import com.project.devidea.modules.content.mentoring.MentorRepository;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.study.StudyRole;
import com.project.devidea.modules.content.study.StudySampleGenerator;
import com.project.devidea.modules.content.study.StudyService;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@DependsOn(value={"TagService", "ZoneService"})
public class InitService {
    private final AccountRepository accountRepository;
    private final StudyRepository studyRepository;
    private final StudyService studyService;
    private final StudySampleGenerator studySampleGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TagRepository tagRepository;
    private final ZoneRepository zoneRepository;
    private final MenteeRepository menteeRepository;
    private final ResumeRepository resumeRepository;
    private final MentorRepository mentorRepository;

    private Set<Tag> getTags(Set<String> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findByFirstName(tag)).collect(Collectors.toSet());
    }

    private Set<Zone> getZones(Set<String> zones) {
        return zones.stream().map(zone -> {
            String[] locations = zone.split("/");
            return zoneRepository.findByCityAndProvince(locations[0], locations[1]);
        }).collect(Collectors.toSet());
    }


    @PostConstruct
    @Transactional
    void Setting(){

        if(accountRepository.count()!=0) return;

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
                .modifiedAt(LocalDateTime.now())
                .interests(new HashSet<>())
                .mainActivityZones(new HashSet<>())
                .build();

        Account account3=new Account().builder()
                .nickname("테스트_회원2")
                .email("test2@test.com")
                .emailCheckToken("abcdefghijklmn")
                .bio("bio")
                .gender("남성")
                .roles("ROLE_USER")
                .name("테스트_회원")
                .password("{bcrypt}" + bCryptPasswordEncoder.encode("1234"))
                .joinedAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .interests(new HashSet<>())
                .mainActivityZones(new HashSet<>())
                .build();

        Account quitAccount = Account.builder()
                .nickname("탈퇴회원")
                .email("quit@quit.com")
                .emailCheckToken("abcdefghijklmn")
                .bio("bio")
                .gender("남성")
                .roles("ROLE_USER")
                .name("탈퇴회원")
                .password("{bcrypt}" + bCryptPasswordEncoder.encode(SHA256.encrypt("1234")))
                .joinedAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .interests(new HashSet<>())
                .mainActivityZones(new HashSet<>())
                .quit(true)
                .build();

        studyRepository.saveAll(studySampleGenerator.generateDumy(30));
        accountRepository.saveAll(Arrays.asList(account,account2,account3, quitAccount));
        studyRepository.findAll().stream().forEach(study -> {
            studyService.addMember(account,study, StudyRole.팀장);
            studyService.addMember(account2,study, StudyRole.회원);
        });

        Set<String> tagSet1 = new HashSet<String>(Arrays.asList("java", "python", "scala"));
        Set<String> zoneSet1 = new HashSet<String>(Arrays.asList("경기도/수정구", "경기도/광주시"));

        // Mentee
        Mentee mentee1 = Mentee.createMentee(account,
                "멘티1 설명",
                getZones(zoneSet1),
                getTags(tagSet1),
                true);
        menteeRepository.save(mentee1);

        Set<String> tagSet2 = new HashSet<String>(Arrays.asList("react", "AngularJS", "express", "flask"));
        Set<String> zoneSet2 = new HashSet<String>(Arrays.asList("서울특별시/강서구", "서울특별시/금천구", "서울특별시/구로구"));
        Mentee mentee2 = Mentee.createMentee(account2,
                "멘티2 설명",
                getZones(zoneSet2),
                getTags(tagSet2),
                true);
        menteeRepository.save(mentee2);

        // Mentor
        Set<String> tagSet3 = new HashSet<String>(Arrays.asList("algorithm", "kubernetes", "docker", "devops"));
        Set<String> zoneSet3 = new HashSet<String>(Arrays.asList("부산광역시/사하구", "부산광역시/북구", "부산광역시/금정구"));

        // - Resume
        Resume resume3 = Resume.createResume(account3,
                "01012345678",
                "test2@github.com",
                "test2@blog.com");
        resumeRepository.save(resume3);

        Mentor mentor = Mentor.createMentor(account3,
                resume3,
                getZones(zoneSet3),
                getTags(tagSet3),
                false,
                10000);
        mentorRepository.save(mentor);


        Account account4=new Account().builder()
                .nickname("테스트_회원4")
                .email("test4@test.com")
                .emailCheckToken("abcdefghijklmn")
                .bio("bio")
                .gender("남성")
                .roles("ROLE_USER")
                .name("테스트_회원")
                .password("{bcrypt}" + bCryptPasswordEncoder.encode("1234"))
                .joinedAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .interests(new HashSet<>())
                .mainActivityZones(new HashSet<>())
                .build();

        Account account5=new Account().builder()
                .nickname("테스트_회원5")
                .email("test5@test.com")
                .emailCheckToken("abcdefghijklmn")
                .bio("bio")
                .gender("남성")
                .roles("ROLE_USER")
                .name("테스트_회원")
                .password("{bcrypt}" + bCryptPasswordEncoder.encode("1234"))
                .joinedAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .interests(new HashSet<>())
                .mainActivityZones(new HashSet<>())
                .build();

        Account account6=new Account().builder()
                .nickname("테스트_회원6")
                .email("test6@test.com")
                .emailCheckToken("abcdefghijklmn")
                .bio("bio")
                .gender("남성")
                .roles("ROLE_USER")
                .name("테스트_회원")
                .password("{bcrypt}" + bCryptPasswordEncoder.encode("1234"))
                .joinedAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .interests(new HashSet<>())
                .mainActivityZones(new HashSet<>())
                .build();

        accountRepository.saveAll(Arrays.asList(account4,account5,account6));
    }

}