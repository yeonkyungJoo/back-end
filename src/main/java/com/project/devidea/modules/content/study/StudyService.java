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
import com.project.devidea.modules.notification.Notification;
import com.project.devidea.modules.notification.NotificationRepository;
import com.project.devidea.modules.notification.NotificationService;
import com.project.devidea.modules.notification.NotificationType;
import com.project.devidea.modules.notification.aop.WithNotification;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
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
    private final NotificationRepository notificationRepository;

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
        Study study = ConvertToStudy(studyMakingForm);
        Account admin = accountRepository.findByNickname(NickName);
        studyRepository.save(study);
        studyMemberRepository.save(generateStudyMember(study, admin, Study_Role.팀장));
        StudyDetailForm studyDetailForm = studyMapper.map(study, StudyDetailForm.class);
        studyDetailForm.setMembers(new HashSet<String>(Arrays.asList(admin.getName().toString())));
        return studyDetailForm;
    }

    public String applyStudy(@Valid StudyApplyForm studyApplyForm) {
        StudyApply studyApply = StudyApply.builder()
                .study(Study.generateStudyById(studyApplyForm.getStudyId()))
                .applicant(Account.generateAccountById(accountRepository.findIdByNickname(studyApplyForm.getApplicant())))
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
        if(study.getCounts()==study.getMaxCount())
            return "인원이 꽉찼습니다.";
        studyApply.setAccpted(accept);
        if (accept) {
            return addMember(applicant, study, Study_Role.회원);
        }
        else return "성공적으로 거절하였습니다.";
    }

    public String addMember(Account applicant, Study study, Study_Role role) {
        studyMemberRepository.save(
                StudyMember.
                        builder()
                        .study(study)
                        .member(applicant)
                        .role(role)
                        .build()
        );
        notificationRepository.save(
                        new Notification().builder()
                            .account(applicant)
                            .createdDateTime(LocalDateTime.now())
                            .message(study.getTitle()+"스터디에 가입 됐습니다.")
                            .title(study.getTitle()+"스터디 승인 완료")
                            .build()
        );
        return "성공적으로 저장하였습니다.";
    }

    List<StudyApplyForm> getApplyForm(Long id) { //해당 스터디 가입신청 리스트 보기
        return studyApplyRepository.findById(id).stream()
                .map(studyApply -> {
                    return studyMapper.map(studyApply, StudyApplyForm.class);
                }).collect(Collectors.toList());
    }
    public String deleteStudy(Long id) { //해당 스터디 가입신청 리스트 보기
        Study study = studyRepository.findById(id).orElseThrow();
        studyRepository.delete(study);
        return "성공적으로 삭제하였습니다.";
    }

    public String leaveStudy(String nickName, Long study_id) {
        Account account = accountRepository.findByNickname(nickName);
        studyMemberRepository.deleteByStudy_IdAndMember_Id(study_id, account.getId());
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
        StudyApplyForm studyApplyForm = new StudyApplyForm()
                .builder()
                .studyId(study.id)
                .study(study.getTitle())
                .answer(study.getQuestion())
                .applicant("")
                .build();
        return studyApplyForm;
    }

    public Study ConvertToStudy(StudyMakingForm studyMakingForm) {
        Study study = studyMakingForm.toStudy();
        String[] locations = studyMakingForm.getLocation().split("/");
        Zone zone = zoneRepository.findByCityAndProvince(locations[0], locations[1]);
        Set<Tag> tagsSet = studyMakingForm.getTags().stream().map(tag -> {
            return tagRepository.findByFirstName(tag);
        }).collect(Collectors.toSet());
        study.setLocation(zone);
        study.setTags(tagsSet);
        study.setCounts(study.getCounts() + 1);
        return study;
    }

    public StudyMember generateStudyMember(Study study, Account account, Study_Role role) {
        return StudyMember.builder()
                .study(study)
                .member(account)
                .JoinDate(LocalDateTime.now())
                .role(role)
                .build();
    }

    public String setEmpower(Long study_id, EmpowerForm empowerForm) {
        studyMemberRepository.updateRole(study_id, accountRepository.findByNickname(empowerForm.getNickName()).getId(), empowerForm.getRole());
        return "성공적으로 권한을 부여했습니다.";
    }

    public List<StudyApplyForm> myApplyList(String nickName) {
        List<StudyApply> studyApplies = studyApplyRepository.findByApplicant_Nickname(nickName);
        return studyApplies.stream().map(studyApply -> {
                    return studyMapper.map(studyApply, StudyApplyForm.class);
                }
        ).collect(Collectors.toList());
    }
}
