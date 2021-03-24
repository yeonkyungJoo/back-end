package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.Mentor;
import com.project.devidea.modules.content.mentoring.MentorRepository;
import com.project.devidea.modules.content.resume.form.CreateCareerRequest;
import com.project.devidea.modules.content.resume.form.UpdateCareerRequest;
import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume/career")
public class CareerController {

    private final MentorRepository mentorRepository;
    private final ResumeRepository resumeRepository;

    private final CareerRepository careerRepository;
    private final CareerService careerService;
    private final ModelMapper modelMapper;

    /**
     * Career 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getCareers(@AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        // TODO - Test: 멘토가 아닌 경우
        Mentor findMentor = checkIsMentor(account.getId());
        List<Career> careers = findMentor.getResume().getCareers();
        List<CareerDto> collect = careers.stream()
                .map(career -> new CareerDto(career))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    private Mentor checkIsMentor(Long accountId) {
        Mentor findMentor = mentorRepository.findByAccountId(accountId);
        if (findMentor == null) {
            // 예외 처리
        }
        return findMentor;
    }

    /**
     * Career 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getCareer(@AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable("id") Long careerId) {
        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();
        // TODO - Test: 멘토가 아닌 경우
        Mentor findMentor = checkIsMentor(account.getId());

        Career career = findMentor.getResume().getCareers()
                .stream().filter(c -> c.getId().equals(careerId)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid careerId"));

        return new ResponseEntity(new CareerDto(career), HttpStatus.OK);
    }

    /**
     * Career 등록
     */
    @PostMapping("/")
    public ResponseEntity newCareer(@RequestBody @Valid CreateCareerRequest request, Errors errors,
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

        Career career = Career.createCareer(resume, request.getCompanyName(), request.getDuty(),
                                request.getStartDate(), request.getEndDate(), request.isPresent(),
                                request.getTags(), request.getDetail(), request.getUrl());
        Long careerId = careerRepository.save(career).getId();
        return new ResponseEntity(careerId, HttpStatus.CREATED);
    }

    /**
     * Career 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editCareer(@RequestBody @Valid UpdateCareerRequest request, Errors errors,
                                     @PathVariable("id") Long careerId, @AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        careerService.updateCareer(request, career);
        return new ResponseEntity(careerId, HttpStatus.OK);
    }

    /**
     * Career 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteCareer(@PathVariable("id") Long careerId, @AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // TODO - Test: 존재하지 않는 Career
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

        careerService.deleteCareer(resume, career);
        return new ResponseEntity(careerId, HttpStatus.OK);
    }


    @Data
    public class CareerDto {

        private String companyName;
        private String duty;
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean present;
        private Set<Tag> tags;
        private String detail;
        private String url;

        public CareerDto(Career career) {
            this.companyName = career.getCompanyName();
            this.duty = career.getDuty();
            this.startDate = career.getStartDate();
            this.endDate = career.getEndDate();
            this.present = career.isPresent();
            this.tags = career.getTags();
            this.detail = career.getDetail();
            this.url = career.getUrl();
        }
    }

}
