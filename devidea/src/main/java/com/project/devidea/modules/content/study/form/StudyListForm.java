package com.project.devidea.modules.content.study.form;

import com.project.devidea.modules.content.study.Level;
import com.project.devidea.modules.tag.Tag;
import com.project.devidea.modules.zone.Zone;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class StudyListForm {


    @NotBlank
   private Long id;
    @NotBlank
    private String title;

    private String shortDescription;

    @NotBlank
    private Set<String> tags=new HashSet<>();

    private int counts;

    @NotBlank
    private String location;
    @NotBlank
    private LocalDateTime PublishedDateTime;
    @NotBlank
    private int Likes;
    @NotBlank
    private int maxCount;
    @NotBlank
    private Level level;
    @NotBlank
    private Boolean mentoRecruiting;

    public void customSetTags(Set<Tag> tagSet){
        tagSet.stream().forEach(tag -> tags.add(tag.getFirstName()));
    }
    public void customSetLocation(Zone zone){
        location=zone.getCity();
    }

}
