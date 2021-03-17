package com.project.devidea.modules.content.study;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.Content;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.core.metrics.StartupStep;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = @Index(name = "location", columnList = "location_id"))
@EqualsAndHashCode(of = "id")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Study implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    @OneToMany(mappedBy = "study", cascade ={CascadeType.ALL,CascadeType.REMOVE,CascadeType.REFRESH}) //여기 스터디가 삭제되면 studymember에도 영향끼침
    private Set<StudyMember> members = new HashSet<StudyMember>();
    private String title;

    private String shortDescription;

    private String fullDescription;

    @ManyToMany
    @JoinTable(name = "study_tag",
            joinColumns = @JoinColumn(name = "study_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            indexes = @Index(name = "tag", columnList = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Zone location;
    private boolean recruiting; //모집중인스터디
    private LocalDateTime publishedDateTime;
    private boolean open; //공개여부

    private int Likes = 0;

    private int counts = 0;
    private int maxCount;

    @Enumerated(EnumType.STRING)
    private Level level;

    private Boolean mentoRecruiting;

    private String question;


    public Boolean ContainsKeyword(String Keyword) {
        for (Tag tag : tags) if (tag.contains(Keyword)) return true;
        return false;
    }

    public void addMember(StudyMember member) {
        if(members==null) members=new HashSet<>();
      members.add(member);
      counts++;
    }
    public boolean addMember(Account member,Study_Role role) {
        if(counts==maxCount) return false;
        StudyMember study=generateMember(member,role);
        member.addStudy(study);
        members.add(study);
        return true;
    }
    public void removeMember(Account account) {
        for (Iterator<StudyMember> iterator = members.iterator();
             iterator.hasNext(); ) {
            account.getStudies().remove(this);
            members.remove(account);
            StudyMember studyMember = iterator.next();

            if (studyMember.getStudy().equals(this) &&
                    studyMember.getMember().equals(account)) {
                iterator.remove();
            }
        }
    }

    public void setAdmin(Account account) {
        if(this.members==null) members=new HashSet<>();
        if(account==null) return;
        members.add(generateMember(account, Study_Role.팀장));

    }

    public void ChangeRole(Account member, Study_Role role) {
        for (StudyMember member1 : members) {
            if (member1.equals(member)) {
                member1.setRole(role);
                break;
            }
        }
    }

    public List<Account> getMember() {
        return members.stream().map(
                member -> {
                    return member.getMember();
                }
        ).collect(Collectors.toList());
    }

    public List<Account> getManager() {
        return members.stream().filter(
                member -> !member.getRole().equals(Study_Role.회원) &&
                        !member.getRole().equals(Study_Role.멘토)
        ).map(member -> {
            return member.getMember();
        }).collect(Collectors.toList());
    }

    public void setOpenAndRecruiting(boolean open, boolean recruiting) {
        this.open = open;
        this.recruiting = recruiting;
    }

    public StudyMember generateMember(Account member, Study_Role role) {
        return new StudyMember().builder()
                .role(role)
                .member(member)
                .study(this)
                .JoinDate(LocalDateTime.now())
                .build();
    }

    @Override
    public String toString() {
        return this.title;
    }
}