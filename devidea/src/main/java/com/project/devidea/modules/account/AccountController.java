package com.project.devidea.modules.account;

import com.project.devidea.infra.config.jwt.JwtUserDetailsService;
import com.project.devidea.modules.account.form.LoginRequestDto;
import com.project.devidea.modules.account.form.SignUpRequestDto;
import com.project.devidea.modules.account.form.SignUpResponseDto;
import com.project.devidea.modules.account.validator.SignUpRequestValidator;
import jdk.jshell.spi.ExecutionControlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@EnableWebMvc
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws Exception {
        return new ResponseEntity<>(accountService.save(signUpRequestDto), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        Map<String, String> result = accountService.login(loginRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set(result.get("header"), result.get("token"));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}