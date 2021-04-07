package com.project.devidea.modules.content.resume.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.content.resume.form.education.CreateEducationRequest;
import com.project.devidea.modules.content.resume.form.education.EducationRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class EducationRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EducationRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        EducationRequest request = (EducationRequest) target;

        LocalDate entranceDate = LocalDate.parse(request.getEntranceDate());
        LocalDate graduationDate = LocalDate.parse(request.getGraduationDate());

        if (entranceDate.isAfter(LocalDate.now())) {
            errors.rejectValue("entranceDate", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

        if (graduationDate != null && !graduationDate.isAfter(entranceDate)) {
            errors.rejectValue("graduationDate", ErrorCode.INVALID_INPUT_VALUE.getCode());
        }

    }
}
