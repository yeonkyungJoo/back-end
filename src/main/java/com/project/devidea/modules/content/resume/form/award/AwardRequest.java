package com.project.devidea.modules.content.resume.form.award;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@Builder
public class AwardRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String date;
    private String link;
    private String description;

}
