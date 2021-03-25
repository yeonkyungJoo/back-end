package com.project.devidea.modules.account.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountProfileUpdateRequestDto {

    private String bio;
    private String profileImage;
    private String url;
    private String gender;
    private String job;
    private int careerYears;
    @Builder.Default
    private List<String> techStacks = new ArrayList<>();
}
