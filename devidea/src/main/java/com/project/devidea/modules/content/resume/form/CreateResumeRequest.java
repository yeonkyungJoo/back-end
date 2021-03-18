package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.CreateRequest;
import com.project.devidea.modules.content.resume.Career;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class CreateResumeRequest extends CreateRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phoneNumber;
    private String github;
    private String blog;

    private List<Career> careers;

}
