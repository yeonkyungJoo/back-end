package com.project.devidea.modules.account;

import com.project.devidea.infra.config.jwt.JwtTokenUtil;
import com.project.devidea.infra.config.oauth.OAuthService;
import com.project.devidea.infra.config.oauth.provider.SocialLoginType;
import com.project.devidea.modules.account.form.LoginRequestDto;
import com.project.devidea.modules.account.form.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final OAuthService oAuthService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws Exception {
        return new ResponseEntity<>(accountService.save(signUpRequestDto), HttpStatus.OK);
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

    @PostMapping("/asdf")
    public void loginConfirm(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String email = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        System.out.println();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication.getAuthorities().stream().);
    }
}