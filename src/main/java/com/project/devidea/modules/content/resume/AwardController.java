package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.resume.form.CreateAwardRequest;
import com.project.devidea.modules.content.resume.form.UpdateAwardRequest;
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
@RequestMapping("/resume/award")
@RequiredArgsConstructor
public class AwardController {

    private final ResumeRepository resumeRepository;
    private final AwardRepository awardRepository;
    private final AwardService awardService;

    /**
     * Award 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getAwards(@AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // TODO : (비교) awardRepository.findByResumeId(resume.getId());
        List<Award> awards = resume.getAwards();
        List<AwardDto> collect = awards.stream()
                .map(award -> new AwardDto(award))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    /**
     * Award 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getAward(@AuthenticationPrincipal LoginUser loginUser,
                                   @PathVariable("id") Long awardId) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Award award = awardRepository.findById(awardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid awardId"));

        return new ResponseEntity(new AwardDto(award), HttpStatus.OK);
    }

    /**
     * Award 등록
     */
    @PostMapping("/")
    public ResponseEntity newAward(@RequestBody @Valid CreateAwardRequest request, Errors errors,
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

        Award award = Award.createAward(resume, request.getName(),
                request.getDate(), request.getLink(), request.getDescription());
        Long awardId = awardService.save(award);
        return new ResponseEntity(awardId, HttpStatus.CREATED);
    }

    /**
     * Award 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editAward(@RequestBody @Valid UpdateAwardRequest request, Errors errors,
                                    @AuthenticationPrincipal LoginUser loginUser,
                                    @PathVariable("id") Long awardId) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Award award = awardRepository.findById(awardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid awardId"));
        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        awardService.updateAward(request, award);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Award 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteAward(@AuthenticationPrincipal LoginUser loginUser,
                                      @PathVariable("id") Long awardId) {
        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Award award = awardRepository.findById(awardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid awardId"));

        awardService.deleteAward(resume, award);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    public class AwardDto {

        private Long id;
        private String name;
        private LocalDate date;
        private String link;
        private String description;

        public AwardDto(Award award) {
            this.id = award.getId();
            this.name = award.getName();
            this.date = award.getDate();
            this.link = award.getLink();
            this.description = award.getDescription();
        }
    }
}
