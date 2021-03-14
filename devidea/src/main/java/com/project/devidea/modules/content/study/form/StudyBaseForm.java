package com.project.devidea.modules.content.study.form;

import com.project.devidea.modules.content.study.Level;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
public class StudyBaseForm {

    @NotBlank
    private String title;

    private String shortDescription;

    @NotBlank
    private Set<String> tags=new HashSet<>();

    private int counts;
    @NotBlank
    private String location;

    @NotBlank
    private int Likes;
    @NotBlank
    private int maxCount;
    @NotBlank
    private Level level;

    private boolean recruiting; //모집하고 있는 스터디

    @NotBlank
    private Boolean mentoRecruiting;

}
