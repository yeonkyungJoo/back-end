package com.project.devidea.modules.content.mentoring.validator;

import com.project.devidea.modules.content.mentoring.form.CreateMentorRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateMentorRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CreateMentorRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
