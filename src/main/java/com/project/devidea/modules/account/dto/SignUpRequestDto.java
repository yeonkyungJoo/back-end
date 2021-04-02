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

//    프론트분들이 암호화해서 보내주십니다!
    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    private String password;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    private String passwordConfirm;

    @Size(min = 2, max = 8, message = "2자 이상 입력해주세요.")
    private String name;

    private String gender;
}
