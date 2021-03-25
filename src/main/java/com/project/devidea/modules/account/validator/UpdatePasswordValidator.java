package com.project.devidea.modules.account.validator;

import com.project.devidea.modules.account.dto.UpdatePasswordRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UpdatePasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdatePasswordRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UpdatePasswordRequestDto updatePasswordRequestDto = (UpdatePasswordRequestDto) target;

        if (!updatePasswordRequestDto.getPassword().equals(updatePasswordRequestDto.getPasswordConfirm())) {
            errors.rejectValue("password", "invalid.password",
                    "패스워드와 패스워드 확인이 일치하지 않습니다.");
        }
    }
}
