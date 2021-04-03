package com.project.devidea.modules.content.mentoring.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.content.mentoring.form.MenteeRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MenteeRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MenteeRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MenteeRequest request = (MenteeRequest) target;

        if (request.getZones().size() == 0) {
            errors.rejectValue("zones", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

        if (request.getTags().size() == 0) {
            errors.rejectValue("tags", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

    }
}
