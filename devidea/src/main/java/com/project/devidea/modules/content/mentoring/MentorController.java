package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.form.CreateMentorRequest;
import com.project.devidea.modules.content.mentoring.validator.CreateMentorRequestValidator;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mentor")
public class MentorController {

    private final MentorRepository mentorRepository;
    private final CreateMentorRequestValidator createMentorRequestValidator;

    @InitBinder("createMentorRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createMentorRequestValidator);
    }

    // 페이징
    @GetMapping("/")
    public ResponseEntity getMentors(Pageable pageable) {

        return ResponseEntity.ok(null);
    }

/*
    @GetMapping("/")
    public ResponseEntity getMentors() {

        List<Mentor> mentors = mentorRepository.findAll();
        List<MentorDto> collect = mentors.stream()
                .map(mentor -> new MentorDto(mentor))
                .collect(Collectors.toList());

        return new ResponseEntity(collect, HttpStatus.OK);
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity getMentor(@PathVariable(name = "id") Long id) {

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

        MentorDto dto = new MentorDto(mentor);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/")
    public ResponseEntity newMentor(@RequestBody @Valid CreateMentorRequest request, Errors errors,
                                    @AuthenticationPrincipal Account account) {
        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Mentor mentor = new Mentor();
        mentor.setAccount(account);


        return new ResponseEntity(mentor.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity updateMentor(@PathVariable(name = "id") Long id,
                                       @RequestBody @Valid UpdateMentorRequest request, Errors errors) {


        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/delete")
    public ResponseEntity quitMentor() {
        return null;
    }



    @Data
    class MentorDto {

        private String name;
        private Set<Zone> zones;
        private Set<Tag> tags;
        private boolean open;
        private boolean free;
        private Integer cost;

        public MentorDto(Mentor mentor) {
            this.name = mentor.getAccount().getName();
            this.zones = mentor.getZones();
            this.tags = mentor.getTags();
            this.open = mentor.isOpen();
            this.free = mentor.isFree();
            this.cost = mentor.getCost();
        }
    }


}
