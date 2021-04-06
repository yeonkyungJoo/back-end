package com.project.devidea.modules.content.mentoring;

import com.project.devidea.infra.config.security.CurrentUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.mentoring.form.CreateMentorRequest;
import com.project.devidea.modules.content.mentoring.form.UpdateMentorRequest;
import com.project.devidea.modules.content.mentoring.validator.MentorRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.WebDataBinder;
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
    private final MentorService mentorService;

    // TODO - 페이징
    /**
     * 멘토 전체 조회 - 페이징
     */
    @GetMapping("/list")
    public ResponseEntity getMentors(Pageable pageable) {
        return ResponseEntity.ok(null);
    }

    @ApiOperation("멘토 전체 조회")
    @GetMapping("/")
    public ResponseEntity getMentors() {

        List<MentorDto> collect = mentorRepository.findAll().stream()
                .map(mentor -> new MentorDto(mentor))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    @ApiOperation("멘토 조회")
    @GetMapping("/{id}")
    public ResponseEntity getMentor(@PathVariable(name = "id") Long mentorId) {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 멘토입니다."));
        return new ResponseEntity(new MentorDto(mentor), HttpStatus.OK);
    }

    @ApiOperation("멘토 등록")
    @PostMapping("/")
    public ResponseEntity newMentor(@RequestBody @Valid CreateMentorRequest request,
                                    @CurrentUser Account account) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Long mentorId = mentorService.createMentor(account, request);
        return new ResponseEntity(mentorId, HttpStatus.CREATED);
    }

    @ApiOperation("멘토 정보 수정")
    @PostMapping("/update")
    public ResponseEntity editMentor(@RequestBody @Valid UpdateMentorRequest request,
                                     @CurrentUser Account account) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        mentorService.updateMentor(account, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("멘토 탈퇴")
    @PostMapping("/delete")
    public ResponseEntity quitMentor(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        mentorService.deleteMentor(account);
        return new ResponseEntity(HttpStatus.OK);
    }



    @Data
    class MentorDto {

        private Long id;
        private String name;
        private Set<String> zones;
        private Set<String> tags;
        private boolean open;
        private boolean free;
        private Integer cost;

        public MentorDto(Mentor mentor) {
            this.id = mentor.getId();
            this.name = mentor.getAccount().getName();
            this.zones = mentor.getZones().stream()
                    .map(zone -> zone.toString()).collect(Collectors.toSet());
            this.tags = mentor.getTags().stream()
                    .map(tag -> tag.toString()).collect(Collectors.toSet());
            this.open = mentor.isOpen();
            this.free = mentor.isFree();
            this.cost = mentor.getCost();
        }
    }


}
