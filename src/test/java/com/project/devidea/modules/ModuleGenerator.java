package com.project.devidea.modules;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudyMember;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;

import java.time.LocalDateTime;
import java.util.*;

public class ModuleGenerator {
    public static Account generateAccount(String name,Long id){
            return Account.builder()
                    .id(id)
                    .email(name+"@email.com")
                    .password("password")
                    .name("account2")
                    .nickname("account2")
                    .roles("ROLE_USER")
                    .joinedAt(LocalDateTime.now())
                    .gender("남성")
                    .build();
        }
    public static Zone generateZone(String city,String province,Long id){
        return new Zone().builder().id(id)
                .city(city)
                .province(province)
                .build();
    }
    public static Tag generateTag(String firstName,Long id){
        return new Tag().builder()
                .id(id)
                .firstName(firstName)
                .build();
    }
    public static Study generateStudy (String title,List<StudyMember> studies,List<Tag> tags,Zone zone) {
        return new Study().builder()
                .id(1L)
                .likes(0)
                .title(title)
                .shortDescription(title+" "+"shortDescription")
                .members(new HashSet<StudyMember>(studies))
                .location(zone)
                .fullDescription(title+" "+"fullDescription")
                .shortDescription(title+" "+"shortDescription")
                .recruiting(true)
                .publishedDateTime(LocalDateTime.now())
                .open(true)
                .maxCount(6)
                .tags(new HashSet<Tag>(tags))
                .build();
    }
    public static StudyMember generateStudyMember(Study study,Account account,Long id){
        return new StudyMember().builder()
                .id(id)
                .study(study)
                .member(account)
                .build();
    }
}
