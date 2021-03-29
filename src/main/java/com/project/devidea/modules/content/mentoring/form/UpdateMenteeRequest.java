package com.project.devidea.modules.content.mentoring.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Builder
public class UpdateMenteeRequest extends Request {

    private String description;
    @Builder.Default
    @NotEmpty
    private Set<String> zones = new HashSet<>();
    @Builder.Default
    @NotEmpty
    private Set<String> tags = new HashSet<>();
    private boolean open;
    private boolean free;

}
