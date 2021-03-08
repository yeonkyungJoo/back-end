package com.project.devidea.modules.account;


import com.project.devidea.infra.config.jwt.JwtTokenUtil;
import com.project.devidea.infra.config.jwt.JwtUserDetailsService;
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

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper;

    private void authenticate(String email, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

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

//    public String login(LoginRequestDto loginRequestDto) throws Exception {
//        authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());
//
//        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginRequestDto.getEmail());
//        return jwtTokenUtil.generateToken(userDetails);
//    }

}
