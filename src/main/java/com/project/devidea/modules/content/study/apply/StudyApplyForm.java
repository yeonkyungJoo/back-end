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
public class StudyApplyForm { //스터디 지원서를 보낼때도 쓰고 확인할때도 씀.. 처음에 보낼때는 appiant_id는 필요없음

    @NotNull( message = "스터디id")
    Long studyId;

    @NotNull( message = "지원자닉네임")
    String applicant; //지원자이름

    @NotNull( message = "스터디이름")
    String study;


    String answer;

    String etc;
}
