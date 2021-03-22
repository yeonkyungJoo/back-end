package com.project.devidea.modules.content.study.controller;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.content.study.StudyService;
import com.project.devidea.modules.content.study.form.OpenRecruitForm;
import com.project.devidea.modules.content.study.form.TagZoneForm;
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

import javax.validation.Valid;
@Controller
@RestController
@RequiredArgsConstructor
public class StudyAdminController {

    private final StudyService studyService;
    private final StudyMemberRepository studyMemberRepository;

    @GetMapping("/study/{id}/status")
    public ResponseEntity<?> 스터디_공개_및_모집여부_설정폼(@AuthenticationPrincipal LoginUser account, @PathVariable Long id) {
        return new ResponseEntity<>(studyService.getOpenRecruitForm(id), HttpStatus.OK);
    }

    @PostMapping("/study/{id}/open") //미정
    public ResponseEntity<?> 스터디_공개_및_모집여부_변경(@AuthenticationPrincipal LoginUser account, @PathVariable Long id, OpenRecruitForm openRecruitForm) {
        return new ResponseEntity<>(studyService.UpdateOpenRecruiting(id, openRecruitForm), HttpStatus.OK);
    }

    @GetMapping("/study/{id}/tag_zone")
    public ResponseEntity<?> 지역_태그_설정폼(@AuthenticationPrincipal LoginUser account, @PathVariable Long id) {
        return new ResponseEntity<>(studyService.getTagandZone(id), HttpStatus.OK);
    }

    @PostMapping("/study/{id}/tag_zone")
    public ResponseEntity<?> 지역_태그_설정_변경(@AuthenticationPrincipal LoginUser account, @PathVariable Long id, @Valid TagZoneForm tagZoneForm) {
        return new ResponseEntity<>(studyService.UpdateTagAndZOne(id, tagZoneForm), HttpStatus.OK);
    }

    @PostMapping("/study/{id}/delete")
    public ResponseEntity<?> 스터디_삭제(@AuthenticationPrincipal LoginUser account, @PathVariable Long id) {
        return new ResponseEntity<>(studyService.deleteStudy(account.getNickName(),id), HttpStatus.OK);
    }
}
