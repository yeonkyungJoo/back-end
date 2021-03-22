package com.project.devidea.modules.account.form;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpOAuthRequestDto {

    @NotBlank(message = "인증한 소셜 사이트 제공자를 입력해주세요.")
    private String provider;

    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "성함을 입력해주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    private String profileImage;

}
