package com.project.devidea.modules.account.dto;

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

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "성함을 입력해주세요.")
    private String name;

    private String profileImage;

}
