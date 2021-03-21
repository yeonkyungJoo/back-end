package com.project.devidea.modules.content.mentoring.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateMentorRequest extends Request {

    private Set<Zone> zones;
    private Set<Tag> tags;
    private boolean free;
    private Integer cost;

    private boolean open;
}