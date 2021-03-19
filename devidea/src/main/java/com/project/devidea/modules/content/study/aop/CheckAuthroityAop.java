package com.project.devidea.modules.content.study.aop;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.Study_Role;
import com.project.devidea.modules.content.study.apply.StudyApply;
import com.project.devidea.modules.content.study.apply.StudyApplyRepository;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
@Aspect
@RequiredArgsConstructor
public class CheckAuthroityAop {
    private final StudyMemberRepository studyMemberRepository;
    private final StudyApplyRepository studyApplyRepository;
    @Pointcut("@annotation(com.project.devidea.modules.content.study.aop.스터디설정권한)")
    public void 스터디설정권한() {
    }

    @Pointcut("@annotation(com.project.devidea.modules.content.study.aop.스터디신청서권한)")
    public void 스터디신청서권한() {
    }

    @Before("스터디설정권한() && args(account,id)")
    public void 설정권한확인(LoginUser account, Long id) {
        Study_Role study_role = studyMemberRepository.findByStudy_IdAndMember_Nickname(id, account.getNickName()).getRole();
        if (study_role == null || study_role != Study_Role.팀장)
            throw new ValidationException("권한이 없습니다.");
        else System.out.println("성공적으로 권한확인 했습니다.");

    }

    @Before("스터디신청서권한() && args(account,id)")
    public void 신청서권한확인(LoginUser account, Long id) {
        StudyApply studyApply=studyApplyRepository.findById(id).orElseThrow();
        Study_Role study_role = studyMemberRepository.findByStudy_IdAndMember_Nickname(studyApply.getStudy().getId(), account.getNickName()).getRole();
        if (study_role == null || study_role == Study_Role.회원 || study_role == Study_Role.멘토)
            throw new ValidationException("권한이 없습니다.");
        else System.out.println("성공적으로 권한확인 했습니다.");
    }
}
