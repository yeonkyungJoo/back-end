package com.project.devidea.modules.content.mentoring.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.*;

import java.util.Set;

@Getter
@Setter
public class UpdateMenteeRequest extends Request {

    private String description;
    private Set<Zone> zones;
    private Set<Tag> tags;

    private boolean open;
    private boolean free;

    @Builder
    public UpdateMenteeRequest(String description, Set<Zone> zones, Set<Tag> tags, boolean open, boolean free) {
        this.description = description;
        this.zones = zones;
        this.tags = tags;
        this.open = open;
        this.free = free;
    }
}
