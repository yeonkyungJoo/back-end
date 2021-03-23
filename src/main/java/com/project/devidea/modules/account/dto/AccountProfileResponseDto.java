package com.project.devidea.modules.account.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountProfileResponseDto {

    private String email;
    private String name;
    private String nickname;
    private LocalDateTime joinedAt;
    private LocalDateTime modifiedAt;
    private String bio;
    private String profileImage;
    private String url;
    private String gender;
    private String job;
    private int careerYears;
    private String techStacks;
}
