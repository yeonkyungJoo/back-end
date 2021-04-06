package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.error.GlobalResponse;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.account.validator.NicknameValidator;
import com.project.devidea.modules.account.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/account/settings")
public class AccountInfoController {

    private final AccountService accountService;
    private final PasswordValidator updatePasswordValidator;
    private final NicknameValidator nicknameValidator;

    @InitBinder("passwordRequest")
    public void initUpdatePasswordValidator(WebDataBinder binder) {
        binder.addValidators(updatePasswordValidator);
    }

    @InitBinder("nicknameRequest")
    public void initNicknameValidator(WebDataBinder binder) {
        binder.addValidators(nicknameValidator);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal LoginUser loginUser) {

        return new ResponseEntity<>(GlobalResponse.of(accountService.getProfile(loginUser)), HttpStatus.OK);
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal LoginUser loginUser,
                                           @RequestBody Update.ProfileRequest request) {

        accountService.updateProfile(loginUser, request);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal LoginUser loginUser,
                                            @Valid @RequestBody Update.PasswordRequest request) {

        accountService.updatePassword(loginUser, request);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/interests")
    public ResponseEntity<?> getInterests(@AuthenticationPrincipal LoginUser loginUser) {

        return new ResponseEntity<>(GlobalResponse.of(accountService.getAccountInterests(loginUser)), HttpStatus.OK);
    }

    @PatchMapping("/interests")
    public ResponseEntity<?> updateInterests(@AuthenticationPrincipal LoginUser loginUser,
                                             @RequestBody Update.Interest request) {
        accountService.updateAccountInterests(loginUser, request);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/mainactivityzones")
    public ResponseEntity<?> getMainActivityZones(@AuthenticationPrincipal LoginUser loginUser) {

        return new ResponseEntity<>(GlobalResponse.of(accountService.getAccountMainActivityZones(loginUser)), HttpStatus.OK);
    }

    @PatchMapping("/mainactivityzones")
    public ResponseEntity<?> updateMainActivityZones(@AuthenticationPrincipal LoginUser loginUser,
                                                     @RequestBody Update.MainActivityZone request) {
        accountService.updateAccountMainActivityZones(loginUser, request);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/nickname")
    public ResponseEntity<?> getAccountNickname(@AuthenticationPrincipal LoginUser loginUser) {

        Map<String, String> map = accountService.getAccountNickname(loginUser);
        return new ResponseEntity<>(GlobalResponse.of(map), HttpStatus.OK);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<?> updateAccountNickname(@AuthenticationPrincipal LoginUser loginUser,
                                                   @Valid @RequestBody Update.NicknameRequest request){

        accountService.updateAccountNickname(loginUser, request);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getAccountNotifications(@AuthenticationPrincipal LoginUser loginUser) {

        Update.Notification response = accountService.getAccountNotification(loginUser);
        return new ResponseEntity<>(GlobalResponse.of(response), HttpStatus.OK);
    }

    @PatchMapping("/notifications")
    public ResponseEntity<?> updateAccountNotifications(@AuthenticationPrincipal LoginUser loginUser,
                                                     @RequestBody Update.Notification request) {

        accountService.updateAccountNotification(loginUser, request);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);

    }
}
