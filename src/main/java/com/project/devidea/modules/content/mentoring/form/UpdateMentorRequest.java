package com.project.devidea.modules.content.mentoring.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateMentorRequest extends Request {

    private Set<Zone> zones;
    private Set<Tag> tags;
    private boolean free;
    private Integer cost;
    private boolean open;

    @Builder
    public UpdateMentorRequest(Set<Zone> zones, Set<Tag> tags, boolean free, Integer cost, boolean open) {
        this.zones = zones;
        this.tags = tags;
        this.free = free;
        this.cost = cost;
        this.open = open;
    }
}