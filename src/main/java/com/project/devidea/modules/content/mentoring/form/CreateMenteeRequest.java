package com.project.devidea.modules.content.mentoring.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.*;

import java.util.Set;

@Getter @Setter
public class CreateMenteeRequest extends Request {

    private String description;
    private Set<Zone> zones;
    private Set<Tag> tags;
    private boolean free;

    @Builder
    public CreateMenteeRequest(String description, Set<Zone> zones, Set<Tag> tags, boolean free) {
        this.description = description;
        this.zones = zones;
        this.tags = tags;
        this.free = free;
    }
}
