package com.project.devidea.modules.account;

import com.project.devidea.infra.config.jwt.JwtUserDetailsService;
import com.project.devidea.modules.account.form.LoginRequestDto;
import com.project.devidea.modules.account.form.SignUpRequestDto;
import com.project.devidea.modules.account.form.SignUpResponseDto;
import com.project.devidea.modules.account.validator.SignUpRequestValidator;
import jdk.jshell.spi.ExecutionControlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final JwtUserDetailsService jwtUserDetailsService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new SignUpRequestValidator(accountRepository));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto,
                                                    Errors errors) throws Exception {
        return ResponseEntity.of(Optional.of(accountService.save(signUpRequestDto)));
    }

//    @GetMapping("/login")
//    public String login(@Valid @RequestBody LoginRequestDto loginRequestDto, Errors errors) throws Exception {
//
//        if (errors.hasErrors()) {
//            errors.rejectValue("error.code", "이메일 형식으로 입력해주세요.");
//        }
//
//        return accountService.login(loginRequestDto);
//    }
}