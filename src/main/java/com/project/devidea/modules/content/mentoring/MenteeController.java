package com.project.devidea.modules.content.mentoring;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.form.CreateMenteeRequest;
import com.project.devidea.modules.content.mentoring.form.UpdateMenteeRequest;
import com.project.devidea.modules.content.mentoring.validator.CreateMenteeRequestValidator;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mentee")
public class MenteeController {

    private final MenteeService menteeService;
    private final MenteeRepository menteeRepository;
    private final CreateMenteeRequestValidator createMenteeRequestValidator;
/*
    @InitBinder("createMenteeRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createMenteeRequestValidator);
    }
*/

    /**
     * 멘티 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getMentees() {

        List<Mentee> mentees = menteeRepository.findAll();

        List<MenteeDto> collect = mentees.stream()
                .map(mentee -> new MenteeDto(mentee))
                .collect(Collectors.toList());

        return new ResponseEntity(collect, HttpStatus.OK);

    }

    /**
     * 멘티 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getMentee(@PathVariable(name = "id") Long menteeId) {

        Mentee mentee = menteeRepository.findById(menteeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

        MenteeDto dto = new MenteeDto(mentee);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    /**
     * 멘티 등록
     */
    @PostMapping("/")
    public ResponseEntity newMentee(@RequestBody @Valid CreateMenteeRequest request, Errors errors,
                                     // @AuthenticationPrincipal Account account)
                                     @AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        // TODO - test : 이미 멘티인 경우
        Mentee findMentee = menteeRepository.findByAccountId(account.getId());
        if(findMentee != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        // TODO - validate
        Mentee mentee = Mentee.builder()
                .description(request.getDescription())
                .zones(request.getZones())
                .tags(request.getTags())
                .free(request.isFree())
                .build();

        Long menteeId = menteeService.createMentee(mentee, account);
        return new ResponseEntity(menteeId, HttpStatus.CREATED);
    }

    /**
     * 멘티 정보 수정
     */
    @PostMapping("/update")
    public ResponseEntity editMentee(@RequestBody @Valid UpdateMenteeRequest request, Errors errors,
                                     // @AuthenticationPrincipal Account account)
                                     @AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        Mentee mentee = menteeRepository.findByAccountId(account.getId());
        if(mentee == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        // TODO - validate
        menteeService.updateMentee(request, mentee);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 멘티 탈퇴
     */
    @PostMapping("/delete")
    public ResponseEntity quitMentee(
            // @AuthenticationPrincipal Account account)
            @AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        Mentee mentee = menteeRepository.findByAccountId(account.getId());
        if (mentee == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        menteeService.deleteMentee(mentee);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Data
    class MenteeDto {

        private String name;
        private String description;
        private Set<Zone> zones;
        private Set<Tag> tags;
        private boolean open;
        private boolean free;

        public MenteeDto(Mentee mentee) {
            this.name = mentee.getAccount().getName();
            this.description = mentee.getDescription();
            this.zones = mentee.getZones();
            this.tags = mentee.getTags();
            this.open = mentee.isOpen();
            this.free = mentee.isFree();
        }
    }
}

