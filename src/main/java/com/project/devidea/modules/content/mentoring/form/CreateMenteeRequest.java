package com.project.devidea.modules.content.mentoring.form;

import com.project.devidea.api.Request;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Builder
public class CreateMenteeRequest extends Request {

    private String description;

    @Builder.Default
    @NotEmpty
    private Set<String> zones = new HashSet<>();

    @Builder.Default
    @NotEmpty
    private Set<String> tags = new HashSet<>();

    private boolean free;

}
