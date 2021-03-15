package com.project.devidea.modules.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static com.project.devidea.modules.notification.NotificationController.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class NotificationControllerTest {


    @Autowired
    MockMvc mockMvc;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("읽지 않은 알람들 조회")
    @WithMockUser
    public void getNewNotifications() throws Exception {

        //given : 읽지 않은 알람 2개 존재, 읽은 알람 1개 존재
        Account account = new Account().builder().build();
        accountRepository.save(account);

        Notification notificationA = new Notification().builder()
                .account(account)
                .checked(false)
                .notificationType(NotificationType.STUDY_CREATED)
                .title("스터디생성")
                .message("스터디가 생성되었습니다.")
                .build();

        Notification notificationB = new Notification().builder()
                .account(account)
                .checked(false)
                .notificationType(NotificationType.STUDY_CREATED)
                .title("스터디생성")
                .message("스터디가 생성되었습니다.")
                .build();

        Notification OldNotification = new Notification().builder()
                .account(account)
                .checked(true)
                .notificationType(NotificationType.STUDY_CREATED)
                .title("스터디생성")
                .message("스터디가 생성되었습니다.")
                .build();

        notificationRepository.save(notificationA);
        notificationRepository.save(notificationB);
        notificationRepository.save(OldNotification);

        //when : get, /notifications/new 으로 요청 시 읽지 않은 알람들 조회
        //시큐리티 적용전 임시 적용 파라미터 account
        MvcResult result = mockMvc.perform(get("/notifications/new")
                .param("id", account.getId().toString()))
                .andExpect(status().isOk()).andReturn();

        //then : 조회된 알람의 수 2개
        String strResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseNotifications<List<Notification>> response = objectMapper.readValue(strResult, ResponseNotifications.class);

        Assertions.assertEquals(response.getCount(), 2);
        Assertions.assertEquals(response.getOtherCount(), 1);
    }

    @Test
    @DisplayName("읽은 알람들 조회")
    @WithMockUser
    public void getOldNotifications() throws Exception {

        //given : 읽은 알람 2개 존재, 읽지 않은 알람 1개 존재
        Account account = new Account().builder().build();
        accountRepository.save(account);

        Notification notificationA = new Notification().builder()
                .account(account)
                .checked(true)
                .notificationType(NotificationType.STUDY_CREATED)
                .title("스터디생성")
                .message("스터디가 생성되었습니다.")
                .build();

        Notification notificationB = new Notification().builder()
                .account(account)
                .checked(true)
                .notificationType(NotificationType.STUDY_CREATED)
                .title("스터디생성")
                .message("스터디가 생성되었습니다.")
                .build();

        Notification newNotification = new Notification().builder()
                .account(account)
                .checked(false)
                .notificationType(NotificationType.STUDY_CREATED)
                .title("스터디생성")
                .message("스터디가 생성되었습니다.")
                .build();

        notificationRepository.save(notificationA);
        notificationRepository.save(notificationB);
        notificationRepository.save(newNotification);

        //when : get, /notifications/old 으로 요청 시 읽은 알람들 조회
        //시큐리티 적용전 임시 적용 파라미터 account
        MvcResult result = mockMvc.perform(get("/notifications/old")
                .param("id", account.getId().toString()))
                .andExpect(status().isOk()).andReturn();

        //then : 조회된 알람의 수 2개
        String strResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseNotifications<List<Notification>> response = objectMapper.readValue(strResult, ResponseNotifications.class);

        Assertions.assertEquals(response.getCount(), 2);
        Assertions.assertEquals(response.getOtherCount(), 1);
    }

    @Test
    @DisplayName("읽은 알림 전체 삭제")
    @WithMockUser
    public void deleteAllOldNotifications() throws Exception {
        //given : 읽은 알람 2개 존재
        Account account = new Account().builder().build();
        accountRepository.save(account);

        Notification notificationA = new Notification().builder()
                .account(account)
                .checked(true)
                .notificationType(NotificationType.STUDY_CREATED)
                .title("스터디생성")
                .message("스터디가 생성되었습니다.")
                .build();

        Notification notificationB = new Notification().builder()
                .account(account)
                .checked(true)
                .notificationType(NotificationType.STUDY_CREATED)
                .title("스터디생성")
                .message("스터디가 생성되었습니다.")
                .build();

        notificationRepository.save(notificationA);
        notificationRepository.save(notificationB);

        //when : post, /notifications/delete 으로 요청 시 읽은 알람들 전체 삭제
       mockMvc.perform(post("/notifications/delete")
                .param("id", account.getId().toString())
                .with(csrf()))
                .andExpect(status().isOk());

        //then : 읽은 알람 0개 존재 확인
        int countOldNotifications = notificationRepository.countByAccountAndChecked(account, true);
        Assertions.assertEquals(countOldNotifications,0);
    }

    @Test
    @DisplayName("특정 알림 삭제")
    @WithMockUser
    public void deleteNotification() throws Exception {
        //given : 알림 1개 존재
        Account account = new Account().builder().build();
        accountRepository.save(account);

        Notification notification = new Notification().builder()
                .account(account)
                .checked(true)
                .notificationType(NotificationType.STUDY_CREATED)
                .title("스터디생성")
                .message("스터디가 생성되었습니다.")
                .build();

        notificationRepository.save(notification);

        //when : post, /notifications/delete 으로 요청 시 읽은 알람들 전체 삭제
        mockMvc.perform(post("/notifications/"+notification.getId()+"/delete")
                .with(csrf()))
                .andExpect(status().isOk());

        //then : 알림 삭제 확인
        Optional<Notification> findNotification = notificationRepository.findById(notification.getId());
        Assertions.assertEquals(findNotification.isPresent(), false);
    }
}
