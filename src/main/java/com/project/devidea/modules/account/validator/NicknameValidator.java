package com.project.devidea.modules.account.validator;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.account.dto.ChangeNicknameRequest;
import com.project.devidea.modules.account.exception.AccountException;
import com.project.devidea.modules.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class NicknameValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return ChangeNicknameRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ChangeNicknameRequest request = (ChangeNicknameRequest) target;

        if (accountRepository.existsByNickname(request.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", "닉네임이 중복되었습니다.");
        }

        if (errors.hasErrors()) {
            throw new AccountException(ErrorCode.ACCOUNT_ERROR, errors);
        }
    }
}
