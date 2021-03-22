package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.resume.form.CreateResumeRequest;
import com.project.devidea.modules.content.resume.form.UpdateResumeRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * 이력서 조회
     */
    @GetMapping("/")
    public ResponseEntity getResume(
            // @AuthenticationPrincipal Account account)
            @AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        Resume resume = resumeRepository.findByAccountId(account.getId());
        ResumeDto dto = new ResumeDto(resume);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    /**
     * 이력서 등록
     */
    @PostMapping("/")
    public ResponseEntity newResume(@RequestBody @Valid CreateResumeRequest request, Errors errors,
                                    // @AuthenticationPrincipal Account account)
                                    @AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // TODO - validate
        Resume resume = Resume.builder()
                .phoneNumber(request.getPhoneNumber())
                .github(request.getGithub())
                .blog(request.getBlog())
                .careers(request.getCareers())
                .projects(request.getProject())
                .educations(request.getEducations())
                .awards(request.getAwards())
                .activites(request.getActivities())
                .build();

        Long resumeId = resumeService.createResume(resume, account);
        return new ResponseEntity(resumeId, HttpStatus.CREATED);
    }

    /**
     * 이력서 수정
     */
    @PostMapping("/edit")
    public ResponseEntity editResume(@RequestBody @Valid UpdateResumeRequest request, Errors errors,
                                     // @AuthenticationPrincipal Account account)
                                     @AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // TODO - validate
        resumeService.updateResume(request, resume);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 이력서 삭제
     */
    @PostMapping("/delete")
    public ResponseEntity deleteResume(
            // @AuthenticationPrincipal Account account)
            @AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        resumeService.deleteResume(resume);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    class ResumeDto {

        private String name;
        private String email;
        private String phoneNumber;
        private String github;
        private String blog;
        private List<Career> careers;
        private List<Project> projects;
        private List<Education> educations;
        private List<Award> awards;
        private List<Activity> activities;

        public ResumeDto(Resume resume) {

            this.name = resume.getAccount().getName();
            this.email = resume.getAccount().getEmail();
            this.phoneNumber = resume.getPhoneNumber();
            this.github = resume.getGithub();
            this.blog = resume.getBlog();

            this.careers = resume.getCareers();
            this.projects = resume.getProjects();
            this.educations = resume.getEducations();
            this.awards = resume.getAwards();
            this.activities = resume.getActivites();
        }
    }

}
