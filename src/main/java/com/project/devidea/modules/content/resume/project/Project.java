package com.project.devidea.modules.content.resume.project;

import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String shortDescription;
    @Builder.Default
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

}
