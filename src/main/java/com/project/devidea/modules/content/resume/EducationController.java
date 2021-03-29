package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.resume.form.CreateEducationRequest;
import com.project.devidea.modules.content.resume.form.UpdateEducationRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resume/education")
@RequiredArgsConstructor
public class EducationController {

    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final EducationService educationService;

    /**
     * Education 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getEducations(@AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        // TODO : (비교) account.getResume();
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        List<Education> educations = resume.getEducations();
        List<EducationDto> collect = educations.stream()
                .map(education -> new EducationDto(education))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    /**
     * Education 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getEducation(@AuthenticationPrincipal LoginUser loginUser,
                                       @PathVariable("id") Long educationId) {
        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid educationId"));

        return new ResponseEntity(new EducationDto(education), HttpStatus.OK);
    }

    /**
     * Education 등록
     */
    @PostMapping("/")
    public ResponseEntity newEducation(@RequestBody @Valid CreateEducationRequest request, Errors errors,
                                       @AuthenticationPrincipal LoginUser loginUser) {
        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        // TODO : account - resume 연관관계 설정
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Education education = Education.createEducation(resume, request.getSchoolName(), request.getMajor(),
                request.getEntranceDate(), request.getGraduationDate(), request.getScore(), request.getDegree());

        Long educationId = educationService.save(education);
        return new ResponseEntity(educationId, HttpStatus.CREATED);
    }

    /**
     * Education 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editEducation(@RequestBody @Valid UpdateEducationRequest request, Errors errors,
                                        @AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable("id") Long educationId) {

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

        // TODO : (비교) filter - resume.getEducations()
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid educationId"));
        educationService.updateEducation(request, education);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Education 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteEducation(@AuthenticationPrincipal LoginUser loginUser,
                                          @PathVariable("id") Long educationId) {
        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid projectId"));

        educationService.deleteEducation(resume, education);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    public class EducationDto {

        private String schoolName;
        private String major;
        private LocalDate entranceDate;
        private LocalDate graduationDate;
        private double score;
        private String degree;

        public EducationDto(Education education) {
            this.schoolName = education.getSchoolName();
            this.major = education.getMajor();
            this.entranceDate = education.getEntranceDate();
            this.graduationDate = education.getGraduationDate();
            this.score = education.getScore();
            this.degree = education.getDegree();
        }
    }
}
