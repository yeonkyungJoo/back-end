package com.project.devidea.modules.content.study.form;

import com.project.devidea.modules.content.study.Level;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
public class StudyBaseForm implements Serializable {

    @NotEmpty
    private String title;

    private String shortDescription;

    //@NotEmpty
    private Set<String> tags=new HashSet<>();

    private int counts;
    //@NotEmpty
    private String location;


    private int Likes;
    @DecimalMax(value="6")
    @DecimalMin(value="2")
    private int maxCount;

    //@NotNull
    private Level level;

    //@NotNull
    private Boolean recruiting; //모집하고 있는 스터디
    //@NotNull
    private Boolean mentoRecruiting;


    public Study toStudy(){
        return new Study().builder()
                .title(this.title)
                .shortDescription(this.shortDescription)
                .counts(this.counts)
                .likes(this.Likes)
                .maxCount(this.maxCount)
                .level(this.level)
                .recruiting(this.recruiting)
                .mentoRecruiting(this.mentoRecruiting)
                .build();
    }

}
