package com.project.devidea.modules.content.study.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudySearchForm {

    private String keyword; //keyword

    private int page=0; //page

    private List<String> tags;

    private Integer pageSize;

    private String city; ////서울특별시

    private String province; //송파구

    private Boolean recruiting; //모집하고 있는 스터디


    private Boolean mentorRecruiting; //멘토 있는 스터디

    private Boolean Likes; //좋아요 순으로

}
