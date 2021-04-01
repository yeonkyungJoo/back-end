package com.project.devidea.infra.error.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
    private Errors errors;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }

    public BusinessException(String message, ErrorCode errorCode, Errors errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Errors getErrors() {
        return errors;
    }
}
