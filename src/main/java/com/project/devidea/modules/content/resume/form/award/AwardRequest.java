package com.project.devidea.modules.content.resume.form.award;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AwardRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String date;
    private String link;
    private String description;

}
