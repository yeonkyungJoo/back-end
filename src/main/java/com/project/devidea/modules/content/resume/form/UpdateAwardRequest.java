package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
public class UpdateAwardRequest extends Request {

    @NotEmpty
    private String name;
    @NotNull
    private LocalDate date;
    private String link;
    private String description;

    @Builder
    public UpdateAwardRequest(@NotEmpty String name, @NotNull LocalDate date, String link, String description) {
        this.name = name;
        this.date = date;
        this.link = link;
        this.description = description;
    }
}
