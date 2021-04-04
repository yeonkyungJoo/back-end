package com.project.devidea.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.modules.account.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@Slf4j
class AccountInfoControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Test
    @WithUserDetails(value = "test@test.com")
    void 유저의_프로필_가져오기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");

//        when
        MvcResult result = mockMvc.perform(get("/account/settings/profile")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

//        then
        Account account = loginUser.getAccount();
        String str = result.getResponse().getContentAsString();
        Map<String, String> map = objectMapper.readValue(str, Map.class);
        assertAll(() -> assertEquals(account.getEmail(), "test@test.com"),
                () -> assertEquals(account.getNickname(), "테스트_회원"));
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    void 유저_프로필_수정() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        AccountProfileUpdateRequestDto accountProfileUpdateRequestDto =
                AccountDummy.getAccountProfileUpdateRequestDto();

//        when
        mockMvc.perform(patch("/account/settings/profile")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountProfileUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());

//        then
        LoginUser findUser = (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        Account account = findUser.getAccount();
        assertAll(
                () -> assertEquals(account.getBio(), accountProfileUpdateRequestDto.getBio()),
                () -> assertEquals(account.getProfileImage(), accountProfileUpdateRequestDto.getProfileImage()),
                () -> assertEquals(account.getUrl(), accountProfileUpdateRequestDto.getUrl()),
                () -> assertEquals(account.getGender(), accountProfileUpdateRequestDto.getGender()),
                () -> assertEquals(account.getJob(), accountProfileUpdateRequestDto.getJob()),
                () -> assertEquals(account.getCareerYears(), accountProfileUpdateRequestDto.getCareerYears()),
                () -> assertEquals(account.getTechStacks(),
                        StringUtils.join(accountProfileUpdateRequestDto.getTechStacks(), '/')));
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    void 유저_패스워드_수정() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        UpdatePasswordRequestDto updatePasswordRequestDto =
                AccountDummy.getUpdatePassowordRequestDto();

//        when
        mockMvc.perform(patch("/account/settings/password")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePasswordRequestDto)))
                .andDo(print());

//        then, 바뀐 비밀번호로 로그인할 경우 Bearer 토큰을 주는지?
        MockHttpServletResponse response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LoginRequestDto.builder()
                        .email("test@test.com")
                        .password(updatePasswordRequestDto.getPassword()).build())))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization")).andReturn().getResponse();

        String jwtToken = response.getHeader("Authorization").substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        assertEquals(username, "test@test.com");
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    void 관심기술_가져오기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");

//        when, then
        mockMvc.perform(get("/account/settings/interests")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    void 관심기술_수정하기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        InterestsUpdateRequestDto interestsUpdateRequestDto = AccountDummy.getInterestsUpdateRequestDto();

//        when
        mockMvc.perform(patch("/account/settings/interests")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(interestsUpdateRequestDto)))
                .andExpect(status().isOk());

//        then, 조회 시 수정한 값이 제대로 나오는지 확인하기
        mockMvc.perform(get("/account/settings/interests")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tagNames.length()", is(3)))
                .andDo(print());
    }

    @Test
    @WithUserDetails("test@test.com")
    void 활동지역_가져오기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");

//        when, then
        mockMvc.perform(get("/account/settings/mainactivityzones")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails("test@test.com")
    void 활동지역_수정하기() throws Exception {

//        given
        LoginUser loginUser = (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        MainActivityZonesUpdateRequestDto mainActivityZonesUpdateRequestDto =
                AccountDummy.getMainActivityZonesUpdateRequestDto();

//        when
        mockMvc.perform(patch("/account/settings/mainactivityzones")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mainActivityZonesUpdateRequestDto)))
                .andExpect(status().isOk());

//        then
        mockMvc.perform(get("/account/settings/mainactivityzones")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.zoneNames.length()", is(2)));
    }

    @Test
    @WithUserDetails("test@test.com")
    void 닉네임_가져오기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");

//        when, then
        mockMvc.perform(get("/account/settings/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser)))
                .andDo(print())
                .andExpect(jsonPath("$.data.nickname", is(loginUser.getNickName())));
    }

    @Test
    @WithUserDetails("test@test.com")
    void 닉네임_변경하기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        ChangeNicknameRequest request = ChangeNicknameRequest.builder().nickname("변경할닉네임").build();

//        when
        mockMvc.perform(patch("/account/settings/nickname")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());

//        then
        LoginUser findUser = (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        assertThat(findUser.getNickName()).isEqualTo(request.getNickname());
    }

    @Test
    @WithUserDetails("test@test.com")
    void 알림_설정_가져오기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");

//        when, then
        mockMvc.perform(get("/account/settings/notifications")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(6)))
                .andDo(print());
    }

    @Test
    @WithUserDetails("test@test.com")
    void 알림_설정_수정하기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        NotificationRequestResponse request = NotificationRequestResponse.builder()
                .receiveTechNewsNotification(true).receiveStudyNotification(true).receiveRecruitingNotification(true)
                .receiveNotification(true).receiveMentoringNotification(true).receiveEmail(true).build();

//        when
        mockMvc.perform(patch("/account/settings/notifications")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

//        then
        mockMvc.perform(get("/account/settings/notifications")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(6)))
                .andExpect(jsonPath("$.data.receiveEmail", is(true)))
                .andExpect(jsonPath("$.data.receiveNotification", is(true)))
                .andExpect(jsonPath("$.data.receiveTechNewsNotification", is(true)))
                .andExpect(jsonPath("$.data.receiveMentoringNotification", is(true)))
                .andExpect(jsonPath("$.data.receiveStudyNotification", is(true)))
                .andExpect(jsonPath("$.data.receiveRecruitingNotification", is(true)));
    }

//    ValidationTest ===================================================================================================
    @Test
    @WithUserDetails("test@test.com")
    void 패스워드_변경_유효성_검사_1_패스워드와_패스워드확인_공백체크() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        UpdatePasswordRequestDto request = AccountDummy.getBlankPasswordRequest();


//        when, then
//        불일치, 공백금지
        mockMvc.perform(patch("/account/settings/password")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(3)));
    }

    @Test
    @WithUserDetails("test@test.com")
    void 패스워드_변경_유효성_검사_2_패스워드와_패스워드확인값_불일치() throws Exception {

    //        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        UpdatePasswordRequestDto updatePasswordRequestDto = AccountDummy.getNotEqualsPasswordAndPasswordConfirm();


    //        when, then
        mockMvc.perform(patch("/account/settings/password")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePasswordRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(1)));
    }

    @Test
    @WithUserDetails("test@test.com")
    void 닉네임_변경_유효성_검사_1_Size() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        ChangeNicknameRequest request = ChangeNicknameRequest.builder().nickname("범석").build();

//        when, then
//        Valid 3자 미만의 경우
        mockMvc.perform(patch("/account/settings/nickname")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(1)));
    }

    @Test
    @WithUserDetails("test@test.com")
    void 닉네임_변경_유효성_검사_2_Duplicate() throws Exception{

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        ChangeNicknameRequest request = ChangeNicknameRequest.builder().nickname("DevIdea").build();

//        when, then
//        중복 금지
        mockMvc.perform(patch("/account/settings/nickname")
                .header("Authorization", "Bearer " + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.length()", is(1)));
    }
}