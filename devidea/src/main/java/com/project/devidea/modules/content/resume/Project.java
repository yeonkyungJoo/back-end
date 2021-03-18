package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class Project {

    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;
    private String projectName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String shortDescription;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_tag",
                joinColumns = @JoinColumn(name = "project_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    private String description;
    private String url;
    private boolean open;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
