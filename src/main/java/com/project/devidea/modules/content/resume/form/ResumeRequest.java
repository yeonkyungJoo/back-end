package com.project.devidea.modules.content.resume.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class ResumeRequest {

    @NotEmpty
    private String phoneNumber;
    private String github;
    private String blog;
}
