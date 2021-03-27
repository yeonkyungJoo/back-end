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
import org.springframework.security.crypto.password.PasswordEncoder;

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

//    Account account;
//    SignUpRequestDto request;
//    SignUpResponseDto response;

//    @BeforeEach
//    void init() {
//        request = SignUpRequestDto.builder()
//                .email("ko@naver.com")
//                .name("고범석")
//                .password("1234")
//                .passwordConfirm("1234")
//                .gender("male")
//                .build();
//
//        account = Account.builder()
//                .id(1L)
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .name(request.getName())
//                .nickname(request.getNickname())
//                .roles("ROLE_USER")
//                .joinedAt(LocalDateTime.now())
//                .gender(request.getGender())
//                .build();
//
//        response = SignUpResponseDto.builder()
//                .id(account.getId().toString())
//                .email(account.getEmail())
//                .gender(account.getGender())
//                .name(account.getName())
//                .nickname(account.getNickname())
//                .build();
//    }
//
//    @Test
//    @DisplayName("회원가입")
//    void save() throws Exception {
//
////        given
//        when(accountRepository.save(any(Account.class)))
//                .thenReturn(account);
//        when(modelMapper.map(account, SignUpResponseDto.class))
//                .thenReturn(response);
//
////        when
//        SignUpResponseDto responseDto = accountService.signUp(request);
//
////        then
//        verify(modelMapper).map(account, SignUpResponseDto.class);
//        assertAll(
//                () -> assertNotNull(responseDto),
//                () -> assertEquals(responseDto.getId(), account.getId().toString()),
//                () -> assertEquals(responseDto.getEmail(), account.getEmail()),
//                () -> assertEquals(responseDto.getNickname(), account.getNickname()),
//                () -> assertEquals(responseDto.getName(), account.getName()),
//                () -> assertEquals(responseDto.getGender(), account.getGender())
//        );
//    }
//
//    @Test
//    @DisplayName("로그인 시 jwt 코드 반환")
//    void loginAndConfirm() throws Exception {
//
////        given
//        accountRepository.save(Account.builder().id(1L).email("ko@naver.com")
//                .password(passwordEncoder.encode("123412341234")).nickname("고범석").name("고범석").build());
//        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
//                .email("ko@naver.com")
//                .password("123412341234")
//                .build();
//        when(jwtTokenUtil.generateToken(any(String.class)))
//                .thenReturn("jwttoken");
//
////        when
//        Map<String, String> result = accountService.login(loginRequestDto);
//
////        then
//        verify(jwtTokenUtil).generateToken(any(String.class));
//        assertEquals(result.get("token"), "Bearer jwttoken");
//    }

    @Test
    void 프로필_가져오기() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        when(accountService.getProfile(loginUser))
                .thenReturn(AccountDummy.getAccountProfileResponseDtoAtMockito());

//        when
        AccountProfileResponseDto accountProfileResponseDto =
                accountService.getProfile(loginUser);

//        then
        verify(modelMapper).map(loginUser.getAccount(), AccountProfileResponseDto.class);
    }

    @Test
    void 프로필_업데이트() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        AccountProfileUpdateRequestDto accountProfileUpdateRequestDto =
                mock(AccountProfileUpdateRequestDto.class);
        when(accountRepository.findByEmail(account.getEmail()))
                .thenReturn(Optional.of(account));

//        when
        accountService.updateProfile(loginUser, accountProfileUpdateRequestDto);

//        then
        verify(accountRepository).findByEmail(account.getEmail());
        verify(account).updateProfile(accountProfileUpdateRequestDto);
    }

    @Test
    void 패스워드_변경() throws Exception {

//        given
        LoginUser loginUser = mock(LoginUser.class);
        Account account = mock(Account.class);
        UpdatePasswordRequestDto updatePasswordRequestDto = mock(UpdatePasswordRequestDto.class);
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
        InterestsUpdateRequestDto interestsUpdateRequestDto = mock(InterestsUpdateRequestDto.class);
        List<Tag> tags = new ArrayList<>();
        Tag tag = mock(Tag.class);
        tags.add(tag);

        when(accountRepository.findByEmailWithInterests(loginUser.getUsername()))
                .thenReturn(account);
        when(tagRepository.findByFirstNameIn(interestsUpdateRequestDto.getInterests()))
                .thenReturn(tags);
//        when(interestRepository.deleteByAccount(account)).thenReturn(anyInt());

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
        MainActivityZonesUpdateRequestDto mainActivityZonesUpdateRequestDto =
                mock(MainActivityZonesUpdateRequestDto.class);
        List<Zone> zones = new ArrayList<>();
        Zone zone = mock(Zone.class);
        zones.add(zone);

        when(accountRepository.findByEmailWithMainActivityZones(loginUser.getUsername()))
                .thenReturn(account);
        when(zoneRepository.findByCityInAndProvinceIn(any(), any()))
                .thenReturn(zones);
//        when(mainActivityZoneRepository.deleteByAccount(account)).thenReturn(anyInt());

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
}
