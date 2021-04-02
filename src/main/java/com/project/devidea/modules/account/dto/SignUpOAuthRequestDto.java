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

    @NotBlank(message = "인증한 소셜 사이트의 고유 식별자 값을 입력해주세요.")
    private String id;

    @NotBlank(message = "성함을 입력해주세요.")
    private String name;

    private String profileImage;

}
