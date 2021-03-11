package com.project.devidea.infra.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.devidea.infra.config.jwt.JwtTokenUtil;
import com.project.devidea.infra.config.jwt.JwtUserDetailsService;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountRepository;
import com.project.devidea.modules.account.AccountService;
import com.project.devidea.modules.account.form.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BandCombineOp;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {

    private final List<SocialOAuth> socialOAuths;
    private final HttpServletResponse response;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final String OAUTH_PASSWORD = "devidea";
    private final JwtTokenUtil jwtTokenUtil;

    public void request(SocialLoginType socialLoginType) {
        SocialOAuth socialOAuth = findSocialOAuthByType(socialLoginType);
        String redirectURL = socialOAuth.getOAuthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> oauthLogin(SocialLoginType socialLoginType, String code) throws Exception {
        SocialOAuth socialOAuth = findSocialOAuthByType(socialLoginType);
        Map<String, String> userInfoMap = socialOAuth.requestLogin(code);
        Account account = accountRepository.findByEmail(userInfoMap.get("email")).orElse(null);
        if (account == null) {
            account = save(userInfoMap, socialLoginType);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(account, "", account.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        String jwtToken = jwtTokenUtil.generateToken(account.getUsername());
        return jwtTokenUtil.createTokenMap(jwtToken);
    }

    private Account save(Map<String, String> userInfoMap, SocialLoginType socialLoginType) {
        return accountRepository.save(Account.builder()
                .email(userInfoMap.get("email"))
                .name(userInfoMap.get("name"))
                .password(passwordEncoder.encode(OAUTH_PASSWORD))
                .nickname(userInfoMap.get("name"))
                .roles("ROLE_USER")
                .joinedAt(LocalDateTime.now())
                .profileImage(userInfoMap.get("picture"))
                .provider(socialLoginType.name())
                .build());
    }

    private SocialOAuth findSocialOAuthByType(SocialLoginType socialLoginType) {
        return socialOAuths.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 소셜 계정입니다"));
    }
}
