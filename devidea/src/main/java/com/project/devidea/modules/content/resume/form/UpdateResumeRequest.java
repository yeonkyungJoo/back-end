package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.content.resume.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResumeRequest extends Request {

    private String phoneNumber;
    private String github;
    private String blog;

    private List<Career> careers;
    private List<Project> project;
    private List<Education> educations;
    private List<Award> awards;
    private List<Activity> activities;
}
