package com.project.devidea.modules.content.study.apply;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Study;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StudyApplyListForm {
    @NotNull( message = "스터디id")
    Long id; //studyid

    @NotBlank(message="지원자 이름 입력부탁드립니다.")
    String applicant;

    @NotBlank(message="스터디 이름 입력 부탁드립니다.")
    String studyName;


    public void setStudy(Study study) {
        studyName=study.getTitle();
    }
}
