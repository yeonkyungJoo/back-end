package com.project.devidea.modules.content.resume.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.content.resume.form.career.CareerRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CareerRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CareerRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CareerRequest request = (CareerRequest) target;

        LocalDate startDate = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(request.getEndDate(), DateTimeFormatter.ISO_DATE);
        LocalDate now = LocalDate.now();

        if (startDate.isAfter(now)) {
            errors.rejectValue("startDate", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

        if (!request.isPresent()) { // 재직중이 아닌 경우
            if (endDate == null || (endDate.isAfter(now) || !endDate.isAfter(startDate))) {
                errors.rejectValue("endDate", ErrorCode.INVALID_INPUT_VALUE.getCode());
            }
        }

    }
}
