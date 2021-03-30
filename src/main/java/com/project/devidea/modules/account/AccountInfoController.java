package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.infra.error.GlobalResponse;
import com.project.devidea.modules.account.dto.*;
import com.project.devidea.modules.account.validator.UpdatePasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/settings")
public class AccountInfoController {

    @Autowired
    private final AccountService accountService;
    @Autowired
    private final UpdatePasswordValidator updatePasswordValidator;

    @InitBinder("updatePasswordRequestDto")
    public void initUpdatePasswordValidator(WebDataBinder binder) {
        binder.addValidators(updatePasswordValidator);
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

}
