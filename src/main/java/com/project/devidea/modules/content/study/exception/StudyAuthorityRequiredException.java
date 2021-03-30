package com.project.devidea.modules.content.study.exception;

import com.project.devidea.infra.error.exception.BusinessException;
import com.project.devidea.infra.error.exception.ErrorCode;
import org.springframework.validation.Errors;

public class StudyAuthorityRequiredException extends BusinessException {

    public StudyAuthorityRequiredException(String target) {
        super(target + "님은 스터디 권한이 없습니다.", ErrorCode.UNAUTHORIZED);
    }
}
