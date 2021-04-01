package com.project.devidea.modules.content.study.notification;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.InterestRepository;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.notification.Notification;
import com.project.devidea.modules.notification.NotificationRepository;
import com.project.devidea.modules.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class StudyJoinNotification implements StudyNotification {
    static String messageOthers = "스터디원이 들어왔습니다.";
    static String messageApplicant = "스터디원이 됐습니다.";
    private final NotificationRepository notificationRepository;
    @Override
    public void sendRelated(Study study, JpaRepository jpaRepository) {
        InterestRepository interestRepository = (InterestRepository) jpaRepository;
        List<Account> accountList = interestRepository.findAccountByTagContains(study.getTags());
        List<Notification> notifications = accountList.stream()
                .filter(account -> account.isReceiveStudyNotification() == true)
                .map(account -> {
                    return Notification.generateNotification(study.getTitle(), messageOthers, NotificationType.STUDY, account);
                }).collect(Collectors.toList());
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void sendOwn(Study study, Account account, JpaRepository jpaRepository) {
        notificationRepository.save(Notification.generateNotification(study.getTitle(), messageOthers, NotificationType.STUDY, account));
    }

    @Override
    public void sendAll(Study study, Account account, JpaRepository jpaRepository) {
        sendOwn(study,account,jpaRepository);
        sendRelated(study,jpaRepository);
    }
}