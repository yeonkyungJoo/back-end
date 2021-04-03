package com.project.devidea.modules.account.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNicknameRequest {

    @Size(min = 3, max = 8, message = "닉네임은 3자 이상 8자 이하로 입력해주세요.")
    private String nickname;
}
