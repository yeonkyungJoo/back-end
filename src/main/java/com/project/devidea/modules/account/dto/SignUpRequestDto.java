package com.project.devidea.modules.account.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @Size(min = 8, max = 30, message = "8자 이상 30자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호를 한번 더 입력해주세요.")
    private String passwordConfirm;

    @Size(min = 2, max = 8, message = "2자 이상 입력해주세요.")
    private String name;

    @Size(min = 2, max = 8, message = "닉네임은 2자 이상 8자 이하로 입력해주세요.")
    private String nickname;

    private String gender;
}
