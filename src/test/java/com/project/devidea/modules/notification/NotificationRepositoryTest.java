package com.project.devidea.modules.notification;

import com.project.devidea.infra.TestConfig;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountDummy;
import com.project.devidea.modules.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
public class NotificationRepositoryTest {


    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AccountRepository accountRepository;

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

    @DisplayName("읽은 알람 갯수")
    @Test
    public void countByAccountAndChecked_old() {
        //given : 읽은 알람 3개가 존재
        Account account = accountRepository.findByEmail(AccountDummy.getAccount().getEmail()).get();

        //when : 읽은 알람의 갯수 조회
        int countOldNotifications = notificationRepository.countByAccountAndChecked(account, true);

        //then : 읽은 알람의 갯수 조회 확인
        assertEquals(countOldNotifications,3);
    }

    @DisplayName("읽지 않은 알람 갯수")
    @Test
    public void countByAccountAndChecked_new() {
        //given : 읽지 않은 알람 3개가 존재
        Account account = accountRepository.findByEmail(AccountDummy.getAccount().getEmail()).get();

        //when : 읽지 않은 알람의 갯수 조회
        int countOldNotifications = notificationRepository.countByAccountAndChecked(account, false);

        //then : 읽지 않은 알람의 갯수 조회 확인
        assertEquals(countOldNotifications,3);
    }

    @DisplayName("읽은 알람들 조회")
    @Test
    public void findByAccountAndCheckedOrderByCreatedDateTimeDesc_old() {
        //given : 읽은 알람 3개가 존재
        Account account = accountRepository.findByEmail(AccountDummy.getAccount().getEmail()).get();

        //when : 읽은 알람들 조회
        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, true);

        //then : 읽은 알람 3개 조회 확인
        String[] messages = new String[] {"읽은 알람1","읽은 알람2","읽은 알람3"};
        notifications.forEach(notification -> assertThat(messages).contains(
                        notification.getMessage()));
    }

    @DisplayName("읽지 않은 알람들 조회")
    @Test
    public void findByAccountAndCheckedOrderByCreatedDateTimeDesc_new() {
        //given : 읽지 않은 알람 3개가 존재
        Account account = accountRepository.findByEmail(AccountDummy.getAccount().getEmail()).get();

        //when : 읽지 않은 알람들 조회
        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, false);

        //then : 읽지 않은 알람 3개 조회 확인
        String[] messages = new String[] {"읽지 않은 알람1","읽지 않은 알람2","읽지 않은 알람3"};
        notifications.forEach(notification -> assertThat(messages).contains(
                notification.getMessage()));
    }

    @DisplayName("읽은 알람 전체 삭제")
    @Test
    public void deleteByAccountAndChecked() {
        //given : 읽은 알람 3개가 존재
        Account account = accountRepository.findByEmail(AccountDummy.getAccount().getEmail()).get();

        //when : 읽은 알람들 조회
        notificationRepository.deleteByAccountAndChecked(account, true);

        //then : 읽은 알람 0개 조회 확인
        int countOldNotifications = notificationRepository.countByAccountAndChecked(account, true);
        assertEquals(countOldNotifications,0);
    }
}
