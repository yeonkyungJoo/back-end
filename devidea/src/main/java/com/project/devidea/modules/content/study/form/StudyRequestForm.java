package com.project.devidea.modules.content.study.form;

import com.project.devidea.modules.zone.ZoneForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyRequestForm {

    private String keyword; //keyword

    @NotBlank
    private Long page; //page

    private int pageSize;

    private String zone; //지역

    private Boolean mentorRecruiting; //멘토 있는 스터디

    private boolean recruiting; //모집하고 있는 스터디

    private boolean like; //좋아요 순으로

}
