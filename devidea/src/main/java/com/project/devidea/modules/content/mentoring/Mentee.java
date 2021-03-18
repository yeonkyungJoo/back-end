package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Mentee {

    @Id
    @GeneratedValue
    @Column(name = "mentee_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private String description;

    private LocalDateTime publishedDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mentee_zone",
                joinColumns = @JoinColumn(name = "mentee_id"),
                inverseJoinColumns = @JoinColumn(name = "zone_id"),
                indexes = @Index(name = "zone", columnList = "zone_id"))
    private Set<Zone> zones = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mentee_tag",
                joinColumns = @JoinColumn(name = "mentee_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"),
                indexes = @Index(name = "tag", columnList = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    private boolean open;

    private boolean free;

    // private Integer cost;
}
