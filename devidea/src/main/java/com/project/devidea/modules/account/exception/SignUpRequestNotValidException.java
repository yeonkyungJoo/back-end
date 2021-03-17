package com.project.devidea.modules.account.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;

@Getter
@Setter
public class SignUpRequestNotValidException extends RuntimeException{

    private Errors errors;
    private String msg;

    public SignUpRequestNotValidException(String msg, Errors errors) {
        super(msg);
        this.errors = errors;
    }
}
