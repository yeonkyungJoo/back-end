package com.project.devidea.modules.content.resume.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.content.resume.form.award.AwardRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class AwardRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AwardRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        AwardRequest request = (AwardRequest) target;
        LocalDate date = LocalDate.parse(request.getDate());

        if (date.isAfter(LocalDate.now())) {
            errors.rejectValue("date", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }
    }
}
