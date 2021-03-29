package com.project.devidea.modules.notification;

import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountDummy;
import com.project.devidea.modules.account.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;


@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @BeforeEach
    void init() {
        Account account = accountRepository.save(AccountDummy.getAccount());

        List<Notification> oldNotifications = NotificationDummy.getOldNotifications();
        List<Notification> newNotifications = NotificationDummy.getNewNotifications();

        oldNotifications.forEach(notification -> { notification.setAccount(account);
            notificationRepository.save(notification);
        });
        newNotifications.forEach(notification -> { notification.setAccount(account);
            notificationRepository.save(notification);
        });
    }

    @DisplayName("알람 읽음 처리")
    @Test
    public void markAsRead() throws Exception {

        //given : 읽지 않은 알림 3개, 읽은 알람 3개가 존재
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername(AccountDummy.getAccount().getEmail());
        List<Notification> notifications = notificationRepository.
                findByAccountAndCheckedOrderByCreatedDateTimeDesc(loginUser.getAccount(), false);

        //when : 읽지 않은 알람들 읽음 처리
        notificationService.markAsRead(notifications);

        //then : 읽은 알람 6개 존재
        int countOldNotifications = notificationRepository.countByAccountAndChecked(loginUser.getAccount(), true);
        Assertions.assertEquals(countOldNotifications,6);
    }
}
