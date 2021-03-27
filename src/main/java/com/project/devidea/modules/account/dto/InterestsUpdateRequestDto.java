package com.project.devidea.modules.account.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestsUpdateRequestDto {

    @Builder.Default
    private List<String> interests = new ArrayList<>();
}
