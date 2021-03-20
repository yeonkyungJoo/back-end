//package com.project.devidea.modules.notification;
//
//import com.project.devidea.infra.MockMvcTest;
//import com.project.devidea.modules.account.Account;
//import com.project.devidea.modules.account.repository.AccountRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@MockMvcTest
//public class NotificationServiceTest {
//
//    @Autowired
//    NotificationRepository notificationRepository;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Autowired
//    NotificationService notificationService;
//
//    @DisplayName("알람 읽음 처리")
//    @Test
//    public void markAsRead() throws Exception {
//
//        //given : 읽지 않은 알림 2개, 읽은 알람 1개가 존재
//        Account account = new Account().builder().build();
//        accountRepository.save(account);
//
//        Notification newNotificationA = new Notification().builder()
//                .account(account)
//                .checked(false)
//                .notificationType(NotificationType.STUDY_CREATED)
//                .title("스터디생성")
//                .message("스터디가 생성되었습니다.")
//                .build();
//
//        Notification newNotificationB = new Notification().builder()
//                .account(account)
//                .checked(false)
//                .notificationType(NotificationType.STUDY_CREATED)
//                .title("스터디생성")
//                .message("스터디가 생성되었습니다.")
//                .build();
//
//        Notification oldNotification = new Notification().builder()
//                .account(account)
//                .checked(true)
//                .notificationType(NotificationType.STUDY_CREATED)
//                .title("스터디생성")
//                .message("스터디가 생성되었습니다.")
//                .build();
//
//        notificationRepository.save(newNotificationA);
//        notificationRepository.save(newNotificationB);
//        notificationRepository.save(oldNotification);
//
//        //when : 읽지 않은 알람들 읽음 처리
//        List<Notification> notifications = new ArrayList<>();
//        notifications.add(newNotificationA);
//        notifications.add(newNotificationB);
//        notificationService.markAsRead(notifications);
//
//        //then : 읽은 알람 3개 존재
//        int countOldNotifications = notificationRepository.countByAccountAndChecked(account, true);
//        Assertions.assertEquals(countOldNotifications,3);
//    }
//}
