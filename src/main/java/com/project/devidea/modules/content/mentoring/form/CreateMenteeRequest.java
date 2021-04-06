package com.project.devidea.modules.content.mentoring.form;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class CreateMenteeRequest extends MenteeRequest {

    @Builder
    public CreateMenteeRequest(String description, @NotEmpty Set<String> zones, @NotEmpty Set<String> tags, boolean free) {
        super(description, zones, tags, free);
    }
}
