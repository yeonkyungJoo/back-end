package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public abstract class AbstractService {

    protected final TagRepository tagRepository;
    protected final ZoneRepository zoneRepository;

    protected AbstractService(TagRepository tagRepository, ZoneRepository zoneRepository) {
        this.tagRepository = tagRepository;
        this.zoneRepository = zoneRepository;
    }

    protected Set<Tag> getTags(Set<String> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findByFirstName(tag)).collect(Collectors.toSet());
    }

    public Set<Zone> getZones(Set<String> zones) {
        return zones.stream().map(zone -> {
            String[] locations = zone.split("/");
            return zoneRepository.findByCityAndProvince(locations[0], locations[1]);
        }).collect(Collectors.toSet());
    }
}
