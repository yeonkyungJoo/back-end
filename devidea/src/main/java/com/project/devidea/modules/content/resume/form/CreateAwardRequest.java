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
public class CreateAwardRequest extends CreateRequest {

    @NotEmpty
    private String name;
    @NotNull
    private LocalDateTime date;
    private String link;
    private String description;
}
