package com.project.devidea.modules.content.resume.form.education;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationRequest {

    @NotEmpty
    private String schoolName;
    @NotEmpty
    private String major;
    @NotEmpty
    private String entranceDate;
    private String graduationDate;
    private double score;
    private String degree;

}
