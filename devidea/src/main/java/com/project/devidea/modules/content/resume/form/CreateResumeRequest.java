package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.content.resume.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreateResumeRequest extends Request {

    @NotEmpty
    private String phoneNumber;
    private String github;
    private String blog;

    private List<Career> careers;
    private List<Project> project;
    private List<Education> educations;
    private List<Award> awards;
    private List<Activity> activities;

}
