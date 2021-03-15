package com.project.devidea.modules.content.study.apply;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class StudyApplyForm {
    @NotBlank
    Long id; //studyid

    @NotBlank
    String account;

    @NotBlank
    String study;

    String answer;

    String etc;
}
