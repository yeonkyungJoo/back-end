package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateAwardRequest extends Request {

    @NotEmpty
    private String name;
    @NotNull
    private LocalDateTime date;
    private String link;
    private String description;

    @Builder
    public CreateAwardRequest(@NotEmpty String name, @NotNull LocalDateTime date, String link, String description) {
        this.name = name;
        this.date = date;
        this.link = link;
        this.description = description;
    }
}
