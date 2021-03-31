package com.project.devidea.modules.content.study.notification;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.StudyMember;
import com.project.devidea.modules.content.study.StudyRole;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import com.project.devidea.modules.notification.Notification;
import com.project.devidea.modules.notification.NotificationRepository;
import com.project.devidea.modules.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class StudyApplyNotification implements StudyNotification{
    static String message_releated = "스터디 지원자가 있습니다.";
    static String message_applicant = "스터디에 성공적으로 지원했습니다.";
    private final NotificationRepository notificationRepository;
    @Override
    public void sendRelated(Study study, JpaRepository jpaRepository) {
        StudyMemberRepository studyMemberRepository=(StudyMemberRepository) jpaRepository;
        List<StudyMember> studyMembers=studyMemberRepository.findByStudy_Id(study.getId());
        List<Notification> notifications=studyMembers.stream().filter(studyMember ->
            (studyMember.getRole().equals(StudyRole.괸리자))||(studyMember.getRole().equals(StudyRole.팀장))
        ).map(studyMember -> {
            return Notification.generateNotification(study.getTitle(), message_releated, NotificationType.STUDY, studyMember.getMember());
        }).collect(Collectors.toList());
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void sendOwn(Study study, Account account, JpaRepository jpaRepository) {
        notificationRepository.save(Notification.generateNotification(study.getTitle(), message_applicant, NotificationType.STUDY,account));
    }

    @Override
    public void sendAll(Study study, Account account, JpaRepository jpaRepository) {
        sendRelated(study,jpaRepository);
        sendOwn(study,account,jpaRepository);
    }
}
