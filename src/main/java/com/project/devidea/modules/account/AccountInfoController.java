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
                                            @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto, Errors errors) {
//        if (errors.hasErrors()) {
//            ControllerAdvice 보고 작성하기
//        }
        accountService.updatePassword(loginUser, updatePasswordRequestDto);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/interests")
    public InterestsResponseDto getInterests(@AuthenticationPrincipal LoginUser loginUser) {
        return accountService.getAccountInterests(loginUser);
    }

    @PatchMapping("/interests")
    public ResponseEntity<?> updateInterests(@AuthenticationPrincipal LoginUser loginUser,
                                             @RequestBody InterestsUpdateRequestDto interestsUpdateRequestDto) {
        accountService.updateAccountInterests(loginUser, interestsUpdateRequestDto);
        return new ResponseEntity<>(GlobalResponse.of(), HttpStatus.OK);
    }

    @GetMapping("/mainactivityzones")
    public MainActivityZonesResponseDto getMainActivityZones(@AuthenticationPrincipal LoginUser loginUser) {
        return accountService.getAccountMainActivityZones(loginUser);
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
}
