package com.project.devidea.modules.content.study.apply;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyApplyForm {
    @NotNull
    Long id; //applyid

    @NotNull
    String applicant; //지원자

    @NotNull
    String study;

    @NotNull
    Long studyId;

    String answer;

    String etc;
}
