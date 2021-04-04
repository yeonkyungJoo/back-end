package com.project.devidea.modules.content.resume.form.activity;

import com.project.devidea.modules.content.resume.form.activity.ActivityRequest;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateActivityRequest extends ActivityRequest {

    @Builder
    public CreateActivityRequest(@NotEmpty String activityName, @NotEmpty String startDate, @NotEmpty String endDate, String description, String link) {
        super(activityName, startDate, endDate, description, link);
    }
}
