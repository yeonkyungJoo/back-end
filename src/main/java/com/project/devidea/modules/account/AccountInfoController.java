package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.dto.AccountProfileResponseDto;
import com.project.devidea.modules.account.dto.AccountProfileUpdateRequestDto;
import com.project.devidea.modules.account.exception.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/settings")
public class AccountInfoController {

    @Autowired
    private final AccountService accountService;

    @GetMapping("/profile")
    public AccountProfileResponseDto getProfile(@AuthenticationPrincipal LoginUser loginUser) {
        return accountService.getProfile(loginUser);
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal LoginUser loginUser,
                                          AccountProfileUpdateRequestDto accountProfileUpdateRequestDto) {
        return new ResponseEntity(AccountResponse.isOkResponse(), HttpStatus.OK);
    }
}
