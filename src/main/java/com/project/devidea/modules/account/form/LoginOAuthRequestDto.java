package com.project.devidea.modules.account.form;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginOAuthRequestDto {

    @NotBlank(message = "인증한 소셜 사이트 제공자를 입력해주세요.")
    private String provider;
    private String email;
}
