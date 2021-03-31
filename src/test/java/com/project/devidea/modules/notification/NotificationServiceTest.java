package com.project.devidea.modules.notification;

import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountDummy;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


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

    @Autowired
    StudyRepository studyRepository;

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
    public void markAsRead(){

        //given : 읽지 않은 알림 3개, 읽은 알람 3개가 존재
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername(AccountDummy.getAccount().getEmail());
        List<Notification> notifications = notificationRepository.
                findByAccountAndCheckedOrderByCreatedDateTimeDesc(loginUser.getAccount(), false);

        //when : 읽지 않은 알람들 읽음 처리
        notificationService.markAsRead(notifications);

        //then : 읽은 알람 6개 존재
        int countOldNotifications = notificationRepository.countByAccountAndChecked(loginUser.getAccount(), true);
        assertEquals(countOldNotifications,6);
    }

    @DisplayName("알람 생성")
    @Test
    public void createNotification() {

        //given : 생성된 스터디 1개 존재
        Account account = accountRepository.findByEmail(AccountDummy.getAccount().getEmail()).get();
        Study study = Study.builder()
                .title("스터디제목")
                .maxCount(3)
                .build();
        studyRepository.save(study);

        //when : 스터디 관련 알람이 생성되었을 때
        Long createdId = notificationService.createNotification(NotificationType.STUDY, study.getId(), account,
                "스터디 생성", "관심 스터디가 생성되었습니다.");

        //then : 알람 생성 확인
        Optional<Notification> findNotification = notificationRepository.findById(createdId);

        assertThat(findNotification.isPresent());
        Notification notification = findNotification.get();

        assertAll(
                () -> assertThat(notification.getTitle()).isEqualTo("스터디 생성"),
                () -> assertThat(notification.getMessage()).isEqualTo("관심 스터디가 생성되었습니다."),
                () -> assertThat(notification.getNotificationType()).isEqualTo(NotificationType.STUDY),
                () -> assertThat(notification.getAccount()).isEqualTo(account),
                () -> assertThat(notification.getLink()).isEqualTo("/" + NotificationType.STUDY + "/" + study.getId())
        );
    }
}
