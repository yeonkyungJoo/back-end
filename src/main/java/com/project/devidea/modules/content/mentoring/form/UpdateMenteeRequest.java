package com.project.devidea.modules.content.mentoring.form;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Builder
public class UpdateMenteeRequest extends MenteeRequest {

    private boolean open = true;
}
