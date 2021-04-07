package com.project.devidea.modules.content.resume.award;

import com.project.devidea.infra.config.security.CurrentUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.award.CreateAwardRequest;
import com.project.devidea.modules.content.resume.form.award.UpdateAwardRequest;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resume/award")
@RequiredArgsConstructor
public class AwardController {

    private final ResumeRepository resumeRepository;
    private final AwardRepository awardRepository;
    private final AwardService awardService;

    @ApiOperation("Award 전체 조회")
    @GetMapping("/")
    public ResponseEntity getAwards(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }
        List<AwardDto> collect = resume.getAwards().stream()
                .map(award -> new AwardDto(award))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    @ApiOperation("Award 조회")
    @GetMapping("/{id}")
    public ResponseEntity getAward(@CurrentUser Account account,
                                   @PathVariable("id") Long awardId) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Award award = awardRepository.findById(awardId)
                .orElseThrow(() -> new NotFoundException());
        return new ResponseEntity(new AwardDto(award), HttpStatus.OK);
    }

    @ApiOperation("Award 등록")
    @PostMapping("/")
    public ResponseEntity newAward(@RequestBody @Valid CreateAwardRequest request,
                                   @CurrentUser Account account) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Long awardId = awardService.createAward(account, request);
        return new ResponseEntity(awardId, HttpStatus.CREATED);
    }

    @ApiOperation("Award 수정")
    @PostMapping("/{id}/edit")
    public ResponseEntity editAward(@RequestBody @Valid UpdateAwardRequest request,
                                    @CurrentUser Account account, @PathVariable("id") Long awardId) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        awardService.updateAward(awardId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("Award 삭제")
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteAward(@CurrentUser Account account,
                                      @PathVariable("id") Long awardId) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        awardService.deleteAward(awardId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    public class AwardDto {

        private Long id;
        private String name;
        private String date;
        private String link;
        private String description;

        public AwardDto(Award award) {
            this.id = award.getId();
            this.name = award.getName();
            this.date = award.getDate().toString();
            this.link = award.getLink();
            this.description = award.getDescription();
        }
    }
}
