package com.project.devidea.modules.account;

import com.project.devidea.modules.account.dto.AccountProfileUpdateRequestDto;
import com.project.devidea.modules.account.dto.SignUpDetailRequestDto;
import com.project.devidea.modules.content.study.StudyMember;
import lombok.*;
import org.apache.tomcat.util.buf.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String nickname;

    private String emailCheckToken;

    private String roles;

    private LocalDateTime joinedAt;

    private LocalDateTime modifiedAt;

    private String bio;

    private String profileImage;

    private String url;

    private String gender;

    //    현재 직업
    private String job;

    //    현재 분야 경력
    private int careerYears;

    //    테크 스택, 문자열로 구분자 지어주기
    private String techStacks;

    //    관심분야, 일대다 다대일로 풂
    @Builder.Default
    @OneToMany(mappedBy = "account", cascade = {CascadeType.ALL})
    private Set<Interest> interests = new HashSet<>();

    //    주요 활동지역, 일대다 다대일로 풂
    @Builder.Default
    @OneToMany(mappedBy = "account", cascade = {CascadeType.ALL})
    private Set<MainActivityZone> mainActivityZones = new HashSet<>();

//    private Set<Resume> resume;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}) //여기 어카운트가 삭제되면 studymember에도 영향끼침
    private Set<StudyMember> studies = new HashSet<>();

    public void addStudy(StudyMember studyMember) {
        if (studies == null) studies = new HashSet<>();
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
    public String toString() {
        return getNickname();
    }

    @Builder
    public Account(Long id, String email, String password, String name, String nickname, String emailCheckToken, String roles, LocalDateTime joinedAt, LocalDateTime modifiedAt, String bio, String profileImage, String url, String gender, String job, int careerYears, String techStacks, Set<Interest> interests, Set<MainActivityZone> mainActivityZones, Set<StudyMember> studies, String provider, boolean receiveEmail, boolean receiveNotification, boolean receiveTechNewsNotification, boolean receiveMentoringNotification, boolean receiveStudyNotification, boolean receiveRecruitingNotification) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.emailCheckToken = emailCheckToken;
        this.roles = roles;
        this.joinedAt = joinedAt;
        this.modifiedAt = modifiedAt;
        this.bio = bio;
        this.profileImage = profileImage;
        this.url = url;
        this.gender = gender;
        this.job = job;
        this.careerYears = careerYears;
        this.techStacks = techStacks;
        this.interests = interests;
        this.mainActivityZones = mainActivityZones;
        this.studies = studies;
        this.provider = provider;
        this.receiveEmail = receiveEmail;
        this.receiveNotification = receiveNotification;
        this.receiveTechNewsNotification = receiveTechNewsNotification;
        this.receiveMentoringNotification = receiveMentoringNotification;
        this.receiveStudyNotification = receiveStudyNotification;
        this.receiveRecruitingNotification = receiveRecruitingNotification;
    }

    //    편의 메서드
    public void saveSignUpDetail(SignUpDetailRequestDto req, Set<MainActivityZone> mainActivityZones, Set<Interest> interests) {

        this.nickname = req.getNickname();
        this.profileImage = req.getProfileImage();
        this.receiveEmail = req.isReceiveEmail();
        this.careerYears = req.getCareerYears();
        this.job = req.getJobField();
        this.techStacks = StringUtils.join(req.getTechStacks(), '/');

        this.interests.addAll(interests);
        this.mainActivityZones.addAll(mainActivityZones);
    }

    public void updateProfile(AccountProfileUpdateRequestDto accountProfileUpdateRequestDto) {
        this.bio = accountProfileUpdateRequestDto.getBio();
        this.profileImage = accountProfileUpdateRequestDto.getProfileImage();
        this.url = accountProfileUpdateRequestDto.getUrl();
        this.gender = accountProfileUpdateRequestDto.getGender();
        this.job = accountProfileUpdateRequestDto.getJob();
        this.careerYears = accountProfileUpdateRequestDto.getCareerYears();
        this.techStacks = StringUtils.join(accountProfileUpdateRequestDto.getTechStacks(), '/');
        this.modifiedAt = LocalDateTime.now();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateInterests(Set<Interest> interests) {
        this.interests.clear();
        this.interests.addAll(interests);
    }

    public void updateMainActivityZones(Set<MainActivityZone> mainActivityZones) {
        this.mainActivityZones.clear();
        this.mainActivityZones.addAll(mainActivityZones);
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}

