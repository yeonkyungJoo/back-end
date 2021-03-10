package com.project.devidea.modules.content.study;

import com.google.common.collect.Sets;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import com.project.devidea.modules.tag.Tag;
import com.project.devidea.modules.tag.TagRepository;
import com.project.devidea.modules.zone.Zone;
import com.project.devidea.modules.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class StudySampleGenerator {
    private final StudyRepository studyRepository;
    private final TagRepository tagRepository;
    private final ZoneRepository zoneRepository;

    public Study generate(String title, Set<Tag> tags, Boolean isRecruiting) {
        List<Zone> zones = zoneRepository.findAll();
        Study study = new Study().toBuilder()
                .Likes(0)
                .title(title)
                .shortDescription("스프링 스터디입니다")
                .location(zones.get(0))
                .recruiting(isRecruiting)
                .publishedDateTime(LocalDateTime.now())
                .open(true)
                .maxCount(3)
                .tags(tags)
                .build();
        return study;
    }
    public List<Study> generateDumy(int number){
        List<Study> studyList=new ArrayList<>();
        Random rand=new Random();
        List<Tag> tagList=tagRepository.findAll();
        String[] titles={"스프링 스터디","자바 스터디","하둡 스터디","인공지능 스터디"};
        for(int cur=0;cur<number;cur++){
            Set<Tag> tagSet=new HashSet<>();
            while(tagSet.size()<3){
                int randomIndex = rand.nextInt(tagList.size());
                tagSet.add(tagList.get(randomIndex));
            }
            studyList.add(generate(titles[rand.nextInt(4)]+cur,tagSet,rand.nextBoolean()));
        }
        return studyList;
    }

}
