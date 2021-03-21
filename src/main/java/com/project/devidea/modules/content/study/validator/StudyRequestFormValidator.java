package com.project.devidea.modules.content.study.validator;

import com.project.devidea.modules.content.study.form.StudySearchForm;
import com.project.devidea.modules.content.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class StudyRequestFormValidator implements Validator {

    private final StudyRepository studyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return StudySearchForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudySearchForm studyForm = (StudySearchForm)target;

    }
}
