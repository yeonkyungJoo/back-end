package com.project.devidea.modules.notification;

import com.project.devidea.modules.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        int countOldNotifications = notificationRepository.countByAccountAndChecked(account, true);
        return new ResponseNotifications(notifications.size(), countOldNotifications, notifications);
    }

    @GetMapping("/old")
    public ResponseNotifications<List<Notification>> getOldNotifications(Long id) {
        // 시큐리티 적용전 임시
        Account account = new Account().builder().id(id).build();
        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, true);
        int countNewNotifications = notificationRepository.countByAccountAndChecked(account, false);
        return new ResponseNotifications(notifications.size(), countNewNotifications, notifications);
    }

    @PostMapping("/delete")
    public void deleteAllOldNotifications(Long id) {
        // 시큐리티 적용전 임시
        Account account = new Account().builder().id(id).build();
        notificationRepository.deleteByAccountAndChecked(account, true);
    }

    @PostMapping("/{id}/delete")
    public void deleteNotification(@PathVariable("id") Long id) {
        notificationRepository.deleteById(id);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ResponseNotifications<T> {
        private int count;
        private int otherCount;
        private T notifications;
    }
}
