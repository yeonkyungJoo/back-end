package com.project.devidea.modules.notification;

import com.project.devidea.modules.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @GetMapping("/new")
    public ResponseNotifications<List<Notification>> getNewNotifications(Long id) {
        // 시큐리티 적용전 임시
        Account account = new Account().builder().id(id).build();
        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, false);
        return new ResponseNotifications<>(notifications.size(), notifications);
    }

    @GetMapping("/old")
    public ResponseNotifications<List<Notification>> getOldNotifications(Long id) {
        // 시큐리티 적용전 임시
        Account account = new Account().builder().id(id).build();
        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, true);
        return new ResponseNotifications<>(notifications.size(), notifications);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ResponseNotifications<T> {
        private int count;
        private T notifications;
    }
}
