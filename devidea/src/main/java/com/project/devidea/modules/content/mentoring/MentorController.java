package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mentor")
public class MentorController {

    private final MentorRepository mentorRepository;

    @GetMapping("/")
    public ResponseEntity getMentors() {

        List<Mentor> mentors = mentorRepository.findAll();

        // 생성자 v1
/*        List<MentorDto> collect = mentors.stream()
                .map(mentor -> {
                    MentorDto dto = new MentorDto();
                    // dto.setName(mentor.getAccount().getName);
                    dto.setZones(mentor.getZones());
                    dto.setTags(mentor.getTags());
                    dto.setPublished(mentor.isPublised());
                    dto.setFree(mentor.isFree());
                    dto.setCost(mentor.getCost());

                    return dto;
                }).collect(Collectors.toList());*/

        // 생성자 v2
        List<MentorDto> collect = mentors.stream()
                .map(mentor -> new MentorDto(mentor)).
                        collect(Collectors.toList());

        return new ResponseEntity(collect, HttpStatus.OK);
    }

    // 생성자 v1
/*    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class MentorDto {

        // private Account account;
        private String name;
        private Set<Zone> zones;
        private Set<Tag> tags;
        private boolean published;
        private boolean free;
        private Integer cost;
    }*/

    // 생성자 v2
    @Data
    class MentorDto {

        // private Account account;
        private String name;
        private Set<Zone> zones;
        private Set<Tag> tags;
        private boolean published;
        private boolean free;
        private Integer cost;

        public MentorDto(Mentor mentor) {
            // this.name = mentor.getAccount().getName();
            this.zones = mentor.getZones();
            this.tags = mentor.getTags();
            this.published = mentor.isPublised();
            this.free = mentor.isFree();
            this.cost = mentor.getCost();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMentor(@PathVariable(name = "id") Long id) {

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

        MentorDto dto = new MentorDto(mentor);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity newMentor(@RequestBody @Valid CreateMentorRequest request, Errors errors,
                                        @AuthenticationPrincipal User user) {

        // TODO - validate
        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (user == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        // AccountAdapter 필요
        // 혹은 아예 AccountAdapter로 반환해도 된다.
        // @AuthenticationPrincipal AccountAdapter currentUser


        Mentor newMentor = new Mentor();

        return new ResponseEntity(newMentor.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity updateMentor(@PathVariable(name = "id") Long id,
                                       @RequestBody @Valid UpdateMentorRequest request, Errors errors) {


        return new ResponseEntity(HttpStatus.OK);
    }


    // id가 PathVariable로 필요한가?
    @DeleteMapping("/{id}/delete")
    public ResponseEntity quitMentor() {
        return null;
    }



}
