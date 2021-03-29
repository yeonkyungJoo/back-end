package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class CreateActivityRequest extends Request {

    @NotEmpty
    private String activityName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private String description;
    private String link;

    @Builder
    public CreateActivityRequest(@NotEmpty String activityName, @NotNull LocalDate startDate, @NotNull LocalDate endDate, String description, String link) {
        this.activityName = activityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.link = link;
    }
}
