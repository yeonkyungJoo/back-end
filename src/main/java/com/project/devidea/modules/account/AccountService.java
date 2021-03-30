package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.infra.config.security.oauth.OAuthServiceInterface;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.account.repository.InterestRepository;
import com.project.devidea.modules.account.repository.MainActivityZoneRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements OAuthServiceInterface {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper;
    private final ZoneRepository zoneRepository;
    private final TagRepository tagRepository;
    private final InterestRepository interestRepository;
    private final MainActivityZoneRepository mainActivityZoneRepository;
    private final String OAUTH_PASSWORD = "dev_idea_oauth_password";

    //    회원가입
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        Account savedAccount = accountRepository.save(Account.builder()
                .email(signUpRequestDto.getEmail())
                .name(signUpRequestDto.getName())
                .password("{bcrypt}" + passwordEncoder.encode(signUpRequestDto.getPassword()))
                .roles("ROLE_USER")
                .joinedAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(null)
                .gender(signUpRequestDto.getGender())
                .profileImage(null)
                .build());

        return modelMapper.map(savedAccount, SignUpResponseDto.class);
    }

//    OAuth 회원가입
    @Override
    public SignUpResponseDto signUpOAuth(SignUpOAuthRequestDto signUpOAuthRequestDto) {
        Account savedAccount = accountRepository.save(Account.builder()
                .email(signUpOAuthRequestDto.getEmail())
                .name(signUpOAuthRequestDto.getName())
                .password("{bcrypt}" + passwordEncoder.encode(OAUTH_PASSWORD))
                .roles("ROLE_USER")
                .joinedAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(signUpOAuthRequestDto.getProvider())
                .gender(null)
                .profileImage(signUpOAuthRequestDto.getProfileImage())
                .build());

        return modelMapper.map(savedAccount, SignUpResponseDto.class);
    }

    public Map<String, String> login(LoginRequestDto requestDto) throws Exception {
        authenticate(requestDto.getEmail(), requestDto.getPassword());

        String jwtToken = jwtTokenUtil.generateToken(requestDto.getEmail());
        return jwtTokenUtil.createTokenMap(jwtToken);
    }

//    OAuth 로그인
    @Override
    public Map<String, String> loginOAuth(LoginOAuthRequestDto loginOAuthRequestDto) throws Exception {
        LoginRequestDto loginRequestDto =
                LoginRequestDto.builder().email(loginOAuthRequestDto.getEmail()).password(OAUTH_PASSWORD).build();
        return login(loginRequestDto);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("회원의 아이디와 비밀번호가 일치하지 않습니다.", e);
        }
    }

    public void saveSignUpDetail(LoginUser loginUser, SignUpDetailRequestDto req) {

        Account account = accountRepository.findByEmailWithMainActivityZoneAndInterests(loginUser.getUsername());

//        활동지역(mainActivityZones)
        Map<String, List<String>> cityProvince = req.splitCitiesAndProvinces();
        List<Zone> zones = zoneRepository
                .findByCityInAndProvinceIn(cityProvince.get("city"), cityProvince.get("province"));
        Set<MainActivityZone> mainActivityZones = getMainActivityZones(account, zones);

//        관심기술(Interest)
        List<Tag> tags = tagRepository.findByFirstNameIn(req.getInterests());
        Set<Interest> interests = getInterests(account, tags);

//        연관관계 설정하기
        account.saveSignUpDetail(req, mainActivityZones, interests);

//        매핑 테이블 데이터들 save
        mainActivityZoneRepository.saveAll(mainActivityZones);
        interestRepository.saveAll(interests);
    }

    private Set<MainActivityZone> getMainActivityZones(Account account, List<Zone> zones) {
        Set<MainActivityZone> mainActivityZones = new HashSet<>();
        zones.forEach(zone -> {
            mainActivityZones.add(MainActivityZone.builder().account(account).zone(zone).build());
        });
        return mainActivityZones;
    }

    private Set<Interest> getInterests(Account account, List<Tag> tags) {
        Set<Interest> interests = new HashSet<>();
        tags.forEach(tag -> {
            interests.add(Interest.builder().tag(tag).account(account).build());
        });
        return interests;
    }

    @Transactional(readOnly = true)
    public AccountProfileResponseDto getProfile(LoginUser loginUser) {
        return modelMapper.map(loginUser.getAccount(), AccountProfileResponseDto.class);
    }

    public void updateProfile(LoginUser loginUser,
                              AccountProfileUpdateRequestDto accountProfileUpdateRequestDto) {
        Account account = accountRepository.findByEmail(loginUser.getUsername()).orElseThrow();
        account.updateProfile(accountProfileUpdateRequestDto);
    }

    public void updatePassword(LoginUser loginUser,
                               UpdatePasswordRequestDto updatePasswordRequestDto) {
        Account account = accountRepository.findByEmail(loginUser.getUsername()).orElseThrow();
        account.updatePassword("{bcrypt}" + passwordEncoder.encode(updatePasswordRequestDto.getPassword()));
    }

    public InterestsResponseDto getAccountInterests(LoginUser loginUser) {
        Set<Interest> interests =
                accountRepository.findByEmailWithInterests(loginUser.getUsername()).getInterests();
        return InterestsResponseDto.builder().tagNames(
                interests.stream()
                        .map(interest -> interest.getTag().getFirstName())
                        .collect(Collectors.toList())).build();
    }

    public void updateAccountInterests(LoginUser loginUser,
                                InterestsUpdateRequestDto interestsUpdateRequestDto) {
        Account account = accountRepository.findByEmailWithInterests(loginUser.getUsername());
        List<Tag> tags = tagRepository.findByFirstNameIn(interestsUpdateRequestDto.getInterests());
        Set<Interest> interests = getInterests(account, tags);

//        기존에 있던 정보들 삭제
        interestRepository.deleteByAccount(account);

//        연관관계 메서드
        account.updateInterests(interests);
        interestRepository.saveAll(interests);
    }

    public MainActivityZonesResponseDto getAccountMainActivityZones(LoginUser loginUser) {
        Set<MainActivityZone> mainActivityZones =
                accountRepository.findByEmailWithMainActivityZones(loginUser.getUsername()).getMainActivityZones();
        return MainActivityZonesResponseDto.builder()
                .zoneNames(
                        mainActivityZones.stream().map(zone -> zone.getZone().toString()).collect(Collectors.toList()))
                .build();
    }

    public void updateAccountMainActivityZones(LoginUser loginUser,
                                               MainActivityZonesUpdateRequestDto mainActivityZonesUpdateRequestDto) {
        Account account = accountRepository.findByEmailWithMainActivityZones(loginUser.getUsername());
        Map<String, List<String>> map = mainActivityZonesUpdateRequestDto.splitCityAndProvince();
        List<Zone> zones
                = zoneRepository.findByCityInAndProvinceIn(map.get("city"), map.get("province"));
        Set<MainActivityZone> mainActivityZones = getMainActivityZones(account, zones);

//        기존에 있던 정보들 삭제
        mainActivityZoneRepository.deleteByAccount(account);

//        연관관계 메서드
        account.updateMainActivityZones(mainActivityZones);
        mainActivityZoneRepository.saveAll(mainActivityZones);
    }
}
