package com.project.devidea.modules.content.resume.form.education;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateEducationRequest extends EducationRequest {

    @Builder
    public CreateEducationRequest(@NotEmpty String schoolName, @NotEmpty String major, @NotEmpty String entranceDate, String graduationDate, double score, String degree) {
        super(schoolName, major, entranceDate, graduationDate, score, degree);
    }
}
