package com.project.devidea.modules.content.resume.form;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class CreateResumeRequest extends ResumeRequest {

    @Builder
    public CreateResumeRequest(@NotEmpty String phoneNumber, String github, String blog) {
        super(phoneNumber, github, blog);
    }
}
