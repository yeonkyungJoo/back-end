package com.project.devidea.modules.content.study.notification;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.account.repository.InterestRepository;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.notification.Notification;
import com.project.devidea.modules.notification.NotificationRepository;
import com.project.devidea.modules.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyCreatedNotification implements StudyNotification {
    static String message = "관심분야의 스터디가 만들어졌습니다.";
    NotificationRepository notificationRepository;

    @Override
    public void send(Study study, JpaRepository jpaRepository) {
        InterestRepository interestRepository = (InterestRepository) jpaRepository;
        List<Account> accountList = interestRepository.findAccountByTagContains(study.getTags());
        List<Notification> notifications = accountList.stream()
                .filter(account -> account.isReceiveStudyNotification() == true)
                .map(account -> {
                return Notification.generateNotification(study.getTitle(), message, NotificationType.STUDY_CREATED, account);
        }).collect(Collectors.toList());
        notificationRepository.saveAll(notifications);
    }
}
