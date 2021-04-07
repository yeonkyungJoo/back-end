package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.CurrentUser;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.form.CreateResumeRequest;
import com.project.devidea.modules.content.resume.form.UpdateResumeRequest;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;

    @ApiOperation("이력서 조회")
    @GetMapping("/")
    public ResponseEntity getResume(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }
        return new ResponseEntity(new ResumeDto(resume), HttpStatus.OK);
    }

    @ApiOperation("이력서 등록")
    @PostMapping("/")
    public ResponseEntity newResume(@RequestBody @Valid CreateResumeRequest request,
                                    @CurrentUser Account account) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Long resumeId = resumeService.createResume(account, request);
        return new ResponseEntity(resumeId, HttpStatus.CREATED);
    }

    @ApiOperation("이력서 수정")
    @PostMapping("/edit")
    public ResponseEntity editResume(@RequestBody @Valid UpdateResumeRequest request,
                                     @CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        resumeService.updateResume(account, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("이력서 삭제")
    @PostMapping("/delete")
    public ResponseEntity deleteResume(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }

        resumeService.deleteResume(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    class ResumeDto {

        private Long id;
        private String name;
        private String email;
        private String phoneNumber;
        private String github;
        private String blog;

        public ResumeDto(Resume resume) {
            this.id = resume.getId();
            this.name = resume.getAccount().getName();
            this.email = resume.getAccount().getEmail();
            this.phoneNumber = resume.getPhoneNumber();
            this.github = resume.getGithub();
            this.blog = resume.getBlog();
        }
    }

}
