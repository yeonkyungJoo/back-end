package com.project.devidea.modules.content.study.form;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Level;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
public class StudyDetailForm extends StudyBaseForm{
    String admin;
    Set<String> UserName = new HashSet<>();
    String fullDescription;
    boolean open; //공개여부
    String question;
}
