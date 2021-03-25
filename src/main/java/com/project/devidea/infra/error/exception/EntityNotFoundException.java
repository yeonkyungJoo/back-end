package com.project.devidea.infra.error.exception;

import lombok.NoArgsConstructor;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String target) {
        super(target + " is not found", ErrorCode.ENTITY_NOT_FOUND);
    }
}
