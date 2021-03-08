package com.project.devidea.modules.account.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 30)
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank
    @Length(min = 2, max = 6)
    private String name;

    @NotBlank
    @Length(min = 2, max = 8)
    private String nickname;

    private String gender;
}
