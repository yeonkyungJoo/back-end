package com.project.devidea.modules.content.resume.form.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    @NotEmpty
    private String projectName;
    @NotEmpty
    private String startDate;
    private String endDate;
    @NotEmpty
    private String shortDescription;

    private Set<String> tags;
    private String description;
    private String url;
    private boolean open;

}
