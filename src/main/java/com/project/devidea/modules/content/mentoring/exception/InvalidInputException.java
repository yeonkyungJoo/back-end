package com.project.devidea.modules.content.mentoring.exception;

import com.project.devidea.infra.error.exception.BusinessException;
import com.project.devidea.infra.error.exception.ErrorCode;

public class InvalidInputException extends BusinessException {

    public InvalidInputException() {
        super("잘못된 입력입니다.", ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidInputException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }
}
