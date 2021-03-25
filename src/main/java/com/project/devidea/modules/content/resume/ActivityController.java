package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.content.resume.form.CreateActivityRequest;
import com.project.devidea.modules.content.resume.form.UpdateActivityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/resume/activity")
@RequiredArgsConstructor
public class ActivityController {

    /**
     * Activity 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getActivities(@AuthenticationPrincipal LoginUser loginUser) {

    }

    /**
     * Activity 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getActivity(@AuthenticationPrincipal LoginUser loginUser,
                                      @PathVariable("id") Long activityId) {

    }

    /**
     * Activity 등록
     */
    @PostMapping("/")
    public ResponseEntity newActivity(@AuthenticationPrincipal LoginUser loginUser,
                                      @RequestBody @Valid CreateActivityRequest request, Errors errors) {

    }

    /**
     * Activity 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editActivity(@AuthenticationPrincipal LoginUser loginUser,
                                       @RequestBody @Valid UpdateActivityRequest request, Errors errors,
                                       @PathVariable("id") Long activityId) {

    }

    /**
     * Activity 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteActivity(@AuthenticationPrincipal LoginUser loginUser,
                                         @PathVariable("id") Long activityId) {

    }
}
