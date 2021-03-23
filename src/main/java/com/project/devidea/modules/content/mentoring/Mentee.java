package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Account account;

    private String description;

    private LocalDateTime publishedDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mentee_zone",
                joinColumns = @JoinColumn(name = "mentee_id"),
                inverseJoinColumns = @JoinColumn(name = "zone_id"))
                // indexes = @Index(name = "zone", columnList = "zone_id"))
    private Set<Zone> zones = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mentee_tag",
                joinColumns = @JoinColumn(name = "mentee_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"))
                // indexes = @Index(name = "tag", columnList = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    private boolean open;

    private boolean free;

    // private Integer cost;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setZones(Set<Zone> zones) {
        this.zones = zones;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public void setClose() {
        this.open = false;
    }

    public void setOpen() {
        this.open = true;
    }

    public void publish() {
        this.publishedDate = LocalDateTime.now();
        setOpen();
    }

    @Builder
    public Mentee(Long id, @NotNull Account account, String description, LocalDateTime publishedDate, Set<Zone> zones, Set<Tag> tags, boolean open, boolean free) {
        this.id = id;
        this.account = account;
        this.description = description;
        this.publishedDate = publishedDate;
        this.zones = zones;
        this.tags = tags;
        this.open = open;
        this.free = free;
    }
}
