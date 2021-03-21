package com.project.devidea.modules.account;

import com.project.devidea.modules.account.exception.ErrorFieldAndMessage;
import com.project.devidea.modules.account.exception.AccountResponse;
import com.project.devidea.modules.account.exception.AccountRequestNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.project.devidea.modules.account")
public class AccountControllerAdvice {

    @ExceptionHandler(AccountRequestNotValidException.class)
    public ResponseEntity<AccountResponse> signUp(AccountRequestNotValidException exception) {

        return new ResponseEntity<>(AccountResponse.badRequestResponse(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AccountResponse> signUp(BadCredentialsException exception) {

        return new ResponseEntity<>(AccountResponse.badCredentialsResponse(exception), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<AccountResponse> login(BadCredentialsException badCredentialsException) {
//
//        AccountResponse errorMessage = AccountResponse.builder()
//                .time(LocalDateTime.now())
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .description(badCredentialsException.getMessage())
//                .build();
//
//        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }
}
