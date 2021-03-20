package com.project.devidea.modules.account.validator;

import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.account.form.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpRequestValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequestDto request = (SignUpRequestDto) target;

        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            errors.rejectValue("password", "invalid.password", "비밀번호가 일치하지 않습니다.");
        }

        if (accountRepository.existsByEmail(request.getEmail())) {
            errors.rejectValue("email", "invalid.email", "이미 존재하는 이메일입니다.");
        }

        if (accountRepository.existsByNickname(request.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", "이미 존재하는 닉네임입니다.");
        }
    }
}
