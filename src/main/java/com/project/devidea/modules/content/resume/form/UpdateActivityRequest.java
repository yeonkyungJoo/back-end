package com.project.devidea.modules.content.resume.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
public class UpdateActivityRequest {

    @NotEmpty
    private String activityName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private String description;
    private String link;

    @Builder
    public UpdateActivityRequest(@NotEmpty String activityName, @NotNull LocalDate startDate, @NotNull LocalDate endDate, String description, String link) {
        this.activityName = activityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.link = link;
    }
}
