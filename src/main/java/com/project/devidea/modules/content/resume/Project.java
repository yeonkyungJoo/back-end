package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
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

    public static Project createProject(Resume resume, String projectName, LocalDate startDate,
            LocalDate endDate, String shortDescription, Set<Tag> tags, String description, String url, boolean open) {

        // TODO - validate
        // - startDate, endDate 비교

        Project project = new Project();
        project.setResume(resume);
        project.setProjectName(projectName);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setShortDescription(shortDescription);
        project.setTags(tags);
        project.setDescription(description);
        project.setUrl(url);
        project.setOpen(open);

        resume.addProject(project);
        return project;
    }

    @Builder
    public Project(Long id, String projectName, LocalDate startDate, LocalDate endDate,
           String shortDescription, Set<Tag> tags, String description, String url, boolean open, Resume resume) {
        this.id = id;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shortDescription = shortDescription;
        this.tags = tags;
        this.description = description;
        this.url = url;
        this.open = open;
        this.resume = resume;
    }
}
