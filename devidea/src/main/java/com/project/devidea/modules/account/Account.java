package com.project.devidea.modules.account;

import com.project.devidea.modules.content.study.Study;
import lombok.*;
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
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    private Set<Study> studies;

//    private List<Apply> applies;

//    private List<Career> careers;

    private String provider;

//    private Set<Like> likes;

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
}

