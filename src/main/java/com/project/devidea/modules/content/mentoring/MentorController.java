package com.project.devidea.modules.content.mentoring;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.form.CreateMentorRequest;
import com.project.devidea.modules.content.mentoring.form.UpdateMentorRequest;
import com.project.devidea.modules.content.mentoring.validator.CreateMentorRequestValidator;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mentor")
public class MentorController {

    private final MentorRepository mentorRepository;
    private final MentorService mentorService;
    private final CreateMentorRequestValidator createMentorRequestValidator;
    private final ResumeRepository resumeRepository;
/*
    @InitBinder("createMentorRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createMentorRequestValidator);
    }
*/

    /**
     * 멘토 전체 조회 - 페이징
     */
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

    /**
     * 멘토 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getMentor(@PathVariable(name = "id") Long mentorId) {

        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

        MentorDto dto = new MentorDto(mentor);
        return ResponseEntity.ok(dto);
    }

    /**
     * 멘토 등록
     */
    @PostMapping("/")
    public ResponseEntity newMentor(@RequestBody @Valid CreateMentorRequest request, Errors errors,
                                    // @AuthenticationPrincipal Account account)
                                    @AuthenticationPrincipal LoginUser loginUser) {

        Account account = loginUser.getAccount();
        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        // TODO - validate
        // - free와 cost 확인

        // TODO - test : 이미 멘토인 경우
        Mentor findMentor = mentorRepository.findByAccountId(account.getId());
        if (findMentor != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new IllegalStateException("Not Exist Resume");
        }

        Mentor mentor = Mentor.builder()
                .account(account)
                .resume(resume)
                .zones(request.getZones())
                .tags(request.getTags())
                .free(request.isFree())
                .cost(request.getCost())
                .build();

        // TODO - validate
        Long mentorId = mentorService.createMentor(mentor);
        return new ResponseEntity(mentorId, HttpStatus.CREATED);
    }

    /**
     * 멘토 정보 수정
     */
    @PostMapping("/update")
    public ResponseEntity editMentor(@RequestBody @Valid UpdateMentorRequest request, Errors errors,
                                     // @AuthenticationPrincipal Account account)
                                     @AuthenticationPrincipal LoginUser loginUser) {

        Account account = loginUser.getAccount();
        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Mentor mentor = mentorRepository.findByAccountId(account.getId());
        if (mentor == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        // TODO - validate
        mentorService.updateMentor(request, mentor);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 멘토 탈퇴
     */
    @PostMapping("/delete")
    public ResponseEntity quitMentor(
            // @AuthenticationPrincipal Account account)
            @AuthenticationPrincipal LoginUser loginUser) {

        Account account = loginUser.getAccount();
        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Mentor mentor = mentorRepository.findByAccountId(account.getId());
        if (mentor == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        // TODO - DB에서 정보를 아예 삭제해버리면 이력관리는? -> is_del 컬럼을 추가하는 게 어떠한가?
        mentorService.deleteMentor(mentor);
        return new ResponseEntity(HttpStatus.OK);
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
