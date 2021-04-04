package com.project.devidea.modules.content.resume.form.activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class ActivityRequest {

    @NotEmpty
    private String activityName;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String endDate;
    private String description;
    private String link;

}
