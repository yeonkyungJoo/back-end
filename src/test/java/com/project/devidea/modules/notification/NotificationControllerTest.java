package com.project.devidea.modules.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.infra.WithAccount;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.infra.error.exception.BusinessException;
import com.project.devidea.infra.error.exception.EntityNotFoundException;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountDummy;
import com.project.devidea.modules.account.repository.AccountRepository;


import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.project.devidea.modules.notification.NotificationController.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class NotificationControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

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

    private Class<? extends Exception> getApiResultExceptionClass(MvcResult result) {
        return Objects.requireNonNull(result.getResolvedException()).getClass();
    }

    @Test
    @DisplayName("읽지 않은 알람들 조회")
    public void getNewNotifications() throws Exception {

        //given : 읽지 않은 알람 3개 존재, 읽은 알람 3개 존재
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername(AccountDummy.getAccount().getEmail());

        //when : get, /notifications/new 으로 요청 시 읽지 않은 알람들 조회
        MvcResult result = mockMvc.perform(get("/notifications/new")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser)))
                .andExpect(status().isOk()).andReturn();

        //then : 읽은 않은 알람 3개 조회, 읽은 알람의 갯수 3개 조회
        String strResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseNotifications<List<Notification>> response = objectMapper.readValue(strResult,
                new TypeReference<ResponseNotifications<List<Notification>>>() {});

        String[] messages = new String[] {"읽지 않은 알람1","읽지 않은 알람2","읽지 않은 알람3"};
        assertAll(
                () -> assertEquals(response.getCount(), 3),
                () -> assertEquals(response.getOtherCount(), 3),
                () -> assertNotNull(response.getNotifications()),
                () -> response.getNotifications().forEach(notification ->
                        assertThat(messages).contains(
                                notification.getMessage()))
        );
    }

    @Test
    @DisplayName("읽은 알람들 조회")
    public void getOldNotifications() throws Exception {

        //given : 읽지 않은 알람 3개 존재, 읽은 알람 3개 존재
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername(AccountDummy.getAccount().getEmail());

        //when : get, /notifications/old 으로 요청 시 읽지 않은 알람들 조회
        MvcResult result = mockMvc.perform(get("/notifications/old")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser)))
                .andExpect(status().isOk()).andReturn();

        //then : 읽은 알람 3개 조회, 읽지 않은 알람의 갯수 3개 조회
        String strResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseNotifications<List<Notification>> response = objectMapper.readValue(strResult,
                new TypeReference<ResponseNotifications<List<Notification>>>() {});

        String[] messages = new String[] {"읽은 알람1","읽은 알람2","읽은 알람3"};
        assertAll(
                () -> assertEquals(response.getCount(), 3),
                () -> assertEquals(response.getOtherCount(), 3),
                () -> assertNotNull(response.getNotifications()),
                () -> response.getNotifications().forEach(notification ->
                        assertThat(messages).contains(
                                notification.getMessage()))
        );
    }

    @Test
    @DisplayName("읽은 알림 전체 삭제")
    public void deleteAllOldNotifications() throws Exception {
        //given : 읽은 알람 3개 존재
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername(AccountDummy.getAccount().getEmail());

        //when : post, /notifications/delete 으로 요청 시 읽은 알람들 전체 삭제
       mockMvc.perform(post("/notifications/delete")
               .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser)))
                .andExpect(status().isOk());

       //then : 읽은 알람 0개 존재 확인
        int countOldNotifications = notificationRepository.countByAccountAndChecked(loginUser.getAccount(), true);
        assertEquals(countOldNotifications,0);
    }

    @Test
    @DisplayName("특정 알림 삭제")
    public void deleteNotification() throws Exception {
        //given : 알림 1개 존재
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername(AccountDummy.getAccount().getEmail());
        List<Notification> notifications = notificationRepository.
                findByAccountAndCheckedOrderByCreatedDateTimeDesc(loginUser.getAccount(), true);
        Notification notification = notifications.get(0);

       //when : post, /notifications/delete 으로 요청 시 읽은 알람들 전체 삭제
        mockMvc.perform(post("/notifications/"+notification.getId()+"/delete")
                .with(csrf()))
                .andExpect(status().isOk());

        //then : 알림 삭제 확인
        Optional<Notification> findNotification = notificationRepository.findById(notification.getId());
        assertEquals(findNotification.isPresent(), false);
    }

    @Test
    @DisplayName("특정 알림 삭제 실패_EntityNotFound")
    public void deleteNotification_fail_EntityNotFound() throws Exception {

        //given : 존재하지 않는 알림 아이디 1개
        Long notExistNotificationId = 9999L;

        //when : post, /notifications/delete 으로 요청 시 읽은 알람들 전체 삭제
        //then : 존재하지 않는 알림 아이디를 통해 알림을 삭제하려고 하여 EntityNotFoundException 발생
        mockMvc.perform(post("/notifications/" + notExistNotificationId + "/delete")
                .with(csrf()))
                .andExpect(status().is4xxClientError())
                .andExpect(result ->
                        assertThat(getApiResultExceptionClass(result)).isEqualTo(EntityNotFoundException.class)
                );
    }
}
