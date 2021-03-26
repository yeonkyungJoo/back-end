package com.project.devidea.modules.content.study.form;

import com.project.devidea.modules.content.study.Level;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class StudyBaseForm implements Serializable {

    private Long id; //studyId

    @NotEmpty
    private String title;

    private String shortDescription;

    //@NotEmpty
    private Set<String> tags=new HashSet<>();

    private int counts;
    //@NotEmpty
    private String location;


    private int likes;
    @DecimalMax(value="6")
    @DecimalMin(value="2", message = "최소2개이상이어야함")
    private int maxCount;

    //@NotNull
    private Level level;

    //@NotNull
    private Boolean recruiting; //모집하고 있는 스터디
    //@NotNull
    private Boolean mentoRecruiting;

    private LocalDateTime publishedDateTime;

    public Study toStudy(){
        return new Study().builder()
                .title(this.title)
                .shortDescription(this.shortDescription)
                .counts(this.counts)
                .likes(this.likes)
                .maxCount(this.maxCount)
                .level(this.level)
                .recruiting(this.recruiting)
                .mentoRecruiting(this.mentoRecruiting)
                .publishedDateTime(LocalDateTime.now())
                .build();
    }

}
