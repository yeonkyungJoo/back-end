package com.project.devidea.modules.content.mentoring;

import com.project.devidea.infra.config.security.CurrentUser;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
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

    @InitBinder("createMenteeRequest")
    public void initCreateMenteeRequestBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createMenteeRequestValidator);
    }

    @InitBinder("updateMenteeRequest")
    public void initUpdateMenteeRequestBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createMenteeRequestValidator);
    }

    /**
     * 멘티 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getMentees() {

        List<MenteeDto> collect = menteeRepository.findAll().stream()
                .map(mentee -> new MenteeDto(mentee))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    /**
     * 멘티 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getMentee(@PathVariable(name = "id") Long menteeId) {

        // TODO - 예외 처리
        Mentee mentee = menteeRepository.findById(menteeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));
        return new ResponseEntity(new MenteeDto(mentee), HttpStatus.OK);
    }

    /**
     * 멘티 등록
     */
    @PostMapping("/")
    public ResponseEntity newMentee(@RequestBody @Valid CreateMenteeRequest createMenteeRequest,
                                    @CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Long menteeId = menteeService.createMentee(account, createMenteeRequest);
        return new ResponseEntity(menteeId, HttpStatus.CREATED);
    }

    /**
     * 멘티 정보 수정
     */
    @PostMapping("/update")
    public ResponseEntity editMentee(@RequestBody @Valid UpdateMenteeRequest updateMenteeRequest,
                                     @CurrentUser Account account) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }

        menteeService.updateMentee(account, updateMenteeRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 멘티 탈퇴
     */
    @PostMapping("/delete")
    public ResponseEntity quitMentee(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        menteeService.deleteMentee(account);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Data
    class MenteeDto {

        private Long id;
        private String name;
        private String description;
        private Set<String> zones;
        private Set<String> tags;
        private boolean open;
        private boolean free;

        public MenteeDto(Mentee mentee) {
            this.id = mentee.getId();
            this.name = mentee.getAccount().getName();
            this.description = mentee.getDescription();
            this.zones = mentee.getZones().stream()
                    .map(zone -> zone.toString()).collect(Collectors.toSet());
            this.tags = mentee.getTags().stream()
                    .map(tag -> tag.toString()).collect(Collectors.toSet());
            this.open = mentee.isOpen();
            this.free = mentee.isFree();
        }
    }
}

