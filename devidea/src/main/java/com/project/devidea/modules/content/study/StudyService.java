package com.project.devidea.modules.content.study;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountRepository;
import com.project.devidea.modules.content.study.apply.StudyApply;
import com.project.devidea.modules.content.study.apply.StudyApplyForm;
import com.project.devidea.modules.content.study.apply.StudyApplyListForm;
import com.project.devidea.modules.content.study.apply.StudyApplyRepository;
import com.project.devidea.modules.content.study.form.*;
import com.project.devidea.modules.content.study.modelmapper.StudyMapper;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final StudyMapper studyMapper;
    private final StudyRepository studyRepository;
    private final ZoneRepository zoneRepository;
    private final TagRepository tagRepository;
    private final AccountRepository accountRepository;
    private final StudyApplyRepository studyApplyRepository;

    List<StudyListForm> searchByCondition(@Valid StudySearchForm studySearchForm) {
        List<Study> studyList=studyRepository.findByCondition(studySearchForm);
        return studyList.stream().map(study -> {
            return studyMapper.StudyList().map(study, StudyListForm.class);
        }).collect(Collectors.toList());
    }
    StudyDetailForm getDetailStudy(Long id){
        return studyMapper.StudyDetail().map(studyRepository.findById(id), StudyDetailForm.class);
    }
    StudyDetailForm makingStudy(@Valid StudyMakingForm studyMakingForm) { //study만들기
        Study study = studyMapper.StudyMaking().map(studyMakingForm, Study.class);
        Zone zone = zoneRepository.findByCityAndProvince(studyMakingForm.getCity(), studyMakingForm.getProvince());
        Set<Tag> tagsSet = studyMakingForm.getTags().stream().map(tag -> {
            return tagRepository.findByFirstName(tag);
        }).collect(Collectors.toSet());
        study.setAdmin(accountRepository.findByUserName(studyMakingForm.getAdmin()));
        study.setLocation(zone);
        study.setTags(tagsSet);
        studyRepository.save(study);
        return studyMapper.StudyDetail().map(study, StudyDetailForm.class);
    }

    String applyStudy(@Valid StudyApplyForm studyApplyForm) {
        Account account = accountRepository.findByUserName(studyApplyForm.getUserName());
        Study study = studyRepository.findById(studyApplyForm.getId()).get();
        if (study == null) return "study 존재하지 않음";
        //알람 넣기
        StudyApply studyApply = StudyApply.builder()
                .study(study)
                .account(account)
                .answer(studyApplyForm.getAnswer())
                .etc(studyApplyForm.getEtc())
                .build();
        studyApplyRepository.save(studyApply);
        return "Yes";//완료 메시지 미완성
    }

    public String decideJoin(@Valid StudyApplyForm studyApplyForm, Boolean accept) {
        Account account = accountRepository.findByUserName(studyApplyForm.getUserName());
        Study study = studyRepository.findById(studyApplyForm.getId()).get();
        StudyApply studyApply = studyApplyRepository.findByAccountAndStudy(account, study);
        if (account == null || study == null || studyApply == null) {
            return "다음과 같은 사용자또는 스터디가 존재하지 않습니다";
        }
        studyApply.setAccpted(accept);
        if (accept) {
            if (study.addMember(account)) return "성공적";
            else return "스터디인원이 꽉찼습니다.";
        }
        else return "거절완료";
    }

    List<StudyApplyForm> getApplyForm(Long id) { //해당 스터디 가입신청 리스트 보기
        return studyApplyRepository.findById(id).stream()
                .map(studyApply -> {
                    return studyMapper.StudyApply().map(studyApply, StudyApplyForm.class);
                }).collect(Collectors.toList());
    }

    String deleteStudy(Long id) { //해당 스터디 가입신청 리스트 보기
        Optional<Study> study=studyRepository.findById(id);
        if(study.isEmpty()) return "없는 스터디 입니다.";
        studyRepository.delete(study.get());
        return "삭제 하였습니다.";
    }

    String leaveStudy(String userName,Long study_id) {
        Study study=studyRepository.findById(study_id).get();
        study.removeMember(accountRepository.findByUserName(userName));
        return "스터디를 떠났습니다.";
    }

    List<StudyListForm> myStudy(String userName) {
        Account account=accountRepository.findByUserName(userName);
        List<Study> studyList=studyRepository.findByMember(account);
        return studyList.stream().map(study->{
            return studyMapper.StudyList().map(study,StudyListForm.class);
        }).collect(Collectors.toList());
    }

    public List<StudyApplyListForm> getApplyList(Long id) {
        return studyApplyRepository.findByStudy_Id(id).stream().map(
                studyApply->{
                    return studyMapper.StudyApplyList().map(studyApply,StudyApplyListForm.class);
                }
        ).collect(Collectors.toList());
    }

    public StudyApplyForm getApplyDetail(Long id) {
        return studyMapper.StudyDetail().map(studyApplyRepository.findById(id),StudyApplyForm.class);
    }

    public OpenRecruitForm getOpenRecruitForm(Long id) {
        Study study=studyRepository.findById(id).get();
        return new OpenRecruitForm(study.isOpen(),study.isRecruiting());
    }

    public TagZoneForm getTagandZone(Long id) {
        Study study=studyRepository.findById(id).get();
        return new TagZoneForm(study.getTags(),study.getLocation());
    }

    public void UpdateOpenRecruiting(Long id, OpenRecruitForm openRecruitForm) {
        Study study=studyRepository.findById(id).get();
        study.setOpenAndRecruiting(openRecruitForm.isOpen(),openRecruitForm.isRecruiting());
    }

    public void UpdateTagAndZOne(Long id, TagZoneForm tagZoneForm) {
        Study study=studyRepository.findById(id).get();
//        study.setTagAndZone(tagZoneForm.s,openRecruitForm.isRecruiting());
    }
}
