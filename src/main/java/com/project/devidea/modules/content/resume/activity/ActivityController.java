package com.project.devidea.modules.content.resume.activity;

import com.project.devidea.infra.config.security.CurrentUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.activity.CreateActivityRequest;
import com.project.devidea.modules.content.resume.form.activity.UpdateActivityRequest;
import com.project.devidea.modules.content.resume.validator.ActivityRequestValidator;
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
@RequestMapping("/resume/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ResumeRepository resumeRepository;
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;
    private final ActivityRequestValidator activityRequestValidator;

    @InitBinder("request")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(activityRequestValidator);
    }

    /**
     * Activity 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getActivities(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }
        List<ActivityDto> collect = resume.getActivites().stream()
                .map(activity -> new ActivityDto(activity))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    /**
     * Activity 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getActivity(@CurrentUser Account account, @PathVariable("id") Long activityId) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException());
        return new ResponseEntity(new ActivityDto(activity), HttpStatus.OK);
    }

    /**
     * Activity 등록
     */
    @PostMapping("/")
    public ResponseEntity newActivity(@CurrentUser Account account,
                                      @RequestBody @Valid CreateActivityRequest request) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Long activityId = activityService.createActivity(account, request);
        return new ResponseEntity(activityId, HttpStatus.CREATED);
    }

    /**
     * Activity 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editActivity(@CurrentUser Account account, @PathVariable("id") Long activityId,
                                       @RequestBody @Valid UpdateActivityRequest request) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        activityService.updateActivity(activityId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Activity 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteActivity(@CurrentUser Account account,
                                         @PathVariable("id") Long activityId) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        activityService.deleteActivity(activityId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    public class ActivityDto {

        private Long id;
        private String activityName;
        private String startDate;
        private String endDate;
        private String description;
        private String link;

        public ActivityDto(Activity activity) {
            this.id = activity.getId();
            this.activityName = activity.getActivityName();
            this.startDate = activity.getStartDate().toString();
            this.endDate = activity.getEndDate().toString();
            this.description = activity.getDescription();
            this.link = activity.getLink();
        }
    }
}
