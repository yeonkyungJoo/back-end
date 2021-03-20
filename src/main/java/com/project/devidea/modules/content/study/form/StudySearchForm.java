package com.project.devidea.modules.content.study.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class StudySearchForm {

    private String keyword; //keyword

    private int page=0; //page

    private List<String> tags;

    private Integer pageSize;

    private String city; ////서울특별시

    private String province; //송파구

    private Boolean recruiting; //모집하고 있는 스터디

    private Boolean mentorRecruiting; //멘토 있는 스터디

    private Boolean likes; //좋아요 순으로

    @Builder
    public StudySearchForm(String keyword, int page, List<String> tags, Integer pageSize, String city, String province, Boolean recruiting, Boolean mentorRecruiting, Boolean likes) {
        this.keyword = keyword;
        this.page = page;
        this.tags = tags;
        this.pageSize = pageSize;
        this.city = city;
        this.province = province;
        this.recruiting = recruiting;
        this.mentorRecruiting = mentorRecruiting;
        this.likes = likes;
    }
}
