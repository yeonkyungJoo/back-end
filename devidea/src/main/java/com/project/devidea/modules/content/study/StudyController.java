package com.project.devidea.modules.content.study;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.content.study.apply.StudyApplyForm;
import com.project.devidea.modules.content.study.form.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RestController
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @GetMapping("/study")
    public ResponseEntity<?> 조회(@RequestBody @Valid StudySearchForm searchForm) {
        return new ResponseEntity<>(studyService.searchByCondition(searchForm), HttpStatus.OK);
    }

    @PostMapping("/study")
    public ResponseEntity<?> 등록(@AuthenticationPrincipal LoginUser account, @RequestBody @Valid StudyMakingForm studyMakingForm) {
        if(account.getNickName()==null) return new ResponseEntity<>("로그인 해주십쇼.", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(studyService.makingStudy(account.getNickName(),studyMakingForm), HttpStatus.OK);
    }

    @PostMapping("/study/mystudy")
    public ResponseEntity<?> 내스터디(@AuthenticationPrincipal LoginUser account) {
        return new ResponseEntity<>(studyService.myStudy(account.getNickName()), HttpStatus.OK);
    }

    @GetMapping("/study/{id}")
    public ResponseEntity<?> 스터디_자세한정보(@PathVariable Long id){
        return new ResponseEntity<>(studyService.getDetailStudy(id), HttpStatus.OK);
}

    @PostMapping("/study/{id}/delete")
    public ResponseEntity<?> 스터디_삭제(@AuthenticationPrincipal LoginUser account, @PathVariable Long id) {
        return new ResponseEntity<>(studyService.deleteStudy(account.getNickName(),id), HttpStatus.OK);
    }

    @PostMapping("/study/{id}/leave")
    public ResponseEntity<?> 스터디에서_나가기(@AuthenticationPrincipal LoginUser account, @PathVariable Long id) {
        return new ResponseEntity<>(studyService.leaveStudy(account.getNickName(), id), HttpStatus.OK);
    }

    @PostMapping("/study/apply/{applyId}/accept")
    public ResponseEntity<?> 스터디_승인(@AuthenticationPrincipal LoginUser account, @Valid StudyApplyForm studyApplyForm) {
        return new ResponseEntity<>(studyService.decideJoin(studyApplyForm, true), HttpStatus.OK);
    }

    @PostMapping("/study/apply/{applyId}/reject")
    public ResponseEntity<?> 스터디_거절(@AuthenticationPrincipal LoginUser account, @Valid StudyApplyForm studyApplyForm) {
        return new ResponseEntity<>(studyService.decideJoin(studyApplyForm, false), HttpStatus.OK);
    }

    @GetMapping("/study/{id}/apply/list")
    public ResponseEntity<?> 가입_신청_리스트(@AuthenticationPrincipal LoginUser account, @PathVariable Long id) {
        return new ResponseEntity<>(studyService.getApplyList(id), HttpStatus.OK);
    }

    @GetMapping("/apply/{applyid}")
    public ResponseEntity<?> 가입_신청_디테일_보기(@AuthenticationPrincipal LoginUser account, @PathVariable Long id) {
        return new ResponseEntity<>(studyService.getApplyDetail(id), HttpStatus.OK);
    }

//    @GetMapping("/study/{id}/members") //미정
//    public List<Account> 스터디_멤버_정보(@PathVariable Long id) {
//
//    }

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


}
