package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.jwt.JwtTokenUtil;
import com.project.devidea.modules.account.form.LoginRequestDto;
import com.project.devidea.modules.account.form.SignUpDetailRequestDto;
import com.project.devidea.modules.account.form.SignUpRequestDto;
import com.project.devidea.modules.account.form.SignUpResponseDto;
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

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper;
    private final ZoneRepository zoneRepository;
    private final TagRepository tagRepository;
    private final InterestRepository interestRepository;
    private final MainActivityZoneRepository mainActivityZoneRepository;

    //    회원가입
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        Account savedAccount = accountRepository.save(
                Account.builder()
                        .email(signUpRequestDto.getEmail())
                        .name(signUpRequestDto.getName())
                        .password("{bcrypt}" + passwordEncoder.encode(signUpRequestDto.getPassword()))
                        .nickname(signUpRequestDto.getNickname())
                        .roles("ROLE_USER")
                        .joinedAt(LocalDateTime.now())
                        .gender(signUpRequestDto.getGender())
                        .build());

        return modelMapper.map(savedAccount, SignUpResponseDto.class);
    }

    /**
     * 로그인 로직, 단순 로그인만 우선적으로 진행했습니다.
     *
     * @param requestDto : 아이디, 비밀번호
     * @return
     */
    public Map<String, String> login(LoginRequestDto requestDto) throws Exception {
        authenticate(requestDto.getEmail(), requestDto.getPassword());

        String jwtToken = jwtTokenUtil.generateToken(requestDto.getEmail());
        return jwtTokenUtil.createTokenMap(jwtToken);
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

    public void saveSignUpDetail(Account account, SignUpDetailRequestDto req) {

//        활동지역(mainActivityZones)
        Map<String, List<String>> cityProvince = req.getCitiesAndProvinces();
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
}

