package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mentor {

    @Id @GeneratedValue
    @Column(name = "mentor_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @NotNull
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    @NotNull
    private Resume resume;

    private LocalDateTime publishedDate;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "mentor_zone",
                joinColumns = @JoinColumn(name = "mentor_id"),
                inverseJoinColumns = @JoinColumn(name = "zone_id"))
                // indexes = @Index(name = "zone", columnList = "zone_id"))
    private Set<Zone> zones = new HashSet<>();  // 가능 지역

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "mentor_tag",
                joinColumns = @JoinColumn(name = "mentor_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"))
                // indexes = @Index(name = "tag", columnList = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    private boolean open;

    private boolean free;

    private Integer cost;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public void setZones(Set<Zone> zones) {
        this.zones = zones;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void addZone(Zone zone) {
        this.zones.add(zone);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void setClose() {
        if (!isOpen()) {
            // TODO - 이미 close 상태
        }
        this.open = false;
    }

    public void setOpen() {
        if (isOpen()) {
            // TODO - 이미 open 상태
        }
        this.open = true;
    }

    public void publish() {
        this.publishedDate = LocalDateTime.now();
        setOpen();
    }

    @Builder
    public Mentor(Long id, @NotNull Account account, @NotNull Resume resume, LocalDateTime publishedDate, Set<Zone> zones, Set<Tag> tags, boolean open, boolean free, Integer cost) {
        this.id = id;
        this.account = account;
        this.resume = resume;
        this.publishedDate = publishedDate;
        this.zones = zones;
        this.tags = tags;
        this.open = open;
        this.free = free;
        this.cost = cost;
    }

    public static Mentor createMentor(Account account, Resume resume,
                      Set<Zone> zones, Set<Tag> tags, boolean free, Integer cost) {

        Mentor mentor = new Mentor();
        mentor.setAccount(account);
        mentor.setResume(resume);
        mentor.setZones(zones);
        mentor.setTags(tags);
        mentor.setFree(free);
        mentor.setCost(cost);

        mentor.publish();
        return mentor;
    }
}
