package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UpdateProjectRequest extends Request {

    @NotEmpty
    private String projectName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotEmpty
    private String shortDescription;
    private Set<Tag> tags;
    private String description;
    private String url;
    private boolean open;

    @Builder
    public UpdateProjectRequest(@NotEmpty String projectName, @NotNull LocalDate startDate, @NotNull LocalDate endDate, @NotEmpty String shortDescription, Set<Tag> tags, String description, String url, boolean open) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shortDescription = shortDescription;
        this.tags = tags;
        this.description = description;
        this.url = url;
        this.open = open;
    }
}
