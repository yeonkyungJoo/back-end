package com.project.devidea.modules.content.resume.form.career;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CareerRequest {

    @NotEmpty
    private String companyName;
    @NotEmpty
    private String duty;
    @NotEmpty
    private String startDate;
    private String endDate;
    private boolean present;

    private Set<String> tags;
    private String detail;
    private String url;

}
