package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.Mentor;
import com.project.devidea.modules.content.mentoring.MentorRepository;
import com.project.devidea.modules.content.resume.form.CreateCareerRequest;
import com.project.devidea.modules.content.resume.form.UpdateCareerRequest;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.sun.mail.iap.Response;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume/career")
public class CareerController {

    private final MentorRepository mentorRepository;

    @GetMapping("/")
    public ResponseEntity getCareers(@AuthenticationPrincipal Account account) {

        // OR @CurrentUser
        if(account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

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

    @GetMapping("/{id}")
    public ResponseEntity getCareer(@AuthenticationPrincipal Account account,
                                        @PathVariable("id") Long careerId) {

        Mentor findMentor = checkIsMentor(account.getId());
        Career career = findMentor.getResume().getCareers()
                .stream().filter(c -> c.getId().equals(careerId)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid careerId"));

        return new ResponseEntity(new CareerDto(career), HttpStatus.OK);
    }

/*
    @PostMapping("/{id}/edit")
    public ResponseEntity editCareer(@RequestBody @Valid UpdateCareerRequest request, Errors errors,
                                        @PathVariable("id") Long careerId) {
        return null;
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity deleteCareer(@PathVariable("id") Long careerId) {
        return null;
    }
*/

    @Data
    public class CareerDto {

        private String companyName;
        private String duty;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
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
