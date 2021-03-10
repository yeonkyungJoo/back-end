package com.project.devidea.modules.content.study.form;

import com.project.devidea.modules.content.study.Level;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class StudyDetailForm {
    @NotBlank
    Long id;
    @NotBlank
    String title;

    String shortDescription;

    @NotBlank
    Set<String> tags;

    @NotBlank
    String location;
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
}
