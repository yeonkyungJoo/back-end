package com.project.devidea.modules.account;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudyMember;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String emailCheckToken;

    private String roles;

    private LocalDateTime joinedAt;

    private String bio;

    private String profileImage;

    private String url;

    private String gender;

//    private Set<Tag> tags;

//    private Set<Resume> resume;

//    private Set<Zone> locations;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}) //여기 어카운트가 삭제되면 studymember에도 영향끼침
    private Set<StudyMember> studies = new HashSet<>();

    public void addStudy(StudyMember studyMember){
        if(studies==null ) studies = new HashSet<>();
        studies.add(studyMember);
    }
//    private List<Apply> applies;

//    private List<Career> careers;

    private String provider;

//    private Set<Like> likes;

//    이메일 수신 동의
    private boolean receiveEmail;

    private boolean receiveNotification;

    private boolean receiveTechNewsNotification;

    private boolean receiveMentoringNotification;

    private boolean receiveStudyNotification;

    private boolean receiveRecruitingNotification;

    @Override
    public String toString(){
        return getNickname();
    }
}

