package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.content.resume.form.CreateEducationRequest;
import com.project.devidea.modules.content.resume.form.UpdateEducationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/resume/education")
@RequiredArgsConstructor
public class EducationController {

    /**
     * Education 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getEducations(@AuthenticationPrincipal LoginUser loginUser) {

    }

    /**
     * Education 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getEducation(@AuthenticationPrincipal LoginUser loginUser,
                                       @PathVariable("id") Long educationId) {

    }

    /**
     * Education 등록
     */
    @PostMapping("/")
    public ResponseEntity newEducation(@RequestBody @Valid CreateEducationRequest request, Errors errors,
                                       @AuthenticationPrincipal LoginUser loginUser) {

    }

    /**
     * Education 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editEducation(@RequestBody @Valid UpdateEducationRequest request, Errors errors,
                                        @AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable("id") Long educationId) {

    }

    /**
     * Education 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteEducation(@AuthenticationPrincipal LoginUser loginUser,
                                          @PathVariable("id") Long educationId) {

    }
}
