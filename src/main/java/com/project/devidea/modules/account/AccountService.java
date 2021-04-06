package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.config.security.SHA256;
import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.infra.config.security.oauth.OAuthService;
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

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements OAuthService {

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

    public SignUp.Response signUp(SignUp.CommonRequest signUpRequestDto) {
        Account savedAccount = accountRepository.save(Account.builder()
                .email(signUpRequestDto.getEmail())
                .name(signUpRequestDto.getName())
                .nickname(signUpRequestDto.getNickname())
                .password("{bcrypt}" + passwordEncoder.encode(signUpRequestDto.getPassword()))
                .roles("ROLE_USER")
                .joinedAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .gender(signUpRequestDto.getGender())
                .build());

        return modelMapper.map(savedAccount, SignUp.Response.class);
    }

    @Override
    public SignUp.Response signUpOAuth(SignUp.OAuthRequest request) throws NoSuchAlgorithmException {

        Account savedAccount = accountRepository.save(Account.builder()
                .email(SHA256.encrypt(request.getId()))
                .name(request.getName())
                .nickname(request.getNickname())
                .password("{bcrypt}" + passwordEncoder.encode(OAUTH_PASSWORD))
                .roles("ROLE_USER")
                .joinedAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(request.getProvider())
                .gender(request.getGender())
                .build());

        return SignUp.Response.builder().provider(savedAccount.getProvider())
                .id(savedAccount.getId().toString()).name(savedAccount.getName()).build();
    }

    public Map<String, String> login(Login.Common login) throws Exception {
        authenticate(login.getEmail(), login.getPassword());

        String jwtToken = jwtTokenUtil.generateToken(login.getEmail());
        return jwtTokenUtil.createTokenMap(jwtToken);
    }

//    OAuth 로그인
    @Override
    public Map<String, String> loginOAuth(Login.OAuth login) throws Exception {
        Login.Common request = Login.Common.builder()
                .email(SHA256.encrypt(login.getId()))
                .password(OAUTH_PASSWORD).build();
        return login(request);
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

    public void saveSignUpDetail(LoginUser loginUser, SignUp.DetailRequest req) {

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
    public Update.ProfileResponse getProfile(LoginUser loginUser) {

        return modelMapper.map(loginUser.getAccount(), Update.ProfileResponse.class);
    }

    public void updateProfile(LoginUser loginUser, Update.ProfileRequest request) {
        Account account = accountRepository.findByEmail(loginUser.getUsername()).orElseThrow();
        account.updateProfile(request);
    }

    public void updatePassword(LoginUser loginUser,
                               Update.PasswordRequest request) {
        Account account = accountRepository.findByEmail(loginUser.getUsername()).orElseThrow();
        account.updatePassword("{bcrypt}" + passwordEncoder.encode(request.getPassword()));
    }

    public Update.Interest getAccountInterests(LoginUser loginUser) {
        Set<Interest> interests =
                accountRepository.findByEmailWithInterests(loginUser.getUsername()).getInterests();
        return Update.Interest.builder().interests(
                interests.stream()
                        .map(interest -> interest.getTag().getFirstName())
                        .collect(Collectors.toList())).build();
    }

    public void updateAccountInterests(LoginUser loginUser, Update.Interest request) {

        Account account = accountRepository.findByEmailWithInterests(loginUser.getUsername());
        List<Tag> tags = tagRepository.findByFirstNameIn(request.getInterests());
        Set<Interest> interests = getInterests(account, tags);

//        기존에 있던 정보들 삭제
        interestRepository.deleteByAccount(account);

//        연관관계 메서드
        account.updateInterests(interests);
        interestRepository.saveAll(interests);
    }

    public Update.MainActivityZone getAccountMainActivityZones(LoginUser loginUser) {
        Set<MainActivityZone> mainActivityZones =
                accountRepository.findByEmailWithMainActivityZones(loginUser.getUsername()).getMainActivityZones();
        return Update.MainActivityZone.builder()
                .mainActivityZones(
                        mainActivityZones.stream().map(zone -> zone.getZone().toString()).collect(Collectors.toList()))
                .build();
    }

    public void updateAccountMainActivityZones(LoginUser loginUser,
                                               Update.MainActivityZone request) {
        Account account = accountRepository.findByEmailWithMainActivityZones(loginUser.getUsername());
        Map<String, List<String>> map = request.splitCityAndProvince();
        List<Zone> zones
                = zoneRepository.findByCityInAndProvinceIn(map.get("city"), map.get("province"));
        Set<MainActivityZone> mainActivityZones = getMainActivityZones(account, zones);

//        기존에 있던 정보들 삭제
        mainActivityZoneRepository.deleteByAccount(account);

//        연관관계 메서드
        account.updateMainActivityZones(mainActivityZones);
        mainActivityZoneRepository.saveAll(mainActivityZones);
    }

    public Map<String, String> getAccountNickname(LoginUser loginUser) {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", loginUser.getAccount().getNickname());
        return map;
    }

    public void updateAccountNickname(LoginUser loginUser, Update.NicknameRequest request) {
        Account account = accountRepository.findByEmail(loginUser.getUsername()).orElseThrow();
        account.changeNickname(request.getNickname());
    }

    public Update.Notification getAccountNotification(LoginUser loginUser) {

        Account account = loginUser.getAccount();
        return modelMapper.map(account, Update.Notification.class);
    }

    public void updateAccountNotification(LoginUser loginUser, Update.Notification request) {

        Account account = accountRepository.findByEmail(loginUser.getUsername()).orElseThrow();
        account.updateNotifications(request);
    }
}
