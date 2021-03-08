package com.project.devidea.modules.content.study;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.Content;
import com.project.devidea.modules.tag.Tag;
import com.project.devidea.modules.zone.Zone;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Study extends Content {
    @ManyToMany
    private Set<Account> members = new HashSet<>();
    String title;
    String shortDescription;
    String fullDescription;
    @ManyToMany
    Set<Tag> tags;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "locationid")
    Zone location;

    boolean recruiting;

    boolean open;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    Account admin;

    int maxCount;

    Level level;

    Boolean mentoRecruiting;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "mento_id")
    Account mento;

    String question;
    //StudyBoard studyBoard
}
