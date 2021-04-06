package com.project.devidea.modules.content.mentoring.exception;

import com.project.devidea.infra.error.exception.BusinessException;
import com.project.devidea.infra.error.exception.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException() {
        super("존재하지 않는 데이터입니다.", ErrorCode.ENTITY_NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }

}
