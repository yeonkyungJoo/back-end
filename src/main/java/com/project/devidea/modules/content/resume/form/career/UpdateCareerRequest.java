package com.project.devidea.modules.content.resume.form.career;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter @Setter
public class UpdateCareerRequest extends CareerRequest {

    @Builder
    public UpdateCareerRequest(@NotEmpty String companyName, @NotEmpty String duty, @NotEmpty String startDate, String endDate, boolean present, Set<String> tags, String detail, String url) {
        super(companyName, duty, startDate, endDate, present, tags, detail, url);
    }
}
