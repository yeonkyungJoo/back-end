package com.project.devidea.modules.content.study.controller;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.content.study.StudyService;
import com.project.devidea.modules.content.study.aop.스터디신청서권한;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
public class StudyManagerController {
    private final StudyService studyService;
    private final StudyMemberRepository studyMemberRepository;

    @PostMapping("/study/apply/{applyId}/reject")
    @스터디신청서권한
    public ResponseEntity<?> 스터디_신청서_거절(@AuthenticationPrincipal LoginUser account, @PathVariable(name="applyId") Long id) {
        return new ResponseEntity<>(studyService.decideJoin(id, false), HttpStatus.OK);
    }

    @GetMapping("/study/{id}/apply/list")
    @스터디신청서권한
    public ResponseEntity<?> 가입_신청_리스트(@AuthenticationPrincipal LoginUser account, @PathVariable Long id) {
        return new ResponseEntity<>(studyService.getApplyList(id), HttpStatus.OK);
    }


    @GetMapping("/apply/{applyid}")
    @스터디신청서권한
    public ResponseEntity<?> 가입_신청_디테일_보기(@AuthenticationPrincipal LoginUser account, @PathVariable Long id) {
        return new ResponseEntity<>(studyService.getApplyDetail(id), HttpStatus.OK);
    }

    @PostMapping("/study/apply/{applyId}/accept")
    @스터디신청서권한
    public ResponseEntity<?> 스터디_신청서_승인(@AuthenticationPrincipal LoginUser account,@PathVariable(name="applyId") Long id) {
        return new ResponseEntity<>(studyService.decideJoin(id, true), HttpStatus.OK);
    }
}
