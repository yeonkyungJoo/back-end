package com.project.devidea.modules.content.resume.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.content.resume.form.activity.ActivityRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class ActivityRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ActivityRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ActivityRequest request = (ActivityRequest) target;
        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());
        LocalDate now = LocalDate.now();

        if (startDate.isAfter(now)) {
            errors.rejectValue("startDate", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

        if (endDate != null && (endDate.isAfter(now) || !endDate.isAfter(startDate))) {
            errors.rejectValue("endDate", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

    }
}
