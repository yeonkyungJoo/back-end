//package com.project.devidea.modules.content.study.controller;
//
//import com.project.devidea.modules.account.exception.AccountRequestNotValidException;
//import com.project.devidea.modules.account.exception.AccountResponse;
//import com.project.devidea.modules.account.exception.ErrorFieldAndMessage;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice(basePackages = "com.project.devidea.modules.study")
//public class StudyControllerAdvice {
//
//    @ExceptionHandler(AccountRequestNotValidException.class)
//    public ResponseEntity<AccountResponse> signUp(AccountRequestNotValidException signUpRequestNotValidException) {
//
//        AccountResponse errorMessage = AccountResponse.builder()
//                .time(LocalDateTime.now())
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .errorFieldAndMessageList(signUpRequestNotValidException.getErrors().getFieldErrors()
//                        .stream()
//                        .map(fieldError -> ErrorFieldAndMessage.builder()
//                                .field(fieldError.getField())
//                                .message(fieldError.getDefaultMessage())
//                                .build())
//                        .collect(Collectors.toList()))
//                .description(signUpRequestNotValidException.getMessage())
//                .build();
//
//        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<AccountResponse> login(BadCredentialsException badCredentialsException) {
//
//        AccountResponse errorMessage = AccountResponse.builder()
//                .time(LocalDateTime.now())
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .description("회원의 아이디와 비밀번호가 일치하지 않습니다.")
//                .build();
//
//        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }
//}
