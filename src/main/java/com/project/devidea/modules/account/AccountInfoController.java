package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.error.GlobalResponse;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.account.validator.NicknameValidator;
import com.project.devidea.modules.account.validator.UpdatePasswordValidator;
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
    private final UpdatePasswordValidator updatePasswordValidator;
    private final NicknameValidator nicknameValidator;

    @InitBinder("updatePasswordRequestDto")
    public void initUpdatePasswordValidator(WebDataBinder binder) {
        binder.addValidators(updatePasswordValidator);
    }

    @InitBinder("changeNicknameRequest")
    public void initNicknameValidator(WebDataBinder binder) {
        binder.addValidators(nicknameValidator);
    }

    @GetMapping("/profile")
    public AccountProfileResponseDto getProfile(@AuthenticationPrincipal LoginUser loginUser) {
//        ResponseEntity로 가져오기
        return accountService.getProfile(loginUser);
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal LoginUser loginUser,
                                           @RequestBody AccountProfileUpdateRequestDto accountProfileUpdateRequestDto) {
        accountService.updateProfile(loginUser, accountProfileUpdateRequestDto);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal LoginUser loginUser,
                                            @Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto, Errors errors) {

        accountService.updatePassword(loginUser, updatePasswordRequestDto);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/interests")
    public ResponseEntity<?> getInterests(@AuthenticationPrincipal LoginUser loginUser) {

        return new ResponseEntity<>(GlobalResponse.of(accountService.getAccountInterests(loginUser)), HttpStatus.OK);
    }

    @PatchMapping("/interests")
    public ResponseEntity<?> updateInterests(@AuthenticationPrincipal LoginUser loginUser,
                                             @RequestBody InterestsUpdateRequestDto interestsUpdateRequestDto) {
        accountService.updateAccountInterests(loginUser, interestsUpdateRequestDto);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/mainactivityzones")
    public ResponseEntity<?> getMainActivityZones(@AuthenticationPrincipal LoginUser loginUser) {
        return new ResponseEntity<>(GlobalResponse.of(accountService.getAccountMainActivityZones(loginUser)),
                HttpStatus.OK);
    }

    @PatchMapping("/mainactivityzones")
    public ResponseEntity<?> updateMainActivityZones(@AuthenticationPrincipal LoginUser loginUser,
                                                     @RequestBody MainActivityZonesUpdateRequestDto mainActivityZonesUpdateRequestDto) {
        accountService.updateAccountMainActivityZones(loginUser, mainActivityZonesUpdateRequestDto);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/nickname")
    public ResponseEntity<?> getAccountNickname(@AuthenticationPrincipal LoginUser loginUser) {

        Map<String, String> map = accountService.getAccountNickname(loginUser);
        return new ResponseEntity<>(GlobalResponse.of(map), HttpStatus.OK);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<?> updateAccountNickname(@AuthenticationPrincipal LoginUser loginUser,
                                                   @Valid @RequestBody ChangeNicknameRequest changeNicknameRequest){

        accountService.updateAccountNickname(loginUser, changeNicknameRequest);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getAccountNotifications(@AuthenticationPrincipal LoginUser loginUser) {

        NotificationRequestResponse response = accountService.getAccountNotification(loginUser);
        return new ResponseEntity<>(GlobalResponse.of(response), HttpStatus.OK);
    }

    @PatchMapping("/notifications")
    public ResponseEntity<?> updateAccountNotifications(@AuthenticationPrincipal LoginUser loginUser,
                                                     @RequestBody NotificationRequestResponse request) {

        accountService.updateAccountNotification(loginUser, request);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);

    }
}
