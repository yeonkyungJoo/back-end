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
public class CreateMentorRequest extends Request {

    private Set<Zone> zones;
    private Set<Tag> tags;
    private boolean free;
    private Integer cost;

    @Builder
    public CreateMentorRequest(Set<Zone> zones, Set<Tag> tags, boolean free, Integer cost) {
        this.zones = zones;
        this.tags = tags;
        this.free = free;
        this.cost = cost;
    }
}
