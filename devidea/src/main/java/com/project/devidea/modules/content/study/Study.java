package com.project.devidea.modules.content.study;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.Content;
import com.project.devidea.modules.tag.Tag;
import com.project.devidea.modules.zone.Zone;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Study extends Content {

    @ManyToMany
    @Builder.Default
    private Set<Account> members = new HashSet<>();
    private String title;
    private String shortDescription;
    private String fullDescription;
    @ManyToMany
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Zone location;

    private boolean recruiting; //모집하고 있는
    private LocalDateTime publishedDateTime;
    private boolean open; //공개여부
    @Builder.Default
    private int Likes = 0;

    //    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    Account admin;
    //
    @Builder.Default
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
