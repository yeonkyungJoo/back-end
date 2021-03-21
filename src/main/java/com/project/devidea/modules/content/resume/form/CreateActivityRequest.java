package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateActivityRequest extends Request {

    @NotEmpty
    private String activityName;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    private String description;
    private String link;
}
