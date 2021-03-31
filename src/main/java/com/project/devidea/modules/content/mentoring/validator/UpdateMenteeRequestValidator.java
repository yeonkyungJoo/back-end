package com.project.devidea.modules.content.mentoring.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.content.mentoring.form.UpdateMenteeRequest;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UpdateMenteeRequestValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UpdateMenteeRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdateMenteeRequest request = (UpdateMenteeRequest) target;

        if (request.getZones().size() == 0) {
            errors.rejectValue("zones", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

        if (request.getTags().size() == 0) {
            errors.rejectValue("tags", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }
    }
}
