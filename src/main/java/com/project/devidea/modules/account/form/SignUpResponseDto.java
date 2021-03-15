package com.project.devidea.modules.account.form;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponseDto {

    private String id;

    private String email;

    private String name;

    private String nickname;

    private String gender;
}
