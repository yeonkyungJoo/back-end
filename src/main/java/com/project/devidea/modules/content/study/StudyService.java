package com.project.devidea.modules.content.study;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.study.apply.StudyApply;
import com.project.devidea.modules.content.study.apply.StudyApplyForm;
import com.project.devidea.modules.content.study.apply.StudyApplyListForm;
import com.project.devidea.modules.content.study.apply.StudyApplyRepository;
import com.project.devidea.modules.content.study.form.*;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final ModelMapper studyMapper;
    private final StudyRepository studyRepository;
    private final ZoneRepository zoneRepository;
    private final TagRepository tagRepository;
    private final AccountRepository accountRepository;
    private final StudyApplyRepository studyApplyRepository;
    private final StudyMemberRepository studyMemberRepository;

    public List<StudyListForm> searchByCondition(@Valid StudySearchForm studySearchForm) {
        List<Study> studyList = studyRepository.findByCondition(studySearchForm);
        return studyList.stream().map(study -> {
            return studyMapper.map(study, StudyListForm.class);
        }).collect(Collectors.toList());
    }

    public StudyDetailForm getDetailStudy(Long id) {
        Study study = studyRepository.findById(id).orElseThrow();
        return studyMapper.map(study, StudyDetailForm.class);
    }

    public StudyDetailForm makingStudy(String NickName, @Valid StudyMakingForm studyMakingForm) { //study만들기
        Study study = studyMapper.map(studyMakingForm, Study.class);
        String[] locations = studyMakingForm.getLocation().split("/");
        Zone zone = zoneRepository.findByCityAndProvince(locations[0], locations[1]);
        Set<Tag> tagsSet = studyMakingForm.getTags().stream().map(tag -> {
            return tagRepository.findByFirstName(tag);
        }).collect(Collectors.toSet());
        study.setLocation(zone);
        study.setTags(tagsSet);
        study.setCounts(study.getCounts() + 1);
        Account admin = accountRepository.findByNickname(NickName);
        studyRepository.save(study);
        studyMemberRepository.save(
                StudyMember.builder()
                        .study(study)
                        .member(admin)
                        .JoinDate(LocalDateTime.now())
                        .role(Study_Role.팀장)
                        .build()
        );
        return studyMapper.map(study, StudyDetailForm.class);
    }

    String applyStudy(@Valid StudyApplyForm studyApplyForm) {
        Account applicant = accountRepository.findByNickname(studyApplyForm.getApplicant());
        Study study = studyRepository.findById(studyApplyForm.getStudyId()).orElseThrow();
        if (study == null) return "study 존재하지 않음";
        //알람 넣기
        StudyApply studyApply = StudyApply.builder()
                .study(study)
                .applicant(applicant)
                .answer(studyApplyForm.getAnswer())
                .etc(studyApplyForm.getEtc())
                .build();
        studyApplyRepository.save(studyApply);
        return "Yes";//완료 메시지 미완성
    }

    public String decideJoin(Long id, Boolean accept) {
        StudyApply studyApply = studyApplyRepository.findById(id).orElseThrow();
        Account applicant = studyApply.getApplicant();
        Study study = studyApply.getStudy();
        if (applicant == null || study == null || studyApply == null) {
            return "다음과 같은 사용자또는 스터디가 존재하지 않습니다";
        }
        studyApply.setAccpted(accept);
        if (accept) {
            return addMember(applicant, study, Study_Role.회원);
        } else return "거절완료";
    }

    public String addMember(Account applicant, Study study, Study_Role role) {
        if (study.getCounts() == study.getMaxCount()) return "꽉 찼습니다.";
        StudyMember studyMember = studyMemberRepository.findByStudyAndMember(study, applicant);
        if (studyMember != null) return "이미 있습니다.";
        studyMember = new StudyMember().builder()
                .member(applicant)
                .study(study)
                .role(role)
                .JoinDate(LocalDateTime.now())
                .build();
        study.setCounts(study.getCounts() + 1); //더하기
        studyRepository.save(study);
        studyMemberRepository.save(studyMember);
        return "성공적으로 완료했습니다.";
    }

    List<StudyApplyForm> getApplyForm(Long id) { //해당 스터디 가입신청 리스트 보기
        return studyApplyRepository.findById(id).stream()
                .map(studyApply -> {
                    return studyMapper.map(studyApply, StudyApplyForm.class);
                }).collect(Collectors.toList());
    }

    public String deleteStudy(String nickname, Long id) { //해당 스터디 가입신청 리스트 보기
        Account account = accountRepository.findByEmail(nickname).orElse(
                accountRepository.findByNickname(nickname));
        Study study = studyRepository.findById(id).orElseThrow();
        Study_Role member_role = studyMemberRepository.findByStudyAndMember(study, account).getRole();
        if (member_role != Study_Role.팀장) return "스터디 삭제 권한이 없습니다.";
        studyRepository.delete(study);
        return "성공적으로 삭제하였습니다.";
    }

    public String leaveStudy(String nickName, Long study_id) {
        Account account = accountRepository.findByNickname(nickName);
        studyMemberRepository.deleteByStudy_IdAndMember_Id(study_id,account.getId());
        studyRepository.LeaveStudy(study_id);
        return "스터디를 떠났습니다.";
    }

    public List<StudyListForm> myStudy(String email) {
        Account account = accountRepository.findByEmail(email).orElse(
                accountRepository.findByNickname(email));
        List<StudyMember> studyList = studyMemberRepository.findByMember(account);
        return studyList.stream().map(study -> {
            return studyMapper.map(study.getStudy(), StudyListForm.class);
        }).collect(Collectors.toList());
    }

    public List<StudyApplyListForm> getApplyList(Long id) {
        return studyApplyRepository.findByStudy_Id(id).stream().map(
                studyApply -> {
                    return studyMapper.map(studyApply, StudyApplyListForm.class);
                }
        ).collect(Collectors.toList());
    }

    public StudyApplyForm getApplyDetail(Long id) {
        return studyMapper.map(studyApplyRepository.findById(id), StudyApplyForm.class);
    }

    public OpenRecruitForm getOpenRecruitForm(Long id) {
        Study study = studyRepository.findById(id).orElseThrow();
        return new OpenRecruitForm(study.isOpen(), study.isRecruiting());
    }

    public TagZoneForm getTagandZone(Long id) {
        Study study = studyRepository.findById(id).orElseThrow();
        return new TagZoneForm(study.getTags(), study.getLocation());
    }

    public String UpdateOpenRecruiting(Long id, OpenRecruitForm openRecruitForm) {
        Study study = studyRepository.findById(id).orElseThrow();
        study.setOpenAndRecruiting(openRecruitForm.isOpen(), openRecruitForm.isRecruiting());
        return "success";
    }

    public String UpdateTagAndZOne(Long id, TagZoneForm tagZoneForm) {
        Study study = studyRepository.findById(id).orElseThrow();
        return "success";
    }

    public StudyApplyForm makeStudyForm(Long id) {
        Study study = studyRepository.findById(id).orElseThrow();
        StudyApplyForm studyApplyForm=new StudyApplyForm()
                .builder()
                .studyId(study.id)
                .study(study.getTitle())
                .answer(study.getQuestion())
                .applicant("")
                .build();
        return studyApplyForm;
    }
}
