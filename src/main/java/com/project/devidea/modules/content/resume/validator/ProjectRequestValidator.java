package com.project.devidea.modules.content.resume.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.content.resume.form.project.ProjectRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class ProjectRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProjectRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProjectRequest request = (ProjectRequest) target;

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
