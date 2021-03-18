package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.Mentor;
import com.project.devidea.modules.content.mentoring.MentorRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.sun.mail.iap.Response;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CareerController {

    private final MentorRepository mentorRepository;
    private final CareerRepository careerRepository;

    @GetMapping("/resume/careers")
    public ResponseEntity getCareers(@AuthenticationPrincipal Account account) {

        // @CurrentUser OR checkIsAuthenticated();
        if(account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        // checkIsMentor(Long accountId);
        Long accountId = account.getId();
        Mentor findMentor = mentorRepository.findByAccountId(accountId);
        if (findMentor == null) {
            // 예외 처리
        }

        List<Career> careers = findMentor.getResume().getCareers();
        List<CareerDto> collect = careers.stream()
                .map(career -> new CareerDto(career))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    @GetMapping("resume/careers/{careerId}")
    public ResponseEntity getCareer(@AuthenticationPrincipal Account account,
                                        @PathVariable("careerId") Long careerId) {

        // checkIsAuthenticated()

        // checkIsMentor(Long accountId);
        Long accountId = account.getId();
        Mentor findMentor = mentorRepository.findByAccountId(accountId);
        if (findMentor == null) {
            // 예외 처리
        }




    }

    public ResponseEntity newCareer() {

    }

    public ResponseEntity editCareer() {

    }

    public ResponseEntity deleteCareer() {

    }

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
