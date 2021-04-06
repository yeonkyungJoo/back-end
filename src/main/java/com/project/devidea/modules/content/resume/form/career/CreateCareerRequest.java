package com.project.devidea.modules.content.resume.form.career;

import com.project.devidea.modules.content.resume.form.career.CareerRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
public class CreateCareerRequest extends CareerRequest {

    @Builder
    public CreateCareerRequest(@NotEmpty String companyName, @NotEmpty String duty, @NotEmpty String startDate, String endDate, boolean present, Set<String> tags, String detail, String url) {
        super(companyName, duty, startDate, endDate, present, tags, detail, url);
    }
}
