package com.project.devidea.modules.content.study;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.Content;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
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
@Table(indexes = @Index(name = "location", columnList = "location_id"))
@EqualsAndHashCode(of = "id")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Study implements Serializable {
    @Id
    @SequenceGenerator(name = "SequenceGenerator", sequenceName = "mySeq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceGenerator")
    @Column(name="study_id")
    private Long id;
    @OneToMany(mappedBy = "study", cascade =CascadeType.ALL) //여기 스터디가 삭제되면 studymember에도 영향끼침
    private Set<StudyMember> members = new HashSet<>();
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

    private int likes = 0;

    private int counts = 0;
    private int maxCount;

    @Enumerated(EnumType.STRING)
    private Level level;

    private Boolean mentoRecruiting;

    private String question;


    public void setOpenAndRecruiting(boolean open, boolean recruiting) {
        this.open = open;
        this.recruiting = recruiting;
    }


    @Override
    public String toString() {
        return this.title;
    }

    @Builder
    public Study(Long id, Set<StudyMember> members, String title, String shortDescription, String fullDescription, Set<Tag> tags, Zone location, boolean recruiting, LocalDateTime publishedDateTime, boolean open, int likes, int counts, int maxCount, Level level, Boolean mentoRecruiting, String question) {
        this.id = id;
        this.members = members;
        this.title = title;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.tags = tags;
        this.location = location;
        this.recruiting = recruiting;
        this.publishedDateTime = publishedDateTime;
        this.open = open;
        this.likes = likes;
        this.counts = counts;
        this.maxCount = maxCount;
        this.level = level;
        this.mentoRecruiting = mentoRecruiting;
        this.question = question;
    }
    @Transient
    public static Study generateStudyById(Long id){
        return new Study().builder()
                .id(id).build();
    }
}
