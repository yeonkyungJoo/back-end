package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class CreateProjectRequest extends Request {

    @NotEmpty
    private String projectName;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    @NotEmpty
    private String shortDescription;
    private Set<Tag> tags;
    private String description;
    private String url;
    private boolean open;
}
