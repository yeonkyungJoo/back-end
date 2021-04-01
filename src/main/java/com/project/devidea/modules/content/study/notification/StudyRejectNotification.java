package com.project.devidea.modules.content.study.notification;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.notification.Notification;
import com.project.devidea.modules.notification.NotificationRepository;
import com.project.devidea.modules.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudyRejectNotification implements StudyNotification{
    private final NotificationRepository notificationRepository;
    String message_reject="스터디 가입신청이 거절 되었습니다.";
    @Override
    public void sendRelated(Study study, JpaRepository jpaRepository) {
        return ;
    }

    @Override
    public void sendOwn(Study study, Account account, JpaRepository jpaRepository) {
        notificationRepository.save(Notification.generateNotification(study.getTitle(), message_reject, NotificationType.STUDY, account));
    }

    @Override
    public void sendAll(Study study, Account account, JpaRepository jpaRepository) {
        sendOwn(study,account,jpaRepository);
    }
}
