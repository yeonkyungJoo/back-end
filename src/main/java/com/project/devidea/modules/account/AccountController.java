package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.oauth.OAuthService;
import com.project.devidea.infra.config.security.oauth.provider.SocialLoginType;
import com.project.devidea.modules.account.exception.AccountRequestNotValidException;
import com.project.devidea.modules.account.exception.AccountResponse;
import com.project.devidea.modules.account.form.LoginRequestDto;
import com.project.devidea.modules.account.form.SignUpDetailRequestDto;
import com.project.devidea.modules.account.form.SignUpRequestDto;
import com.project.devidea.modules.account.validator.SignUpRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final OAuthService oAuthService;
    private final SignUpRequestValidator signUpRequestValidator;

    @InitBinder("signUpRequestDto")
    public void initSignUpRequestDtoValidator(WebDataBinder binder) {
        binder.addValidators(signUpRequestValidator);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            throw new AccountRequestNotValidException("회원가입 폼의 입력값을 확인해주세요.", errors);
        }

        return new ResponseEntity<>(accountService.signUp(signUpRequestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        Map<String, String> result = accountService.login(loginRequestDto);
        HttpHeaders headers = getHttpHeaders(result);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    private HttpHeaders getHttpHeaders(Map<String, String> result) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(result.get("header"), result.get("token"));
        return headers;
    }

    @GetMapping("/login/oauth/{socialLoginType}")
    public void loginOAuth(@PathVariable SocialLoginType socialLoginType) {
        oAuthService.request(socialLoginType);
    }

    @GetMapping("/login/oauth/{socialLoginType}/callback")
    public ResponseEntity<?> loginCallbackOAuth(@PathVariable SocialLoginType socialLoginType,
                                                @RequestParam("code") String code) throws Exception {
        Map<String, String> result = oAuthService.oauthLogin(socialLoginType, code);
        HttpHeaders headers = getHttpHeaders(result);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    /**
     * 회원가입 디테일
     * @param account
     * @param signUpDetailRequestDto
     */
    @PostMapping("/sign-up/detail")
    public ResponseEntity<AccountResponse> signUpDetail(@AuthenticationPrincipal Account account,
                                                        @Valid @RequestBody SignUpDetailRequestDto signUpDetailRequestDto,
                                                        Errors errors) {
        if (errors.hasErrors()) {
            throw new AccountRequestNotValidException("상세 정보 입력을 확인해주세요.", errors);
        }
        accountService.saveSignUpDetail(account, signUpDetailRequestDto);
        return new ResponseEntity<>(AccountResponse.isOkResponse(), HttpStatus.OK);
    }
}