package com.project.devidea.modules.notification;

import com.project.devidea.modules.account.AccountDummy;

import java.util.ArrayList;
import java.util.List;

public class NotificationDummy {

    public static List<Notification> getOldNotifications() {
        List<Notification> oldNotifications = new ArrayList<>();

        Notification notificationA = new Notification().builder()
                .checked(true)
                .notificationType(NotificationType.STUDY)
                .title("스터디생성")
                .message("읽은 알람1")
                .build();

        Notification notificationB = new Notification().builder()
                .checked(true)
                .notificationType(NotificationType.STUDY)
                .title("스터디생성")
                .message("읽은 알람2")
                .build();

        Notification notificationC = new Notification().builder()
                .checked(true)
                .notificationType(NotificationType.STUDY)
                .title("스터디생성")
                .message("읽은 알람3")
                .build();

        oldNotifications.add(notificationA);
        oldNotifications.add(notificationB);
        oldNotifications.add(notificationC);

        return oldNotifications;
    }

    public static List<Notification> getNewNotifications() {
        List<Notification> newNotifications = new ArrayList<>();

        Notification notificationA = new Notification().builder()
                .account(AccountDummy.getAccount())
                .checked(false)
                .notificationType(NotificationType.STUDY)
                .title("스터디생성")
                .message("읽지 않은 알람1")
                .build();

        Notification notificationB = new Notification().builder()
                .account(AccountDummy.getAccount())
                .checked(false)
                .notificationType(NotificationType.STUDY)
                .title("스터디생성")
                .message("읽지 않은 알람2")
                .build();

        Notification notificationC = new Notification().builder()
                .account(AccountDummy.getAccount())
                .checked(false)
                .notificationType(NotificationType.STUDY)
                .title("스터디생성")
                .message("읽지 않은 알람3")
                .build();

        newNotifications.add(notificationA);
        newNotifications.add(notificationB);
        newNotifications.add(notificationC);

        return newNotifications;
    }
}
