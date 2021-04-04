package com.project.devidea.modules.content.resume.form.activity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UpdateActivityRequest extends ActivityRequest {

    @Builder
    public UpdateActivityRequest(@NotEmpty String activityName, @NotEmpty String startDate, @NotEmpty String endDate, String description, String link) {
        super(activityName, startDate, endDate, description, link);
    }
}
