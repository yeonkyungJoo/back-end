package com.project.devidea.modules.content.mentoring.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.content.mentoring.form.CreateMenteeRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class CreateMenteeRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CreateMenteeRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateMenteeRequest request = (CreateMenteeRequest) target;

        if (request.getZones().size() == 0) {
            errors.rejectValue("zones", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

        if (request.getTags().size() == 0) {
            errors.rejectValue("tags", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

    }
}
