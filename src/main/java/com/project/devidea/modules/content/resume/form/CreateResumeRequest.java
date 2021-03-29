package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateResumeRequest extends Request {

    @NotEmpty
    private String phoneNumber;
    private String github;
    private String blog;

    @Builder
    public CreateResumeRequest(@NotEmpty String phoneNumber, String github, String blog) {
        this.phoneNumber = phoneNumber;
        this.github = github;
        this.blog = blog;
    }
}
