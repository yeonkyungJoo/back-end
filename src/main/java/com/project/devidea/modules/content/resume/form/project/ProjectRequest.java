package com.project.devidea.modules.content.resume.form.project;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
public class ProjectRequest {

    @NotEmpty
    private String projectName;
    @NotEmpty
    private String startDate;
    private String endDate;
    @NotEmpty
    private String shortDescription;

    @Builder.Default
    private Set<String> tags = new HashSet<>();
    private String description;
    private String url;
    private boolean open;

}
