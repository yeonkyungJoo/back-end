package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.CreateRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class CreateEducationRequest extends CreateRequest {

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
