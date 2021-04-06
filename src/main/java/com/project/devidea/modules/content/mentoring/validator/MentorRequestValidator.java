package com.project.devidea.modules.content.mentoring.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.content.mentoring.form.CreateMentorRequest;
import com.project.devidea.modules.content.mentoring.form.MentorRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MentorRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MentorRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MentorRequest request = (MentorRequest) target;

        if (request.getZones().size() == 0) {
            errors.rejectValue("zones", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

        if (request.getTags().size() == 0) {
            errors.rejectValue("tags", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

        if ((request.isFree() && request.getCost() > 0) || request.getCost() < 0) {
            errors.rejectValue("cost", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }
    }
}
