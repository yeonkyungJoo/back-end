package com.project.devidea.modules.content.mentoring.form;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
public class MentorRequest {

    @NotEmpty
    private Set<String> zones;
    @NotEmpty
    private Set<String> tags;
    private boolean free;
    private Integer cost;

}
