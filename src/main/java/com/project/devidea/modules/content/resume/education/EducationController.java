package com.project.devidea.modules.content.resume.education;

import com.project.devidea.infra.config.security.CurrentUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.education.CreateEducationRequest;
import com.project.devidea.modules.content.resume.form.education.UpdateEducationRequest;
import com.project.devidea.modules.content.resume.validator.EducationRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resume/education")
@RequiredArgsConstructor
public class EducationController {

    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final EducationService educationService;

    @ApiOperation("Education 전체 조회")
    @GetMapping("/")
    public ResponseEntity getEducations(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }
        
        List<EducationDto> collect = resume.getEducations().stream()
                .map(education -> new EducationDto(education))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    @ApiOperation("Education 조회")
    @GetMapping("/{id}")
    public ResponseEntity getEducation(@CurrentUser Account account, @PathVariable("id") Long educationId) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException());
        return new ResponseEntity(new EducationDto(education), HttpStatus.OK);
    }

    @ApiOperation("Education 등록")
    @PostMapping("/")
    public ResponseEntity newEducation(@RequestBody @Valid CreateEducationRequest request,
                                       @CurrentUser Account account) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Long educationId = educationService.createEducation(account, request);
        return new ResponseEntity(educationId, HttpStatus.CREATED);
    }

    @ApiOperation("Education 수정")
    @PostMapping("/{id}/edit")
    public ResponseEntity editEducation(@CurrentUser Account account, @PathVariable("id") Long educationId,
                                        @RequestBody @Valid UpdateEducationRequest request) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        educationService.updateEducation(educationId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("Education 삭제")
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteEducation(@CurrentUser Account account, @PathVariable("id") Long educationId) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        educationService.deleteEducation(educationId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    public class EducationDto {

        private Long id;
        private String schoolName;
        private String major;
        private String entranceDate;
        private String graduationDate;
        private double score;
        private String degree;

        public EducationDto(Education education) {
            this.id = education.getId();
            this.schoolName = education.getSchoolName();
            this.major = education.getMajor();
            this.entranceDate = education.getEntranceDate().toString();
            this.graduationDate = education.getGraduationDate().toString();
            this.score = education.getScore();
            this.degree = education.getDegree();
        }
    }
}
