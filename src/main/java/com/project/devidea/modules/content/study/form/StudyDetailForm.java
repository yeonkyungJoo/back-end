package com.project.devidea.modules.content.study.form;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.study.Level;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.zone.Zone;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter @Setter
@EqualsAndHashCode(callSuper=false)
public class StudyDetailForm extends StudyBaseForm{
    Set<String> members = new HashSet<>();
    String fullDescription;
    boolean open; //공개여부
    String question;

    @Override
    public Study toStudy(){
        Study study=super.toStudy();
        study.setQuestion(question);
        study.setOpen(open);
        study.setFullDescription(fullDescription);
        return study;
    }
}
