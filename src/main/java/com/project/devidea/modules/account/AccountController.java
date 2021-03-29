package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.exception.AccountRequestNotValidException;
import com.project.devidea.modules.account.exception.AccountResponse;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.account.validator.SignUpOAuthRequestValidator;
import com.project.devidea.modules.account.validator.SignUpRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
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

    @InitBinder("signUpRequestDto")
    public void initSignUpRequestDtoValidator(WebDataBinder binder) {
        binder.addValidators(signUpRequestValidator);
    }

    @InitBinder("signUpOAuthRequestValidator")
    public void initSignUpOAuthRequestValidator(WebDataBinder binder) {
        binder.addValidators(signUpOAuthRequestValidator);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new AccountRequestNotValidException("회원가입 폼의 입력값을 확인해주세요.", errors);
        }

        return new ResponseEntity<>(accountService.signUp(signUpRequestDto), HttpStatus.OK);
    }

//    oauth 회원가입
    @PostMapping("/sign-up/oauth")
    public ResponseEntity<?> loginOAuth(@Valid @RequestBody SignUpOAuthRequestDto signUpOAuthRequestDto,
                                        Errors errors) {
        if (errors.hasErrors()) {
            throw new AccountRequestNotValidException("회원가입 폼의 입력값을 확인해주세요.", errors);
        }
        return new ResponseEntity<>(accountService.signUpOAuth(signUpOAuthRequestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {

        Map<String, String> result = accountService.login(loginRequestDto);
        return new ResponseEntity<>(getHttpHeaders(result), HttpStatus.OK);
    }

//    oauth 로그인
    @PostMapping("/login/oauth")
    public ResponseEntity<?> loginOAuth(@RequestBody LoginOAuthRequestDto loginOAuthRequestDto,
                                        Errors errors) throws Exception {
        if (errors.hasErrors()) {
            throw new AccountRequestNotValidException("정보를 제공하는 소셜 사이트를 확인해주세요.", errors);
        }

        Map<String, String> result = accountService.loginOAuth(loginOAuthRequestDto);
        return new ResponseEntity<>(getHttpHeaders(result), HttpStatus.OK);
    }

    private HttpHeaders getHttpHeaders(Map<String, String> result) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(result.get("header"), result.get("token"));
        return headers;
    }

//    회원가입 디테일
    @PostMapping("/sign-up/detail")
    public ResponseEntity<AccountResponse> signUpDetail(@AuthenticationPrincipal LoginUser loginUser,
                                                        @Valid @RequestBody SignUpDetailRequestDto signUpDetailRequestDto,
                                                        Errors errors) {
        if (errors.hasErrors()) {
            throw new AccountRequestNotValidException("상세 정보 입력을 확인해주세요.", errors);
        }
        accountService.saveSignUpDetail(loginUser, signUpDetailRequestDto);
        return new ResponseEntity<>(AccountResponse.isOkResponse(), HttpStatus.OK);
    }
}