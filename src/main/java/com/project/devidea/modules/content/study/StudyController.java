package com.project.devidea.modules.content.study;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.apply.StudyApplyForm;
import com.project.devidea.modules.content.study.apply.StudyApplyListForm;
import com.project.devidea.modules.content.study.form.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
public class StudyController {
    StudyService studyService;

    @GetMapping("/study")
    public List<StudyListForm> 조회4(@Valid StudySearchForm searchForm) {
        return studyService.searchByCondition(searchForm);
    }

    @PostMapping("/study")
    public StudyDetailForm 등록(@Valid StudyMakingForm studyMakingForm) {
        return studyService.makingStudy(studyMakingForm);
    }

    @PostMapping("/study/mystudy")
    public List<StudyListForm> 내스터디(Account account) {
        return studyService.myStudy(account.getNickName());
    }

    @GetMapping("/study/{id}")
    public StudyDetailForm 스터디_자세한정보(@PathVariable Long id) {
        return studyService.getDetailStudy(id);
    }

    @PostMapping("/study/{id}/delete")
    public String 스터디_삭제(@PathVariable Long id) {
        return studyService.deleteStudy(id);
    }

    @PostMapping("/study/{id}/leave")
    public String 스터디에서_나가기(String userName, @PathVariable Long id) {
        return studyService.leaveStudy(userName, id);
    }

    @PostMapping("/study/apply/{applyId}/accept")
    public String 스터디_승인(@Valid StudyApplyForm studyApplyForm) {
        return studyService.decideJoin(studyApplyForm, true);
    }

    @PostMapping("/study/apply/{applyId}/reject")
    public String 스터디_거절(@Valid StudyApplyForm studyApplyForm) {
        return studyService.decideJoin(studyApplyForm, false);
    }

    @GetMapping("/study/{id}/apply/list")
    public List<StudyApplyListForm> 가입_신청_리스트(@PathVariable Long id) {
        return studyService.getApplyList(id);
    }

    @GetMapping("/apply/{applyid}")
    public StudyApplyForm 가입_신청_디테일(@PathVariable Long id) {
        return studyService.getApplyDetail(id);
    }

//    @GetMapping("/study/{id}/members") //미정
//    public List<Account> 스터디_멤버_정보(@PathVariable Long id) {
//
//    }

    @GetMapping("/study/{id}/status")
    public OpenRecruitForm 스터디_공개_및_모집여부_설정폼(@PathVariable Long id) {
        return studyService.getOpenRecruitForm(id);
    }

    @PostMapping("/study/{id}/open") //미정
    public void 스터디_공개_및_모집여부_변경(@PathVariable Long id, OpenRecruitForm openRecruitForm) {
        studyService.UpdateOpenRecruiting(id,openRecruitForm);
    }

    @GetMapping("/study/{id}/tag_zone")
    public TagZoneForm 지역_태그_설정폼(@PathVariable Long id) {
        return studyService.getTagandZone(id);
    }

    @PostMapping("/study/{id}/tag_zone")
    public void 지역_태그_설정_변경(@PathVariable Long id, @Valid TagZoneForm tagZoneForm) {
        studyService.UpdateTagAndZOne(id,tagZoneForm);
    }
}
