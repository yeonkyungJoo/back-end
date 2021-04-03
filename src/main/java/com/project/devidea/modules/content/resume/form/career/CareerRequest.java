package com.project.devidea.modules.content.resume.form.career;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
public class CareerRequest {

    @NotEmpty
    private String companyName;
    @NotEmpty
    private String duty;
    @NotEmpty
    private String startDate;
    private String endDate;
    private boolean present;

    @Builder.Default
    private Set<String> tags = new HashSet<>();
    private String detail;
    private String url;

}
