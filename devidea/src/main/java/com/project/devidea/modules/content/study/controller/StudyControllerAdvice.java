package com.project.devidea.modules.content.study.controller;

import com.project.devidea.modules.account.exception.AccountErrorMessage;
import com.project.devidea.modules.account.exception.ErrorFieldAndMessage;
import com.project.devidea.modules.account.exception.SignUpRequestNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.project.devidea.modules.study")
public class StudyControllerAdvice {

    @ExceptionHandler(SignUpRequestNotValidException.class)
    public ResponseEntity<AccountErrorMessage> signUp(SignUpRequestNotValidException signUpRequestNotValidException) {

        AccountErrorMessage errorMessage = AccountErrorMessage.builder()
                .time(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorFieldAndMessageList(signUpRequestNotValidException.getErrors().getFieldErrors()
                        .stream()
                        .map(fieldError -> ErrorFieldAndMessage.builder()
                                .field(fieldError.getField())
                                .message(fieldError.getDefaultMessage())
                                .build())
                        .collect(Collectors.toList()))
                .description(signUpRequestNotValidException.getMessage())
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AccountErrorMessage> login(BadCredentialsException badCredentialsException) {

        AccountErrorMessage errorMessage = AccountErrorMessage.builder()
                .time(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .description("회원의 아이디와 비밀번호가 일치하지 않습니다.")
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
