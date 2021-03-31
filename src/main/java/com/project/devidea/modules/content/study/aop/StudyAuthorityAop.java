package com.project.devidea.modules.content.study.aop;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.content.study.StudyRole;
import com.project.devidea.modules.content.study.apply.StudyApplyRepository;
import com.project.devidea.modules.content.study.exception.StudyAuthorityRequiredException;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class StudyAuthorityAop {
    private final StudyMemberRepository studyMemberRepository;
    private final StudyApplyRepository studyApplyRepository;

    @Pointcut("within(com.project.devidea.modules.content.study.controller.StudyAdminController)")
    public void 스터디팀장권한() {
    }


    @Pointcut("within(com.project.devidea.modules.content.study.controller.StudyManagerController)")
    public void 스터디매니저권한() {
    }


    @Before("스터디팀장권한() && args(account,study_id)")
    public void 팀장권한확인(LoginUser account, Long study_id) {
        StudyRole study_role = getRole(account,study_id);
        if (study_role == null || study_role != StudyRole.팀장)
            throw new StudyAuthorityRequiredException(account.getNickName());
        else System.out.println("성공적으로 권한확인 했습니다.");

    }


    @Before("스터디매니저권한() && args(account,study_id)")
    public void 매니저권환확인(LoginUser account, Long study_id) {
        StudyRole study_role = getRole(account,study_id);
        if (study_role == null || study_role == StudyRole.회원|| study_role == StudyRole.멘토)
            throw new StudyAuthorityRequiredException(account.getNickName());
        else System.out.println("성공적으로 권한확인 했습니다.");
    }
    public StudyRole getRole(LoginUser account, Long study_id){
        return studyMemberRepository.findByStudy_IdAndMember_Id(study_id, account.getAccount().getId()).getRole();
    }
 }