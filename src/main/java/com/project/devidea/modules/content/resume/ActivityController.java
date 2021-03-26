package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.resume.form.CreateActivityRequest;
import com.project.devidea.modules.content.resume.form.UpdateActivityRequest;
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
@RequestMapping("/resume/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ResumeRepository resumeRepository;
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;

    /**
     * Activity 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getActivities(@AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        List<Activity> activities = resume.getActivites();
        List<ActivityDto> collect = activities.stream()
                .map(activity -> new ActivityDto(activity))
                .collect(Collectors.toList());

        return new ResponseEntity(collect, HttpStatus.OK);
    }

    /**
     * Activity 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getActivity(@AuthenticationPrincipal LoginUser loginUser,
                                      @PathVariable("id") Long activityId) {
        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid activityId"));
        return new ResponseEntity(new ActivityDto(activity), HttpStatus.OK);
    }

    /**
     * Activity 등록
     */
    @PostMapping("/")
    public ResponseEntity newActivity(@AuthenticationPrincipal LoginUser loginUser,
                                      @RequestBody @Valid CreateActivityRequest request, Errors errors) {

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

        Activity activity = Activity.createActivity(resume, request.getActivityName(),
                request.getStartDate(), request.getEndDate(), request.getDescription(), request.getLink());

        Long activityId = activityService.save(activity);
        return new ResponseEntity(activityId, HttpStatus.CREATED);
    }

    /**
     * Activity 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editActivity(@AuthenticationPrincipal LoginUser loginUser,
                                       @RequestBody @Valid UpdateActivityRequest request, Errors errors,
                                       @PathVariable("id") Long activityId) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid activityId"));

        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        activityService.updateActivity(request, activity);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Activity 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteActivity(@AuthenticationPrincipal LoginUser loginUser,
                                         @PathVariable("id") Long activityId) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid activityId"));
        activityService.deleteActivity(resume, activity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    public class ActivityDto {

        private Long id;
        private String activityName;
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
        private String link;

        public ActivityDto(Activity activity) {
            this.id = activity.getId();
            this.activityName = activity.getActivityName();
            this.startDate = activity.getStartDate();
            this.endDate = activity.getEndDate();
            this.description = activity.getDescription();
            this.link = activity.getLink();
        }
    }
}
