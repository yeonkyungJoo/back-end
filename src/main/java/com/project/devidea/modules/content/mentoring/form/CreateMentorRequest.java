package com.project.devidea.modules.content.mentoring.form;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class CreateMentorRequest extends MentorRequest {

    @Builder
    public CreateMentorRequest(@NotEmpty Set<String> zones, @NotEmpty Set<String> tags, boolean free, Integer cost) {
        super(zones, tags, free, cost);
    }
}
