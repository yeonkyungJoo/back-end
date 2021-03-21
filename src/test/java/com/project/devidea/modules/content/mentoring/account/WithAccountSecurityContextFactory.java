package com.project.devidea.modules.content.mentoring.account;

import com.project.devidea.infra.config.security.CustomUserDetailService;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final AccountRepository accountRepository;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {

        String nickname = withAccount.value();
        String email = nickname + "@email.com";
        Account account = Account.builder()
                .nickname(nickname)
                .name(nickname)
                .email(email)
                .password("1234")
                .roles("ROLE_USER")
                .build();
        accountRepository.save(account);

        UserDetails principal = customUserDetailService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}