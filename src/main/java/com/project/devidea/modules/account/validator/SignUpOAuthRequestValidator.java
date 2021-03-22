package com.project.devidea.modules.account.validator;

import com.project.devidea.modules.account.form.SignUpOAuthRequestDto;
import com.project.devidea.modules.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpOAuthRequestValidator implements Validator {

    private final AccountRepository accountRepository;


    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpOAuthRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpOAuthRequestDto request = (SignUpOAuthRequestDto) target;

        if (accountRepository.existsByEmail(request.getEmail())) {
            errors.rejectValue("email", "invalid.email", "이미 가입하신 회원입니다.");
        }

        if (accountRepository.existsByNickname(request.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", "이미 닉네임이 존재합니다.");
        }
    }
}
