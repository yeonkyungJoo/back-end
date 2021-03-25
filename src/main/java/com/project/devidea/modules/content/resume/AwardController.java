package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.content.resume.form.CreateCareerRequest;
import com.project.devidea.modules.content.resume.form.UpdateCareerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/resume/award")
@RequiredArgsConstructor
public class AwardController {

    /**
     * Award 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getAwards() {

    }

    /**
     * Award 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getAward() {

    }

    /**
     * Award 등록
     */
    @PostMapping("/")
    public ResponseEntity newAward(@RequestBody @Valid CreateCareerRequest request, Errors errors,
                                   @AuthenticationPrincipal LoginUser loginUser) {

    }

    /**
     * Award 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editAward(@RequestBody @Valid UpdateCareerRequest request, Errors errors,
                                    @AuthenticationPrincipal LoginUser loginUser,
                                    @PathVariable("id") Long awardId) {

    }

    /**
     * Award 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteAward() {

    }
}
