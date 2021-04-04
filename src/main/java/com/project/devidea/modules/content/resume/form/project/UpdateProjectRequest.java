package com.project.devidea.modules.content.resume.form.project;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
public class UpdateProjectRequest extends ProjectRequest {

    @Builder
    public UpdateProjectRequest(@NotEmpty String projectName, @NotEmpty String startDate, String endDate, @NotEmpty String shortDescription, Set<String> tags, String description, String url, boolean open) {
        super(projectName, startDate, endDate, shortDescription, tags, description, url, open);
    }
}
