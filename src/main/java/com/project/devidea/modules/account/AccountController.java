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
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    // TODO : Custom-Validator를 상속해도 잘 동작하는지?
    private final SignUpRequestValidator signUpRequestValidator;
    private final SignUpOAuthRequestValidator signUpOAuthRequestValidator;
//    private final NicknameValidator nicknameValidator;

    @InitBinder("commonRequest")
    public void initSignUpValidator(WebDataBinder binder) {
        binder.addValidators(signUpRequestValidator);
    }

    @InitBinder("OAuthRequest")
    public void initSignUpOAuthValidator(WebDataBinder binder) {
        binder.addValidators(signUpOAuthRequestValidator);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUp.CommonRequest commonRequest) {

        return new ResponseEntity<>(GlobalResponse.of(accountService.signUp(commonRequest)), HttpStatus.OK);
    }

    @PostMapping("/sign-up/oauth")
    public ResponseEntity<?> signUpOAuth(@Valid @RequestBody SignUp.OAuthRequest oAuthRequest)
            throws NoSuchAlgorithmException {

        return new ResponseEntity<>(GlobalResponse.of(accountService.signUpOAuth(oAuthRequest)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Login.Common login) throws Exception {

        Map<String, String> result = accountService.login(login);
        return new ResponseEntity<>(GlobalResponse.of(), getHttpHeaders(result), HttpStatus.OK);
    }

    @PostMapping("/login/oauth")
    public ResponseEntity<?> loginOAuth(@Valid @RequestBody Login.OAuth login) throws Exception {

        Map<String, String> result = accountService.loginOAuth(login);
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
                                        @Valid @RequestBody SignUp.DetailRequest detailRequest) {

        accountService.saveSignUpDetail(loginUser, detailRequest);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }
}
