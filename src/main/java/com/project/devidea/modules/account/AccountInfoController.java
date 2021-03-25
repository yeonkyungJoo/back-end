package com.project.devidea.modules.account;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.dto.AccountProfileResponseDto;
import com.project.devidea.modules.account.dto.AccountProfileUpdateRequestDto;
import com.project.devidea.modules.account.dto.UpdatePasswordRequestDto;
import com.project.devidea.modules.account.exception.AccountResponse;
import com.project.devidea.modules.account.repository.AccountRepository;
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
        return new ResponseEntity<>(AccountResponse.isOkResponse(), HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal LoginUser loginUser,
                                            @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto, Errors errors) {
//        if (errors.hasErrors()) {
//
//        }
        accountService.updatePassword(loginUser, updatePasswordRequestDto);
        return new ResponseEntity<>(AccountResponse.isOkResponse(), HttpStatus.OK);
    }
}
