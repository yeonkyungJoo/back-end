package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.error.GlobalResponse;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.account.validator.NicknameValidator;
import com.project.devidea.modules.account.validator.SignUpOAuthRequestValidator;
import com.project.devidea.modules.account.validator.SignUpRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final SignUpRequestValidator signUpRequestValidator;
    private final SignUpOAuthRequestValidator signUpOAuthRequestValidator;
    private final NicknameValidator nicknameValidator;

    @InitBinder("signUpRequestDto")
    public void initSignUpRequestDtoValidator(WebDataBinder binder) {
        binder.addValidators(signUpRequestValidator);
        System.out.println("AccountController.initSignUpRequestDtoValidator");
    }

    @InitBinder("signUpOAuthRequestDto")
    public void initSignUpOAuthRequestValidator(WebDataBinder binder) {
        binder.addValidators(signUpOAuthRequestValidator);
    }

    @InitBinder("signUpDetailRequestDto")
    public void initSignUpDetailRequestDto(WebDataBinder binder) {
        binder.addValidators(nicknameValidator);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {

        return new ResponseEntity<>(GlobalResponse.of(accountService.signUp(signUpRequestDto)), HttpStatus.OK);
    }

//    oauth 회원가입
    @PostMapping("/sign-up/oauth")
    public ResponseEntity<?> loginOAuth(@Valid @RequestBody SignUpOAuthRequestDto signUpOAuthRequestDto) {

        return new ResponseEntity<>(GlobalResponse.of(accountService.signUpOAuth(signUpOAuthRequestDto)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws Exception {

        Map<String, String> result = accountService.login(loginRequestDto);
        return new ResponseEntity<>(GlobalResponse.of(), getHttpHeaders(result), HttpStatus.OK);
    }

//    oauth 로그인
    @PostMapping("/login/oauth")
    public ResponseEntity<?> loginOAuth(@Valid @RequestBody LoginOAuthRequestDto loginOAuthRequestDto) throws Exception {

        Map<String, String> result = accountService.loginOAuth(loginOAuthRequestDto);
        return new ResponseEntity<>(GlobalResponse.of(), getHttpHeaders(result), HttpStatus.OK);
    }

    private HttpHeaders getHttpHeaders(Map<String, String> result) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(result.get("header"), result.get("token"));
        return headers;
    }

//    회원가입 디테일
    @PostMapping("/sign-up/detail")
    public ResponseEntity<?> signUpDetail(@AuthenticationPrincipal LoginUser loginUser,
                                        @Valid @RequestBody SignUpDetailRequestDto signUpDetailRequestDto) {

        accountService.saveSignUpDetail(loginUser, signUpDetailRequestDto);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }
}
