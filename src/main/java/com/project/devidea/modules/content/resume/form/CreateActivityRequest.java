package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateActivityRequest extends Request {

    @NotEmpty
    private String activityName;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    private String description;
    private String link;

    @Builder
    public CreateActivityRequest(@NotEmpty String activityName, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate, String description, String link) {
        this.activityName = activityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.link = link;
    }
}
