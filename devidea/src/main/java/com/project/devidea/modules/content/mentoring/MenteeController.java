package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.content.mentoring.validator.CreateMenteeRequestValidator;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.sun.mail.iap.Response;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mentee")
public class MenteeController {

    private final MenteeRepository menteeRepository;
    private final CreateMenteeRequestValidator createMenteeRequestValidator;

    @InitBinder("createMenteeRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createMenteeRequestValidator);
    }

    @GetMapping("/")
    public ResponseEntity getMentees() {

        List<Mentee> mentees = menteeRepository.findAll();

        List<MenteeDto> collect = mentees.stream()
                .map(mentee -> new MenteeDto(mentee))
                .collect(Collectors.toList());

        return new ResponseEntity(collect, HttpStatus.OK);

    }

    @Data
    class MenteeDto {

        // private Account account;
        private String name;
        private String description;
        private Set<Zone> zones;
        private Set<Tag> tags;
        private boolean open;
        private boolean free;

        public MenteeDto(Mentee mentee) {
            // this.name = mentee.getAccount().getName();
            this.description = mentee.getDescription();
            this.zones = mentee.getZones();
            this.tags = mentee.getTags();
            this.open = mentee.isOpen();
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

