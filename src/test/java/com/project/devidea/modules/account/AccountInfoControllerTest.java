package com.project.devidea.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.account.repository.InterestRepository;
import com.project.devidea.modules.account.repository.MainActivityZoneRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Autowired
    MainActivityZoneRepository mainActivityZoneRepository;
    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    InterestRepository interestRepository;
    @Autowired
    TagRepository tagRepository;

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
    @Transactional
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
        Account account = loginUser.getAccount();
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
    @Transactional
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
    @Transactional
    void 관심기술_가져오기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");

//        when, then
        mockMvc.perform(get("/account/settings/interests")
                .header("Authorization", "Bearer" + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    @Transactional
    void 관심기술_수정하기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
        addInterests(loginUser);
        InterestsUpdateRequestDto interestsUpdateRequestDto = AccountDummy.getInterestsUpdateRequestDto();

//        when
        mockMvc.perform(patch("/account/settings/interests")
                .header("Authorization", "Bearer" + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(interestsUpdateRequestDto)))
                .andExpect(status().isOk())
                .andDo(print());

//        then
        Set<Interest> interests = loginUser.getAccount().getInterests();
        List<String> names =
                interests.stream().map(interest -> interest.getTag().getFirstName()).collect(Collectors.toList());
        List<Interest> findInter = interestRepository.findByAccount(loginUser.getAccount());

        assertThat(names).contains("Vue.js", "java", "docker");
        assertThat(findInter.size()).isEqualTo(3);
    }

    private void addInterests(LoginUser loginUser) {
        Account account = loginUser.getAccount();
        List<Tag> tags = tagRepository.findByFirstNameIn(Arrays.asList("java", "spring"));

        Set<Interest> interests = tags.stream().map(tag -> Interest.builder()
                .account(account).tag(tag).build()).collect(Collectors.toSet());

        account.getInterests().addAll(interests);
        interestRepository.saveAll(interests);
    }

    @Test
    @WithUserDetails("test@test.com")
    @Transactional
    void 활동지역_가져오기() throws Exception {

//        given
        LoginUser loginUser =
                (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");

//        when, then
        mockMvc.perform(get("/account/settings/mainactivityzones")
                .header("Authorization", "Bearer" + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails("test@test.com")
    @Transactional
    void 활동지역_수정하기() throws Exception {

//        given
        LoginUser loginUser = (LoginUser) customUserDetailService.loadUserByUsername("test@test.com");
//        기존 유저에 활동지역 저장해두기
        addMainActivityZones(loginUser);
        MainActivityZonesUpdateRequestDto mainActivityZonesUpdateRequestDto =
                AccountDummy.getMainActivityZonesUpdateRequestDto();

//        when
        mockMvc.perform(patch("/account/settings/mainactivityzones")
                .header("Authorization", "Bearer" + jwtTokenUtil.generateToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mainActivityZonesUpdateRequestDto)))
                .andDo(print());

//        then
        Set<MainActivityZone> mainActivityZones = loginUser.getAccount().getMainActivityZones();
        List<String> citiesAndProvinces = mainActivityZones.stream()
                .map(mainActivityZone -> mainActivityZone.getZone().toString())
                .collect(Collectors.toList());
        List<MainActivityZone> findMains = mainActivityZoneRepository.findByAccount(loginUser.getAccount());

        assertThat(citiesAndProvinces).contains("서울특별시/중랑구", "서울특별시/노원구");
        assertThat(findMains.size()).isEqualTo(2);
    }

    private void addMainActivityZones(LoginUser loginUser) {
        Account account = loginUser.getAccount();
        List<Zone> zones =
                zoneRepository.findByCityInAndProvinceIn(Arrays.asList("서울특별시", "서울특별시"),
                        Arrays.asList("광진구", "송파구"));

        Set<MainActivityZone> mainActivityZones = zones.stream().map(zone -> MainActivityZone.builder()
                .account(account).zone(zone).build()).collect(Collectors.toSet());

        account.getMainActivityZones().addAll(mainActivityZones);
        mainActivityZoneRepository.saveAll(mainActivityZones);
    }
}