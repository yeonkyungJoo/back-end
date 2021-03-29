package com.project.devidea.modules.notification;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.error.exception.EntityNotFoundException;
import com.project.devidea.modules.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @GetMapping("/new")
    public ResponseNotifications<List<Notification>> getNewNotifications(@AuthenticationPrincipal LoginUser loginUser) {
        Account account = loginUser.getAccount();
        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, false);
        int countOldNotifications = notificationRepository.countByAccountAndChecked(account, true);
        return new ResponseNotifications(notifications.size(), countOldNotifications, notifications);
    }

    @GetMapping("/old")
    public ResponseNotifications<List<Notification>> getOldNotifications(@AuthenticationPrincipal LoginUser loginUser) {
        Account account = loginUser.getAccount();
        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, true);
        int countNewNotifications = notificationRepository.countByAccountAndChecked(account, false);
        return new ResponseNotifications(notifications.size(), countNewNotifications, notifications);
    }

    @PostMapping("/delete")
    public void deleteAllOldNotifications(@AuthenticationPrincipal LoginUser loginUser) {
        notificationRepository.deleteByAccountAndChecked(loginUser.getAccount(), true);
    }

    @PostMapping("/{id}/delete")
    public void deleteNotification(@PathVariable("id") Long id) {
        Optional<Notification> findNotification = notificationRepository.findById(id);
        if(!findNotification.isPresent())
            throw new EntityNotFoundException("notification");
        notificationRepository.deleteById(id);
    }

    @GetMapping("/entityErrorTest")
    public void entityErrorTest(Long id) {
        Optional<Notification> findNotification = notificationRepository.findById(id);
        if(!findNotification.isPresent())
            throw new EntityNotFoundException("notification");
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
