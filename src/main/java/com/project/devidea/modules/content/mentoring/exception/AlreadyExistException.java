package com.project.devidea.modules.content.mentoring.exception;

import com.project.devidea.infra.error.exception.BusinessException;
import com.project.devidea.infra.error.exception.ErrorCode;

public class AlreadyExistException extends BusinessException {

    public AlreadyExistException() {
        super("Entity Already Exist", ErrorCode.ENTITY_ALREADY_EXIST);
    }

    public AlreadyExistException(String message) {
        super(message, ErrorCode.ENTITY_ALREADY_EXIST);
    }
}
