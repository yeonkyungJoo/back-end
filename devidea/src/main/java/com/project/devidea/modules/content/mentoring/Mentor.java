package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.tag.Tag;
import com.project.devidea.modules.zone.Zone;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Mentor {

    @Id @GeneratedValue
    @Column(name = "mentor_id")
    private Long id;

    // 일대일 단방향
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private LocalDateTime publishedDate;

    @ManyToMany
    private Set<Zone> zones = new HashSet<>();  // @Basic -> @ManyToMany

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    private boolean publised;

    private boolean free;

    private Integer cost;

}
