package com.project.devidea.modules.notification;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
