package com.project.devidea.modules.content.study;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.Content;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = @Index(name = "location", columnList = "location_id"))
public class Study implements Serializable  {
    @GeneratedValue
    @Id
    Long id;
    @ManyToMany
    private Set<Account> members = new HashSet<>();
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
    private int Likes=0;

    //    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    Account admin;
    //
    private int counts=0;
    private int maxCount;
    private Level level;

    private Boolean mentoRecruiting;
        //
    ////    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    ////    @JoinColumn(name = "mento_id")
    ////    Account mento;
    //
    private String question;


    private Boolean ContainsKeyword(String Keyword) {
        for (Tag tag : tags) if (tag.contains(Keyword)) return true;
        return false;
    }

    private Boolean addMember(Account member) {
        if (members.size() == maxCount) return false;
        members.add(member);
        counts++;
        return true;
    }
}
