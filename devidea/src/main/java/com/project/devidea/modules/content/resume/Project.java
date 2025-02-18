package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public static Project createProject(String projectName, LocalDateTime startDate,
                    LocalDateTime endDate, String shortDescription, Set<Tag> tags, String description, String url, boolean open) {

        // TODO - validate
        // - startDate, endDate 비교

        Project project = Project.builder()
                .projectName(projectName)
                .startDate(startDate)
                .endDate(endDate)
                .shortDescription(shortDescription)
                .tags(tags)
                .description(description)
                .url(url)
                .open(open)
                .build();
        return project;
    }

}
