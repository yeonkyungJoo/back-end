package com.project.devidea.modules.content.resume;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Resume {

    @Id @GeneratedValue
    @Column(name = "resume_id")
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String github;

    private String blog;

    // 일대다 단방향
    @OneToMany(cascade = CascadeType.ALL)
    private List<Career> careers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Award> awards = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Activity> activites = new ArrayList<>();

}
