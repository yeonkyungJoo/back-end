package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.content.resume.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class UpdateResumeRequest extends Request {

    private String phoneNumber;
    private String github;
    private String blog;

    private List<Career> careers;
    private List<Project> project;
    private List<Education> educations;
    private List<Award> awards;
    private List<Activity> activities;

    @Builder
    public UpdateResumeRequest(String phoneNumber, String github, String blog, List<Career> careers, List<Project> project, List<Education> educations, List<Award> awards, List<Activity> activities) {
        this.phoneNumber = phoneNumber;
        this.github = github;
        this.blog = blog;
        this.careers = careers;
        this.project = project;
        this.educations = educations;
        this.awards = awards;
        this.activities = activities;
    }
}
