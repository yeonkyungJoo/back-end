package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Resume {

    @Id @GeneratedValue
    @Column(name = "resume_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @NotNull
    private Account account;
    // private String name;
    // private String email;

    @NotEmpty
    private String phoneNumber;

    private String github;

    private String blog;

    @Builder.Default
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Career> careers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Education> educations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Award> awards = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Activity> activites = new ArrayList<>();

    public void setAccount(Account account) {
        this.account = account;
        // account.setResume(this);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }

    public void setActivites(List<Activity> activites) {
        this.activites = activites;
    }

    public void addCareer(Career career) {
        this.careers.add(career);
        career.setResume(this);
    }

    public void addProject(Project project) {
        this.projects.add(project);
        project.setResume(this);
    }

    public void addEducation(Education education) {
        this.educations.add(education);
        education.setResume(this);
    }

    public void addAward(Award award) {
        this.awards.add(award);
        award.setResume(this);
    }

    public void addActivity(Activity activity) {
        this.activites.add(activity);
        activity.setResume(this);
    }

}
