package com.project.devidea.modules.account;


import com.project.devidea.infra.config.jwt.JwtTokenUtil;
import com.project.devidea.modules.account.form.LoginRequestDto;
import com.project.devidea.modules.account.form.SignUpRequestDto;
import com.project.devidea.modules.account.form.SignUpResponseDto;
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
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper;

//    회원가입
    public SignUpResponseDto save(SignUpRequestDto signUpRequestDto) {
        Account savedAccount = accountRepository.save(
                Account.builder()
                        .email(signUpRequestDto.getEmail())
                        .name(signUpRequestDto.getName())
                        .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                        .nickname(signUpRequestDto.getNickname())
                        .roles("ROLE_USER")
                        .joinedAt(LocalDateTime.now())
                        .gender(signUpRequestDto.getGender())
                        .build());

        return modelMapper.map(savedAccount, SignUpResponseDto.class);
    }

    /**
     * 로그인 로직, 단순 로그인만 우선적으로 진행했습니다.
     * @param requestDto : 아이디, 비밀번호
     * @return
     */
    public Map<String, String> login(LoginRequestDto requestDto) throws Exception {
        authenticate(requestDto.getEmail(), requestDto.getPassword());

        String jwtToken = jwtTokenUtil.generateToken(requestDto.getEmail());
        return jwtTokenUtil.createTokenMap(jwtToken);
    }

    private void authenticate(String email, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
