package com.project.devidea.modules.account;

import com.project.devidea.modules.account.form.SignUpDetailRequestDto;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.*;
import org.apache.tomcat.util.buf.StringUtils;
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
public class Account implements UserDetails {
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

    @Column(nullable = false, unique = true)
    private String nickname;

    private String emailCheckToken;

    private String roles;

    private LocalDateTime joinedAt;

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
    @OneToMany(mappedBy = "account")
    private Set<Interest> interests = new HashSet<>();

//    주요 활동지역, 일대다 다대일로 풂
    @Builder.Default
    @OneToMany(mappedBy = "account")
    private Set<MainActivityZone> mainActivityZones = new HashSet<>();

//    private Set<Resume> resume;

//    private Set<Study> studies;

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Arrays.asList(roles.split(", ")).forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public String getNickName() { return this.nickname; }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString(){
        return nickname;
    }

//    편의 메서드 : 회원가입 디테일 여기서부터 시작하기!
    public void saveSignUpDetail(SignUpDetailRequestDto req, Set<MainActivityZone> mainActivityZones, Set<Interest> interests) {

        this.profileImage = req.getProfileImage();
        this.receiveEmail = req.isReceiveEmail();
        this.careerYears = req.getCareerYears();
        this.job = req.getJobField();
        this.techStacks = StringUtils.join(req.getTechStacks(), '/');

        this.interests.addAll(interests);
        this.mainActivityZones.addAll(mainActivityZones);
    }
}

