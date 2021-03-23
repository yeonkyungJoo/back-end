package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.dto.AccountProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountInfoController {

    @Autowired
    private final AccountService accountService;

    @GetMapping("/settings/profile")
    public AccountProfileResponseDto getProfile(@AuthenticationPrincipal LoginUser loginUser) {
        return accountService.getProfile(loginUser);
    }
}
