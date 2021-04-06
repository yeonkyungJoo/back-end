package com.project.devidea.modules.content.mentoring.form;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenteeRequest {

    private String description;
    @NotEmpty
    private Set<String> zones;
    @NotEmpty
    private Set<String> tags;
    private boolean free;

}
