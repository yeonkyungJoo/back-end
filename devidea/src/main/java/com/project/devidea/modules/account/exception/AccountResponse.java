package com.project.devidea.modules.account.exception;

import com.project.devidea.modules.account.Account;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServlet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private int statusCode;
    private LocalDateTime time;
    private List<ErrorFieldAndMessage> errorFieldAndMessageList = new ArrayList<>();
    private String description;

//    여기서 response를 만들어주기
    public static AccountResponse isOkResponse() {
        return AccountResponse.builder()
                .description("요청이 정상적으로 처리되었습니다.")
                .statusCode(HttpStatus.OK.value())
                .time(LocalDateTime.now())
                .build();
    }

    public static AccountResponse badRequestResponse(AccountRequestNotValidException exception) {
        return AccountResponse.builder()
                .time(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorFieldAndMessageList(exception.getErrors().getFieldErrors()
                        .stream()
                        .map(fieldError -> ErrorFieldAndMessage.builder()
                                .field(fieldError.getField())
                                .message(fieldError.getDefaultMessage())
                                .build())
                        .collect(Collectors.toList()))
                .description(exception.getMsg())
                .build();
    }

    public static AccountResponse badCredentialsResponse(BadCredentialsException exception) {
        return AccountResponse.builder()
                .time(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .description("회원의 아이디와 비밀번호가 일치하지 않습니다.")
                .build();
    }
}
