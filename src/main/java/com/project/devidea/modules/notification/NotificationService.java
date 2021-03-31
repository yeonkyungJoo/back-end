package com.project.devidea.modules.notification;

import com.project.devidea.modules.account.Account;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void markAsRead(List<Notification> notifications) {
        notifications.forEach(it -> it.markAsRead());
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public Long createNotification(NotificationType type, Long id, Account account, String title, String message) {
        String link = "";
        if(id != 0) link = "/" + type + "/" + id;
        Notification notification = Notification.builder()
                .notificationType(type)
                .account(account)
                .title(title)
                .message(message)
                .checked(false)
                .link(link)
                .createdDateTime(LocalDateTime.now()).build();

       notificationRepository.save(notification);

       return notification.getId();
    }
}
