package com.project.devidea.modules.account.dto;

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

    private String gender;

    private String provider;
}
