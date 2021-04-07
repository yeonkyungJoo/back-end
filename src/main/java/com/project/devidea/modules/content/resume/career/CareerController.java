package com.project.devidea.modules.content.resume.career;

import com.project.devidea.infra.config.security.CurrentUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.career.CreateCareerRequest;
import com.project.devidea.modules.content.resume.form.career.UpdateCareerRequest;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume/career")
public class CareerController {

    private final ResumeRepository resumeRepository;
    private final CareerRepository careerRepository;
    private final CareerService careerService;

    @ApiOperation("Career 전체 조회")
    @GetMapping("/")
    public ResponseEntity getCareers(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }
        List<CareerDto> collect = resume.getCareers().stream()
                .map(career -> new CareerDto(career))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    @ApiOperation("Career 조회")
    @GetMapping("/{id}")
    public ResponseEntity getCareer(@CurrentUser Account account, @PathVariable("id") Long careerId) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new NotFoundException());
        return new ResponseEntity(new CareerDto(career), HttpStatus.OK);
    }

    @ApiOperation("Career 등록")
    @PostMapping("/")
    public ResponseEntity newCareer(@RequestBody @Valid CreateCareerRequest request,
                                    @CurrentUser Account account) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Long careerId = careerService.createCareer(account, request);
        return new ResponseEntity(careerId, HttpStatus.CREATED);
    }

    @ApiOperation("Career 수정")
    @PostMapping("/{id}/edit")
    public ResponseEntity editCareer(@RequestBody @Valid UpdateCareerRequest request,
                                     @PathVariable("id") Long careerId, @CurrentUser Account account) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        careerService.updateCareer(careerId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("Career 삭제")
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteCareer(@PathVariable("id") Long careerId,
                                       @CurrentUser Account account) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        careerService.deleteCareer(careerId);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Data
    public class CareerDto {

        private Long id;
        private String companyName;
        private String duty;
        private String startDate;
        private String endDate;
        private boolean present;
        private Set<String> tags;
        private String detail;
        private String url;

        public CareerDto(Career career) {
            this.id = career.getId();
            this.companyName = career.getCompanyName();
            this.duty = career.getDuty();
            this.startDate = career.getStartDate().toString();
            this.endDate = career.getEndDate().toString();
            this.present = career.isPresent();
            this.tags = career.getTags().stream()
                    .map(tag -> tag.toString()).collect(Collectors.toSet());
            this.detail = career.getDetail();
            this.url = career.getUrl();
        }
    }

}
