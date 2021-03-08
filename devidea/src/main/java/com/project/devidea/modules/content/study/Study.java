package com.project.devidea.modules.content.study;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.Content;
import com.project.devidea.modules.tag.Tag;
import com.project.devidea.modules.zone.Zone;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Study extends Content {
    //    @Id
//    @GeneratedValue
//    Long id;
    @ManyToMany
    private Set<Account> members = new HashSet<>();
    private String title;
    private String shortDescription;
    private String fullDescription;
    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Zone location;

    private boolean recruiting;
    private LocalDate PublishedDateTime;
    private boolean open;
    private int Likes = 0;
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "admin_id")
//    Account admin;

    private int maxCount;

    private Level level;

    private Boolean mentoRecruiting;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "mento_id")
//    Account mento;

    private String question;
    //StudyBoard studyBoard
}
