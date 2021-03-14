package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.tag.Tag;
import com.project.devidea.modules.zone.Zone;
import com.sun.mail.iap.Response;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mentee")
public class MenteeController {

    private final MenteeRepository menteeRepository;

    @GetMapping("/")
    public ResponseEntity getMentees() {

        List<Mentee> mentees = menteeRepository.findAll();

        // 생성자 v1
/*        List<MenteeDto> collect = mentees.stream()
                .map(mentee -> {
                    MenteeDto dto = new MenteeDto();
                    // dto.setName(mentee.getAccount().getName);
                    dto.setDescription(mentee.getDescription());
                    dto.setZones(mentee.getZones());
                    dto.setTags(mentee.getTags());
                    dto.setPublished(mentee.isPublished());
                    dto.setFree(mentee.isFree());
                    return dto;
                })
                .collect(Collectors.toList());*/

        // 생성자 v2
        List<MenteeDto> collect = mentees.stream()
                .map(mentee -> new MenteeDto(mentee))
                .collect(Collectors.toList());

        return new ResponseEntity(collect, HttpStatus.OK);

    }

    // 생성자 v1
/*    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class MenteeDto {

        // private Account account;
        private String name;
        private String description;
        private Set<Zone> zones;
        private Set<Tag> tags;
        private boolean published;
        private boolean free;
    }*/

    // 생성자 v2
    @Data
    class MenteeDto {

        // private Account account;
        private String name;
        private String description;
        private Set<Zone> zones;
        private Set<Tag> tags;
        private boolean published;
        private boolean free;

        public MenteeDto(Mentee mentee) {
            // this.name = mentee.getAccount().getName();
            this.description = mentee.getDescription();
            this.zones = mentee.getZones();
            this.tags = mentee.getTags();
            this.published = mentee.isPublished();
            this.free = mentee.isFree();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMentee(@PathVariable(name = "id") Long id) {

        Mentee mentee = menteeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

        MenteeDto dto = new MenteeDto(mentee);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity newMentee() {
        return null;
    }

    // id가 PathVariable로 필요한가?
    @PostMapping("/{id}/delete")
    public ResponseEntity quitMentee() {
        return null;
    }
}

