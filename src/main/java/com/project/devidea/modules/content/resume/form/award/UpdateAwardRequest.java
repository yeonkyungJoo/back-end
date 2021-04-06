package com.project.devidea.modules.content.resume.form.award;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UpdateAwardRequest extends AwardRequest {

    @Builder
    public UpdateAwardRequest(@NotEmpty String name, @NotEmpty String date, String link, String description) {
        super(name, date, link, description);
    }
}
