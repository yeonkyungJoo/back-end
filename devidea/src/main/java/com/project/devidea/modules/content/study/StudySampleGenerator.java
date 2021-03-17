package com.project.devidea.modules.content.study;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class StudySampleGenerator {
    private static Account admin;
    private final TagRepository tagRepository;
    private final ZoneRepository zoneRepository;
    public void setAdmin(Account admin){
        this.admin=admin;
    }
    public Study generate(String title, Set<Tag> tags,Zone zone, Boolean isRecruiting) {
        List<Zone> zones = zoneRepository.findAll();
        Study study = new Study().builder()
                .Likes(0)
                .title(title)
                .shortDescription("스프링 스터디입니다")
                .location(zone)
                .fullDescription("fullDescription")
                .shortDescription("fullDescription")
                .recruiting(isRecruiting)
                .publishedDateTime(LocalDateTime.now())
                .open(true)
                .maxCount(6)
                .tags(tags)
                .build();
        study.setAdmin(admin);
        return study;
    }
    public List<Study> generateDumy(int number){
        List<Study> studyList=new ArrayList<>();
        Random rand=new Random();
        List<Tag> tagList=tagRepository.findAll();
        List<Zone> zoneList=zoneRepository.findAll();
        String[] titles={"스프링 스터디","자바 스터디","하둡 스터디","인공지능 스터디"};
        for(int cur=0;cur<number;cur++){
            Set<Tag> tagSet=new HashSet<>();
            while(tagSet.size()<3){
                int randomIndex = rand.nextInt(tagList.size());
                tagSet.add(tagList.get(randomIndex));
            }
            studyList.add(generate(titles[rand.nextInt(4)]+cur
                    ,tagSet,zoneList.get(rand.nextInt(zoneList.size())),
                    rand.nextBoolean()));
        }
        return studyList;
    }

}
