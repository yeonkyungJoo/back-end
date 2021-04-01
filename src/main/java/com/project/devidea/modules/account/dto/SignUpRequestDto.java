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

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @Size(min = 8, max = 30, message = "8자 이상 30자 이하로 입력해주세요.")
    private String password;

    @Size(min = 8, max = 30, message = "8자 이상 30자 이하로 입력해주세요.")
    private String passwordConfirm;

    @Size(min = 2, max = 8, message = "2자 이상 입력해주세요.")
    private String name;

    private String gender;
}
