package com.project.devidea.modules.content.resume.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeRequest {

    @NotEmpty
    private String phoneNumber;
    private String github;
    private String blog;
}
