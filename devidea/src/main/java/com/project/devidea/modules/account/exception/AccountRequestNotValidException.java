package com.project.devidea.modules.account.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;

@Getter
@Setter
public class AccountRequestNotValidException extends RuntimeException{

    private Errors errors;
    private String msg;

    public AccountRequestNotValidException(String msg, Errors errors) {
        super(msg);
        this.msg = msg;
        this.errors = errors;
    }
}
