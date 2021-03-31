package com.project.devidea.modules.content.study.exception;

import org.springframework.validation.Errors;

public class StudyRequestNotValidException extends RuntimeException {
    private Errors errors;
    private String msg;

    public StudyRequestNotValidException(String msg, Errors errors) {
        super(msg);
        this.msg = msg;
        this.errors = errors;
    }
}
