package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateEducationRequest extends Request {

    @NotEmpty
    private String schoolName;
    @NotEmpty
    private String major;
    @NotNull
    private LocalDateTime entranceDate;
    @NotNull
    private LocalDateTime graduationDate;
    private double score;
    private String degree;
}
