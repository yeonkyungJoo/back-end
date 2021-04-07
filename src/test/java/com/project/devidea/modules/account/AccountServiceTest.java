package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.account.repository.InterestRepository;
import com.project.devidea.modules.account.repository.MainActivityZoneRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {


    @Mock
    ModelMapper modelMapper;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Mock
    AccountRepository accountRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    InterestRepository interestRepository;
    @Mock
    ZoneRepository zoneRepository;
    @Mock
    MainActivityZoneRepository mainActivityZoneRepository;
    @InjectMocks
    AccountService accountService;

    @Test
    void 회원가입_일반() throws Exception {

//        given


//        when


//        then

    }

    @Test
    void 프로필_가져오기() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        when(accountService.getProfile(loginUser))
                .thenReturn(AccountDummy.getAccountProfileResponseDtoAtMockito());

//        when
        Update.ProfileResponse accountProfileResponseDto =
                accountService.getProfile(loginUser);

//        then
        verify(modelMapper).map(loginUser.getAccount(), Update.ProfileResponse.class);
    }

    @Test
    void 프로필_업데이트() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        Update.ProfileRequest request =
                mock(Update.ProfileRequest.class);
        when(accountRepository.findByEmail(account.getEmail()))
                .thenReturn(Optional.of(account));

//        when
        accountService.updateProfile(loginUser, request);

//        then
        verify(accountRepository).findByEmail(account.getEmail());
        verify(account).updateProfile(request);
    }

    @Test
    void 패스워드_변경() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        Update.PasswordRequest updatePasswordRequestDto = mock(Update.PasswordRequest.class);
        String changePassword = "{bcrypt}1234";
        when(passwordEncoder.encode(any()))
                .thenReturn(changePassword);
        when(accountRepository.findByEmail(account.getEmail()))
                .thenReturn(Optional.of(account));

//        when
        accountService.updatePassword(loginUser, updatePasswordRequestDto);

//        then
        verify(accountRepository).findByEmail(account.getEmail());
        verify(updatePasswordRequestDto).getPassword();
        verify(passwordEncoder).encode(updatePasswordRequestDto.getPassword());
        verify(account).updatePassword(any());
    }

    @Test
    void 관심기술_가져오기() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        when(accountRepository.findByEmailWithInterests(loginUser.getUsername())).thenReturn(account);

//        when
        accountService.getAccountInterests(loginUser);

//        then
        verify(accountRepository).findByEmailWithInterests(loginUser.getUsername());
    }

    @Test
    void 관심기술_수정하기() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        Update.Interest interestsUpdateRequestDto = mock(Update.Interest.class);
        List<Tag> tags = new ArrayList<>();
        Tag tag = mock(Tag.class);
        tags.add(tag);

        when(accountRepository.findByEmailWithInterests(loginUser.getUsername()))
                .thenReturn(account);
        when(tagRepository.findByFirstNameIn(interestsUpdateRequestDto.getInterests()))
                .thenReturn(tags);

//        when
        accountService.updateAccountInterests(loginUser, interestsUpdateRequestDto);

//        then
        verify(accountRepository).findByEmailWithInterests(loginUser.getUsername());
        verify(tagRepository).findByFirstNameIn(interestsUpdateRequestDto.getInterests());
        verify(interestRepository).deleteByAccount(account);
        verify(account).updateInterests(any());
        verify(interestRepository).saveAll(any());
    }

    @Test
    void 활동지역_가져오기() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        when(accountRepository.findByEmailWithMainActivityZones(loginUser.getUsername()))
                .thenReturn(account);

//        when
        accountService.getAccountMainActivityZones(loginUser);

//        then
        verify(accountRepository).findByEmailWithMainActivityZones(loginUser.getUsername());
    }

    @Test
    void 활동지역_수정하기() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        Update.MainActivityZone mainActivityZonesUpdateRequestDto =
                mock(Update.MainActivityZone.class);
        List<Zone> zones = new ArrayList<>();
        Zone zone = mock(Zone.class);
        zones.add(zone);

        when(accountRepository.findByEmailWithMainActivityZones(loginUser.getUsername()))
                .thenReturn(account);
        when(zoneRepository.findByCityInAndProvinceIn(any(), any()))
                .thenReturn(zones);

//        when
        accountService.updateAccountMainActivityZones(loginUser, mainActivityZonesUpdateRequestDto);

//        then
        verify(accountRepository).findByEmailWithMainActivityZones(loginUser.getUsername());
        verify(mainActivityZonesUpdateRequestDto).splitCityAndProvince();
        verify(zoneRepository).findByCityInAndProvinceIn(any(), any());
        verify(mainActivityZoneRepository).deleteByAccount(account);
        verify(account).updateMainActivityZones(any());
        verify(mainActivityZoneRepository).saveAll(any());
    }

    @Test
    void 닉네임_가져오기() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        String nickname = "1234";
        when(loginUser.getAccount()).thenReturn(account);
        when(account.getNickname()).thenReturn(nickname);

//        when
        accountService.getAccountNickname(loginUser);

//        then
        verify(loginUser).getAccount();
        verify(account).getNickname();
    }

    @Test
    void 닉네임_변경하기() throws Exception{

//        given
        LoginUser loginUser = mock(LoginUser.class);
        String username = "username";
        String changeUsername = "changeUsername";
        Account account = mock(Account.class);
        Update.NicknameRequest request = mock(Update.NicknameRequest.class);
        when(loginUser.getUsername()).thenReturn(username);
        when(request.getNickname()).thenReturn(changeUsername);
        when(accountRepository.findByEmail(username))
                .thenReturn(Optional.of(account));

//        when
        accountService.updateAccountNickname(loginUser, request);

//        then
        verify(loginUser).getUsername();
        verify(account).changeNickname(request.getNickname());
    }

    @Test
    void 알림_설정_가져오기() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        Update.Notification response = mock(Update.Notification.class);
        when(loginUser.getAccount()).thenReturn(account);

//        when
        accountService.getAccountNotification(loginUser);

//        then
        verify(loginUser).getAccount();
        verify(modelMapper).map(account, Update.Notification.class);
    }

    @Test
    void 알림_설정_수정하기() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        Update.Notification request = mock(Update.Notification.class);
        when(loginUser.getUsername()).thenReturn("email");
        when(accountRepository.findByEmail(loginUser.getUsername()))
                .thenReturn(Optional.of(account));


//        when
        accountService.updateAccountNotification(loginUser, request);

//        then
        verify(accountRepository).findByEmail(loginUser.getUsername());
        verify(account).updateNotifications(request);
    }

    @Test
    void 회원_탈퇴() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        String email = "email";
        when(loginUser.getUsername()).thenReturn(email);
        when(accountRepository.findByEmail(any())).thenReturn(Optional.of(account));

//        when
        accountService.quit(loginUser);

//        then
        verify(accountRepository).findByEmail(any());
        verify(account).changeToQuit();
    }
}
