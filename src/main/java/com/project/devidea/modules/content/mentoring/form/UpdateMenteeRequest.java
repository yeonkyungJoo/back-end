package com.project.devidea.modules.content.mentoring.form;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class UpdateMenteeRequest extends MenteeRequest {

    private boolean open;

    @Builder
    public UpdateMenteeRequest(String description, @NotEmpty Set<String> zones, @NotEmpty Set<String> tags, boolean free, boolean open) {
        super(description, zones, tags, free);
        this.open = open;
    }
}
