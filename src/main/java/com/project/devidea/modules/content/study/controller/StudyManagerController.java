package com.project.devidea.modules.content.study.controller;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.content.study.StudyService;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor
public class StudyManagerController {
    private final StudyService studyService;
    private final StudyMemberRepository studyMemberRepository;


    @GetMapping("/study/{study_id}/apply/list")
    public ResponseEntity<?> 가입_신청_리스트(@AuthenticationPrincipal LoginUser account, @PathVariable(name = "study_id") Long study_id) {
        return new ResponseEntity<>(studyService.getApplyList(study_id), HttpStatus.OK);
    }


    @GetMapping("/study/{study_id}/apply/{apply_id}")
    public ResponseEntity<?> 가입_신청_디테일_보기(@AuthenticationPrincipal LoginUser account, @PathVariable(name = "study_id") Long study_id,
                                          @PathVariable(name = "apply_id") Long apply_id) {
        return new ResponseEntity<>(studyService.getApplyDetail(apply_id), HttpStatus.OK);
    }

    @PostMapping("/study/{study_id}/apply/{apply_id}/reject")
    public ResponseEntity<?> 스터디_신청서_거절(@AuthenticationPrincipal LoginUser account, @PathVariable(name = "study_id") Long study_id,
                                        @PathVariable(name = "apply_id") Long apply_id) {
        return new ResponseEntity<>(studyService.decideJoin(apply_id, false), HttpStatus.OK);
    }

    @PostMapping("/study/{study_id}/apply/{apply_id}/accept")
    public ResponseEntity<?> 스터디_신청서_승인(@AuthenticationPrincipal LoginUser account, @PathVariable(name = "study_id") Long study_id,
                                        @PathVariable(name = "apply_id") Long apply_id) {
        return new ResponseEntity<>(studyService.decideJoin(apply_id, true), HttpStatus.OK);
    }
}
